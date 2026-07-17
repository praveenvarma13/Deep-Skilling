package com.banking.platform;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;

/**
 * Exercise 1: Control Structures Engine.
 * Manages the core database connection and renders the live Localhost Dashboard.
 */
public class BankingEngineEx1 {
    protected static final String DB_URL = "jdbc:h2:mem:bankdb;DB_CLOSE_DELAY=-1";
    protected static String consoleLogs = "System ready. Click an execution block to test triggers.";

    public void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            // Create Schemas matching project brief
            stmt.execute("CREATE TABLE IF NOT EXISTS Customers (CustomerID INT PRIMARY KEY, Name VARCHAR(100), DOB DATE, Balance NUMBER, LastModified DATE, IsVIP VARCHAR(5) DEFAULT 'FALSE')");
            stmt.execute("CREATE TABLE IF NOT EXISTS Loans (LoanID INT PRIMARY KEY, CustomerID INT, LoanAmount NUMBER, InterestRate NUMBER, StartDate DATE, EndDate DATE)");

            // Clean previous test data
            stmt.execute("DELETE FROM Customers");
            stmt.execute("DELETE FROM Loans");

            // Seed sample database records
            stmt.execute("INSERT INTO Customers VALUES (1, 'John Doe', '1985-05-15', 1000, NOW(), 'FALSE')");
            stmt.execute("INSERT INTO Customers VALUES (2, 'Jane Smith', '1990-07-20', 15000, NOW(), 'FALSE')"); // Over 10k balance
            stmt.execute("INSERT INTO Customers VALUES (3, 'Robert Elder', '1955-03-12', 4500, NOW(), 'FALSE')"); // Over 60 years old

            stmt.execute("INSERT INTO Loans VALUES (1, 1, 5000, 5.0, NOW(), DATEADD('MONTH', 60, NOW()))");
            stmt.execute("INSERT INTO Loans VALUES (2, 3, 12000, 6.5, NOW(), DATEADD('DAY', 15, NOW()))"); // Due in 15 days

            System.out.println("[DB] Core Bank Schema Built & Seeded Successfully in Module 3 Workspace.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- PL/SQL SIMULATION BLOCKS ---

    public void runScenario1_InterestDiscount() {
        String query = "SELECT c.CustomerID, c.Name, l.LoanID, l.InterestRate, " +
                       "DATEDIFF('YEAR', c.DOB, NOW()) AS Age " +
                       "FROM Customers c JOIN Loans l ON c.CustomerID = l.CustomerID";
        
        StringBuilder log = new StringBuilder("<b>[Scenario 1 Log]</b><br>");
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                int age = rs.getInt("Age");
                int loanId = rs.getInt("LoanID");
                double currentRate = rs.getDouble("InterestRate");
                
                if (age > 60) {
                    double newRate = currentRate - 1.0;
                    try (PreparedStatement update = conn.prepareStatement("UPDATE Loans SET InterestRate = ? WHERE LoanID = ?")) {
                        update.setDouble(1, newRate);
                        update.setInt(2, loanId);
                        update.executeUpdate();
                    }
                    log.append(String.format("✔️ Applied 1%% Interest Discount for Senior Citizen %s (Age %d). Loan ID %d dropped from %.1f%% to %.1f%%.<br>", 
                            rs.getString("Name"), age, loanId, currentRate, newRate));
                } else {
                    log.append(String.format("❌ Skipped %s (Age %d) - Does not qualify.<br>", rs.getString("Name"), age));
                }
            }
            consoleLogs = log.toString();
        } catch (SQLException e) {
            consoleLogs = "Error: " + e.getMessage();
        }
    }

    public void runScenario2_VIPPromotion() {
        StringBuilder log = new StringBuilder("<b>[Scenario 2 Log]</b><br>");
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT CustomerID, Name, Balance FROM Customers")) {
            
            while (rs.next()) {
                double balance = rs.getDouble("Balance");
                int id = rs.getInt("CustomerID");
                
                if (balance > 10000) {
                    try (PreparedStatement update = conn.prepareStatement("UPDATE Customers SET IsVIP = 'TRUE' WHERE CustomerID = ?")) {
                        update.setInt(1, id);
                        update.executeUpdate();
                    }
                    log.append(String.format("👑 Client %s promoted to VIP status (Balance: $%.2f).<br>", rs.getString("Name"), balance));
                } else {
                    log.append(String.format("❌ Client %s balance $%.2f insufficient for VIP.<br>", rs.getString("Name"), balance));
                }
            }
            consoleLogs = log.toString();
        } catch (SQLException e) {
            consoleLogs = "Error: " + e.getMessage();
        }
    }

    public void runScenario3_LoanReminders() {
        String query = "SELECT l.LoanID, c.Name, l.EndDate, DATEDIFF('DAY', NOW(), l.EndDate) AS DaysRemaining " +
                       "FROM Loans l JOIN Customers c ON l.CustomerID = c.CustomerID";
        
        StringBuilder log = new StringBuilder("<b>[Scenario 3 Log - Loan Alerts]</b><br>");
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            boolean alertsFound = false;
            while (rs.next()) {
                int days = rs.getInt("DaysRemaining");
                if (days > 0 && days <= 30) {
                    log.append(String.format("⚠️ REMINDER: Loan ID %d for %s is due in %d days! (Deadline: %s)<br>", 
                            rs.getInt("LoanID"), rs.getString("Name"), days, rs.getDate("EndDate")));
                    alertsFound = true;
                }
            }
            if (!alertsFound) log.append("No loans are due within the next 30 days.<br>");
            consoleLogs = log.toString();
        } catch (SQLException e) {
            consoleLogs = "Error: " + e.getMessage();
        }
    }

    // --- WEB INTERFACE CONTROLLER ---

    public void startWebServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/", new DashboardHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("=========================================================");
        System.out.println(" 🌐 SYSTEM ONLINE: INTERACTIVE PL/SQL BASELINE WORKING");
        System.out.println(" Access local interactive page: http://localhost:8081");
        System.out.println("=========================================================");
    }

    protected class DashboardHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String uri = exchange.getRequestURI().toString();

            if (uri.contains("/run-s1")) runScenario1_InterestDiscount();
            else if (uri.contains("/run-s2")) runScenario2_VIPPromotion();
            else if (uri.contains("/run-s3")) runScenario3_LoanReminders();

            StringBuilder html = new StringBuilder();
            html.append("<html><head><title>PL/SQL Module 3 Prototype</title>");
            html.append("<style>body{font-family:'Segoe UI',Arial; margin:30px; background:#f4f6f9; color:#333;} ");
            html.append(".container{max-width:1100px; margin:auto;} ");
            html.append(".header{background:#2c3e50; color:white; padding:20px; border-radius:6px; margin-bottom:20px;} ");
            html.append(".grid{display:grid; grid-template-columns: 2fr 1fr; gap:20px;} .card{background:white; padding:20px; border-radius:6px; box-shadow:0 4px 6px rgba(0,0,0,0.05); margin-bottom:20px;} ");
            html.append("table{width:100%; border-collapse:collapse; margin-top:10px;} th,td{padding:10px; border:1px solid #ddd; text-align:left;} th{background:#f8f9fa;} ");
            html.append(".btn{background:#2980b9; color:white; padding:8px 14px; border:none; border-radius:4px; text-decoration:none; font-weight:bold; display:inline-block; margin-right:5px; margin-top:5px; cursor:pointer;} ");
            html.append(".btn:hover{background:#34495e;} .console{background:#1a1a1a; color:#2ecc71; padding:15px; border-radius:5px; font-family:monospace; min-height:120px; line-height:1.5;}</style></head><body>");

            html.append("<div class='container'><div class='header'><h2>Accumulated Banking Core System (Module 3)</h2><p>Active Scope: <b>Exercise 1 (Control Structures)</b></p></div>");
            html.append("<div class='grid'><div>");

            // Build dynamic table content out from the embedded DB fields
            html.append("<div class='card'><h3>📋 Active Customer Matrix (Ex 1)</h3><table><tr><th>ID</th><th>Name</th><th>Birth Date</th><th>Balance</th><th>VIP Status</th></tr>");
            try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Customers")) {
                while (rs.next()) html.append(String.format("<tr><td>%d</td><td>%s</td><td>%s</td><td>$%.2f</td><td><b>%s</b></td></tr>", rs.getInt("CustomerID"), rs.getString("Name"), rs.getDate("DOB"), rs.getDouble("Balance"), rs.getString("IsVIP")));
            } catch (SQLException ignored) {}
            html.append("</table><br>");

            html.append("<h3>📋 Active Loan Accounts (Ex 1)</h3><table><tr><th>Loan ID</th><th>Cust ID</th><th>Amount</th><th>Interest Rate</th><th>End Date</th></tr>");
            try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Loans")) {
                while (rs.next()) html.append(String.format("<tr><td>%d</td><td>%d</td><td>$%.2f</td><td>%.2f%%</td><td>%s</td></tr>", rs.getInt("LoanID"), rs.getInt("CustomerID"), rs.getDouble("LoanAmount"), rs.getDouble("InterestRate"), rs.getDate("EndDate")));
            } catch (SQLException ignored) {}
            html.append("</table></div></div>");

            // Control Actions Panel
            html.append("<div><div class='card'><h3>⚡ Run PL/SQL Control Blocks</h3>");
            html.append("<p><b>Scenario 1:</b> Senior Interest Check</p><a href='/run-s1' class='btn'>Run Block 1</a><hr style='border:1px solid #eee; margin:15px 0;'>");
            html.append("<p><b>Scenario 2:</b> VIP Promotion Scan</p><a href='/run-s2' class='btn'>Run Block 2</a><hr style='border:1px solid #eee; margin:15px 0;'>");
            html.append("<p><b>Scenario 3:</b> Loan Expiry Reminders</p><a href='/run-s3' class='btn'>Run Block 3</a></div>");

            // Real-time Terminal Log Box
            html.append("<div class='card' style='background:#222; color:white;'><h3>🖥️ Engine PL/SQL Terminal Output</h3><div class='console'>").append(consoleLogs).append("</div></div>");

            html.append("</div></div></body></html>");

            byte[] bytes = html.toString().getBytes();
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }

    public static void main(String[] args) throws IOException {
        BankingEngineEx1 engine = new BankingEngineEx1();
        engine.initializeDatabase();
        engine.startWebServer();
    }
}