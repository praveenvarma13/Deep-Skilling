package com.banking.platform;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;

/**
 * Exercise 2: Error Handling Engine (Inherits Exercise 1).
 * Displays both Exercise 1 tables AND adds interactive robust error management scenarios.
 */
public class BankingEngineEx2 extends BankingEngineEx1 {
    private static String ex2ConsoleLogs = "Select an Error Handling Scenario action below to execute.";

    // Additional schemas needed for Exercise 2 Scenario 2 & 3
    public void initializeAdditionalSchemas() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            stmt.execute("CREATE TABLE IF NOT EXISTS Employees (EmployeeID INT PRIMARY KEY, Name VARCHAR(100), Salary NUMBER, Department VARCHAR(50))");
            
            // Clean and Seed Employee records
            stmt.execute("DELETE FROM Employees");
            stmt.execute("INSERT INTO Employees VALUES (1, 'Alice Johnson', 70000, 'HR')");
            stmt.execute("INSERT INTO Employees VALUES (2, 'Bob Brown', 60000, 'IT')");
            
            System.out.println("[DB] Exercise 2 Extended Employee Tables Added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- PL/SQL ERROR HANDLING SIMULATIONS ---

    // Scenario 1: Safe Transfer Funds with Exception Catching & Rollbacks
    public void runSafeTransferFunds(int sourceAcc, int targetAcc, double amount) {
        StringBuilder log = new StringBuilder("<b>[Ex 2 - Scenario 1 Safe Transfer Log]</b><br>");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(false); // Enable manual transaction control for rollbacks

            // Check balance first
            double sourceBalance = 0;
            try (PreparedStatement ps = conn.prepareStatement("SELECT Balance FROM Customers WHERE CustomerID = ?")) {
                ps.setInt(1, sourceAcc);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) sourceBalance = rs.getDouble("Balance");
                }
            }

            // Custom PL/SQL Business Logic Exception handling
            if (sourceBalance < amount) {
                throw new SQLException("PRAGMA EXCEPTION_INIT: Insufficient funds for transaction!");
            }

            // Perform transaction updates
            try (PreparedStatement deduct = conn.prepareStatement("UPDATE Customers SET Balance = Balance - ? WHERE CustomerID = ?")) {
                deduct.setDouble(1, amount);
                deduct.setInt(2, sourceAcc);
                deduct.executeUpdate();
            }

            try (PreparedStatement credit = conn.prepareStatement("UPDATE Customers SET Balance = Balance + ? WHERE CustomerID = ?")) {
                credit.setDouble(1, amount);
                credit.setInt(2, targetAcc);
                credit.executeUpdate();
            }

            conn.commit(); // PL/SQL COMMIT
            log.append(String.format("✔️ SUCCESS: Cleanly transferred $%.2f from Customer %d to %d.<br>", amount, sourceAcc, targetAcc));
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // PL/SQL EXCEPTION ROLLBACK
                    log.append("🛑 EXCEPTION TRIGGERED & ROLLED BACK safely!<br>");
                } catch (SQLException ex) { log.append("Rollback error: " + ex.getMessage()); }
            }
            log.append("<span style='color:#e74c3c;'>Error Caught: " + e.getMessage() + "</span>");
        } finally {
            if (conn != null) { try { conn.close(); } catch (SQLException ignored) {} }
            ex2ConsoleLogs = log.toString();
        }
    }

    // Scenario 2: Update Employee Salary with NO_DATA_FOUND handling
    public void runUpdateSalary(int empId, double percentage) {
        StringBuilder log = new StringBuilder("<b>[Ex 2 - Scenario 2 Salary Log]</b><br>");
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            boolean recordsFound = false;
            try (PreparedStatement check = conn.prepareStatement("SELECT Name FROM Employees WHERE EmployeeID = ?")) {
                check.setInt(1, empId);
                try (ResultSet rs = check.executeQuery()) {
                    if (rs.next()) recordsFound = true;
                }
            }

            // Handle PL/SQL NO_DATA_FOUND error condition
            if (!recordsFound) {
                throw new SQLException("SQLERRM: ORA-01403: No Data Found for Employee ID " + empId);
            }

            try (PreparedStatement update = conn.prepareStatement("UPDATE Employees SET Salary = Salary * (1 + ?/100) WHERE EmployeeID = ?")) {
                update.setDouble(1, percentage);
                update.setInt(2, empId);
                update.executeUpdate();
            }
            log.append(String.format("✔️ SUCCESS: Employee ID %d found. Increased salary by %.1f%%.<br>", empId, percentage));
        } catch (SQLException e) {
            log.append("<span style='color:#e74c3c;'>PL/SQL Exception Handled: " + e.getMessage() + "</span>");
        }
        ex2ConsoleLogs = log.toString();
    }

    // Scenario 3: Add New Customer with DUP_VAL_ON_INDEX Primary Key Check
    public void runAddNewCustomer(int id, String name, double initialBalance) {
        StringBuilder log = new StringBuilder("<b>[Ex 2 - Scenario 3 Customer Insertion Log]</b><br>");
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement insert = conn.prepareStatement("INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified, IsVIP) VALUES (?, ?, '1995-01-01', ?, NOW(), 'FALSE')")) {
            
            insert.setInt(1, id);
            insert.setString(2, name);
            insert.setDouble(3, initialBalance);
            insert.executeUpdate();
            
            log.append(String.format("✔️ SUCCESS: Created records for a fresh profile: %s (ID %d).<br>", name, id));
        } catch (SQLException e) {
            // Check if it's a primary key violation constraint (Error Code 23505 in H2/Standard SQL)
            if ("23505".equals(e.getSQLState())) {
                log.append(String.format("<span style='color:#e74c3c;'>🛑 PL/SQL EXCEPTION HANDLED: ORA-00001: Unique Constraint Violated! Customer ID %d already exists. Record rejected.</span>", id));
            } else {
                log.append("Database Error: " + e.getMessage());
            }
        }
        ex2ConsoleLogs = log.toString();
    }

    // --- OVERRIDDEN EVOLUTIONARY WEB CONTROLLER ---

    @Override
    public void startWebServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/", new AccumulatedDashboardHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("=========================================================");
        System.out.println(" 🌐 ACCUMULATED PLATFORM LIVE: EXERCISE 1 + EXERCISE 2 ACCUMULATED");
        System.out.println(" Open your browser and go to: http://localhost:8081");
        System.out.println("=========================================================");
    }

    private class AccumulatedDashboardHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String uri = exchange.getRequestURI().toString();

            // Intercept URL Action triggers
            if (uri.contains("/run-s1")) runScenario1_InterestDiscount();
            else if (uri.contains("/run-s2")) runScenario2_VIPPromotion();
            else if (uri.contains("/run-s3")) runScenario3_LoanReminders();
            // Exercise 2 URL action binds
            else if (uri.contains("/ex2-s1-fail")) runSafeTransferFunds(1, 2, 99999.00); // Trigger Insufficient Error
            else if (uri.contains("/ex2-s1-pass")) runSafeTransferFunds(2, 1, 500.00);    // Clean transfer
            else if (uri.contains("/ex2-s2-fail")) runUpdateSalary(999, 10.0);           // Non-existent ID
            else if (uri.contains("/ex2-s3-fail")) runAddNewCustomer(1, "Clone John", 500); // Dup Primary Key

            StringBuilder html = new StringBuilder();
            html.append("<html><head><title>PL/SQL Evolution Matrix</title>");
            html.append("<style>body{font-family:'Segoe UI',Arial; margin:30px; background:#f4f6f9; color:#333;} ");
            html.append(".container{max-width:1200px; margin:auto;} ");
            html.append(".header{background:#2c3e50; color:white; padding:20px; border-radius:6px; margin-bottom:20px;} ");
            html.append(".grid{display:grid; grid-template-columns: 1.5fr 1fr; gap:20px;} .card{background:white; padding:20px; border-radius:6px; box-shadow:0 4px 6px rgba(0,0,0,0.05); margin-bottom:20px;} ");
            html.append("table{width:100%; border-collapse:collapse; margin-top:10px;} th,td{padding:10px; border:1px solid #ddd; text-align:left;} th{background:#f8f9fa;} ");
            html.append(".btn{background:#2980b9; color:white; padding:6px 12px; border:none; border-radius:4px; text-decoration:none; font-weight:bold; display:inline-block; margin-right:5px; margin-top:5px; font-size:13px;} ");
            html.append(".btn-danger{background:#e74c3c;} .btn:hover{opacity:0.9;} ");
            html.append(".console{background:#1a1a1a; color:#2ecc71; padding:15px; border-radius:5px; font-family:monospace; min-height:100px; line-height:1.5;}</style></head><body>");

            html.append("<div class='container'><div class='header'><h2>Accumulated Banking Core System (Module 3)</h2><p>Active Combined Scope: <b>Exercise 1 + Exercise 2 (Error Handling Constraints)</b></p></div>");
            html.append("<div class='grid'><div>");

            // --- ACCUMULATED LAYER 1: Exercise 1 Core Datasets ---
            html.append("<div class='card' style='border-left: 4px solid #2980b9;'><h3>📦 Exercise 1 Views: Core Databases</h3>");
            html.append("<h4>Active Customer Balances</h4><table><tr><th>ID</th><th>Name</th><th>Balance</th><th>VIP Status</th></tr>");
            try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Customers")) {
                while (rs.next()) html.append(String.format("<tr><td>%d</td><td>%s</td><td>$%.2f</td><td><b>%s</b></td></tr>", rs.getInt("CustomerID"), rs.getString("Name"), rs.getDouble("Balance"), rs.getString("IsVIP")));
            } catch (SQLException ignored) {}
            html.append("</table></div>");

            // --- NEW LAYER 2: Exercise 2 Core Datasets ---
            html.append("<div class='card' style='border-left: 4px solid #e67e22;'><h3>📋 Exercise 2 Extended Views: Staff Infrastructure</h3>");
            html.append("<table><tr><th>Employee ID</th><th>Employee Name</th><th>Current Salary</th><th>Department</th></tr>");
            try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Employees")) {
                while (rs.next()) html.append(String.format("<tr><td>%d</td><td>%s</td><td>$%.2f</td><td>%s</td></tr>", rs.getInt("EmployeeID"), rs.getString("Name"), rs.getDouble("Salary"), rs.getString("Department")));
            } catch (SQLException ignored) {}
            html.append("</table></div></div>");

            // --- CONTROLS RIGHT SIDEBAR ---
            html.append("<div>");
            
            // Ex 1 Controls remaining functional
            html.append("<div class='card'><h3>⚡ Ex 1 Control Hooks</h3>");
            html.append("<a href='/run-s1' class='btn'>Run Age Promo</a> <a href='/run-s2' class='btn'>Scan VIP Status</a></div>");

            // Ex 2 Error Handling Controls
            html.append("<div class='card' style='border-top: 4px solid #e74c3c;'><h3>🛑 Ex 2 Controls: Exception Managers</h3>");
            html.append("<p><b>Scenario 1:</b> Fund Transfer Protocol</p>");
            html.append("<a href='/ex2-s1-fail' class='btn btn-danger'>Trigger Overdraft (Fail)</a>");
            html.append("<a href='/ex2-s1-pass' class='btn'>Valid Transfer (Pass)</a><hr style='border:1px solid #f3f3f3;'>");
            
            html.append("<p><b>Scenario 2:</b> Employee Management Lookup</p>");
            html.append("<a href='/ex2-s2-fail' class='btn btn-danger'>Query Missing ID (Fail)</a><hr style='border:1px solid #f3f3f3;'>");
            
            html.append("<p><b>Scenario 3:</b> Constraint Integrity Enforcer</p>");
            html.append("<a href='/ex2-s3-fail' class='btn btn-danger'>Insert Duplicate ID (Fail)</a>");
            html.append("</div>");

            // Shared Execution Terminal Console Window
            html.append("<div class='card' style='background:#222; color:white;'><h3>🖥️ Global Engine Terminal Output</h3><div class='console'>").append(ex2ConsoleLogs).append("</div></div>");

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
        BankingEngineEx2 engine = new BankingEngineEx2();
        engine.initializeDatabase();           // Seeds baseline Ex 1 schemas
        engine.initializeAdditionalSchemas();   // Adds extended Ex 2 tables
        engine.startWebServer();               // Launches accumulated web view
    }
}