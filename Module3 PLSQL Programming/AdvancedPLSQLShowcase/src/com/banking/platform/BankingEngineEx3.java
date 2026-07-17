package com.banking.platform;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;

/**
 * Exercise 3: Stored Procedures & Functions Engine (Inherits Exercise 2 & 1).
 * Registers and executes native SQL procedural computations.
 */
public class BankingEngineEx3 extends BankingEngineEx2 {
    private static String ex3ConsoleLogs = "Select a Stored Procedure or Function action below to execute.";

    // --- PL/SQL ALIAS METHOD BINDINGS (Simulating Database Procedures) ---

    /**
     * Stored Procedure Simulation: Processes a loan penalty calculation.
     * Updates account rows directly inside the DB.
     */
    public static void processLoanPenalty(Connection conn, int customerId, double penaltyRate) throws SQLException {
        String updateSQL = "UPDATE Customers SET Balance = Balance - (Balance * ?) WHERE CustomerID = ?";
        try (PreparedStatement ps = conn.prepareStatement(updateSQL)) {
            ps.setDouble(1, penaltyRate / 100);
            ps.setInt(2, customerId);
            ps.executeUpdate();
        }
    }

    /**
     * Stored Function Simulation: Calculates a monthly compound interest yield estimate.
     * Returns a calculated value without editing database values.
     */
    public static double calculateInterestYield(double balance, double annualRate) {
        double monthlyRate = (annualRate / 100) / 12;
        return balance * monthlyRate;
    }

    // --- REGISTRATION & INTERACTION CONTROL ---

    public void registerStoredProcedures() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            // Register Exercise 3 Stored Procedure Alias
            stmt.execute("DROP ALIAS IF EXISTS Process_Loan_Penalty");
            stmt.execute("CREATE ALIAS Process_Loan_Penalty FOR \"com.banking.platform.BankingEngineEx3.processLoanPenalty\"");
            
            // Register Exercise 3 Stored Function Alias
            stmt.execute("DROP ALIAS IF EXISTS Calculate_Interest_Yield");
            stmt.execute("CREATE ALIAS Calculate_Interest_Yield FOR \"com.banking.platform.BankingEngineEx3.calculateInterestYield\"");
            
            System.out.println("[DB] Exercise 3 Stored Procedures & Functions Compiled and Registered Successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Trigger Execution for Procedure Scenario 1
    public void executePenaltyProcedure(int customerId, double penaltyRate) {
        StringBuilder log = new StringBuilder("<b>[Ex 3 - Procedure Execution Log]</b><br>");
        // Standard PL/SQL escape syntax for calling an engine procedure
        String callProcedure = "{CALL Process_Loan_Penalty(?, ?)}";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
        	     CallableStatement cs = conn.prepareCall(callProcedure)) {
            
            cs.setInt(1, customerId);
            cs.setDouble(2, penaltyRate);
            cs.execute();
            
            log.append(String.format("✔️ PL/SQL EXECUTION SUCCESS: Executed {CALL Process_Loan_Penalty(%d, %.1f%%)}.<br>", customerId, penaltyRate));
            log.append("Account balance adjusted directly inside database schemas.");
        } catch (SQLException e) {
            log.append("<span style='color:#e74c3c;'>Procedure Call Failed: " + e.getMessage() + "</span>");
        }
        ex3ConsoleLogs = log.toString();
    }

    // Trigger Execution for Function Scenario 2
    public void executeInterestFunction(int customerId, double annualRate) {
        StringBuilder log = new StringBuilder("<b>[Ex 3 - Function Execution Log]</b><br>");
        String queryFunction = "SELECT Name, Balance, Calculate_Interest_Yield(Balance, ?) AS Yield FROM Customers WHERE CustomerID = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(queryFunction)) {
            
            ps.setDouble(1, annualRate);
            ps.setInt(2, customerId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("Name");
                    double balance = rs.getDouble("Balance");
                    double yield = rs.getDouble("Yield");
                    
                    log.append(String.format("✔️ PL/SQL FUNCTION OUT: Profile %s (ID %d)<br>", name, customerId));
                    log.append(String.format("Principal: $%.2f at Annual %.1f%% Rate<br>", balance, annualRate));
                    log.append(String.format("<b>Computed Monthly Yield: <span style='color:#2ecc71;'>$%.2f</span></b>", yield));
                } else {
                    log.append("<span style='color:#e74c3c;'>Customer Profile Not Found.</span>");
                }
            }
        } catch (SQLException e) {
            log.append("<span style='color:#e74c3c;'>Function Execution Failed: " + e.getMessage() + "</span>");
        }
        ex3ConsoleLogs = log.toString();
    }

    // --- OVERRIDDEN EVOLUTIONARY WEB CONTROLLER ---

    @Override
    public void startWebServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/", new DeepEvolutionDashboardHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("=========================================================");
        System.out.println(" 🌐 FULL STACK LIVE: EXERCISE 1 + 2 + 3 COMPILATION ENGINE");
        System.out.println(" Open your browser and go to: http://localhost:8081");
        System.out.println("=========================================================");
    }

    private class DeepEvolutionDashboardHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String uri = exchange.getRequestURI().toString();

            // Handle inherited control triggers
            if (uri.contains("/run-s1")) runScenario1_InterestDiscount();
            else if (uri.contains("/run-s2")) runScenario2_VIPPromotion();
            else if (uri.contains("/run-s3")) runScenario3_LoanReminders();
            else if (uri.contains("/ex2-s1-fail")) runSafeTransferFunds(1, 2, 99999.00);
            else if (uri.contains("/ex2-s1-pass")) runSafeTransferFunds(2, 1, 500.00);
            else if (uri.contains("/ex2-s2-fail")) runUpdateSalary(999, 10.0);
            else if (uri.contains("/ex2-s3-fail")) runAddNewCustomer(1, "Clone John", 500);
            
            // Exercise 3 Stored Routine hooks
            else if (uri.contains("/ex3-proc")) executePenaltyProcedure(1, 5.0); // Apply 5% Penalty to Account 1
            else if (uri.contains("/ex3-func")) executeInterestFunction(2, 6.0); // Estimate 6% Yield for Account 2

            StringBuilder html = new StringBuilder();
            html.append("<html><head><title>PL/SQL Complete Stack</title>");
            html.append("<style>body{font-family:'Segoe UI',Arial; margin:30px; background:#f4f6f9; color:#333;} ");
            html.append(".container{max-width:1200px; margin:auto;} ");
            html.append(".header{background:#111827; color:white; padding:20px; border-radius:6px; margin-bottom:20px;} ");
            html.append(".grid{display:grid; grid-template-columns: 1.5fr 1fr; gap:20px;} .card{background:white; padding:20px; border-radius:6px; box-shadow:0 4px 6px rgba(0,0,0,0.05); margin-bottom:20px;} ");
            html.append("table{width:100%; border-collapse:collapse; margin-top:10px;} th,td{padding:10px; border:1px solid #ddd; text-align:left;} th{background:#f8f9fa;} ");
            html.append(".btn{background:#2980b9; color:white; padding:6px 12px; border:none; border-radius:4px; text-decoration:none; font-weight:bold; display:inline-block; margin-right:5px; margin-top:5px; font-size:13px;} ");
            html.append(".btn-purple{background:#8e44ad;} .btn-danger{background:#e74c3c;} .btn:hover{opacity:0.9;} ");
            html.append(".console{background:#1a1a1a; color:#2ecc71; padding:15px; border-radius:5px; font-family:monospace; min-height:100px; line-height:1.5;}</style></head><body>");

            html.append("<div class='container'><div class='header'><h2>Accumulated Banking Core System (Module 3)</h2><p>Active Combined Scope: <b>Ex 1 (Cursors) + Ex 2 (Exceptions) + Ex 3 (Procedures & Functions)</b></p></div>");
            html.append("<div class='grid'><div>");

            // --- ALL INHERITED EXPERIMENT VIEWS ---
            html.append("<div class='card' style='border-left: 4px solid #2980b9;'><h3>📦 Active Customer Database Matrix</h3>");
            html.append("<table><tr><th>Account ID</th><th>Holder Name</th><th>Live Balance</th><th>VIP Standing</th></tr>");
            try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Customers")) {
                while (rs.next()) html.append(String.format("<tr><td>%d</td><td>%s</td><td>$%.2f</td><td><b>%s</b></td></tr>", rs.getInt("CustomerID"), rs.getString("Name"), rs.getDouble("Balance"), rs.getString("IsVIP")));
            } catch (SQLException ignored) {}
            html.append("</table></div>");

            html.append("<div class='card' style='border-left: 4px solid #e67e22;'><h3>📋 Corporate Staff Framework</h3>");
            html.append("<table><tr><th>Employee ID</th><th>Employee Name</th><th>Current Salary</th><th>Department</th></tr>");
            try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Employees")) {
                while (rs.next()) html.append(String.format("<tr><td>%d</td><td>%s</td><td>$%.2f</td><td>%s</td></tr>", rs.getInt("EmployeeID"), rs.getString("Name"), rs.getDouble("Salary"), rs.getString("Department")));
            } catch (SQLException ignored) {}
            html.append("</table></div></div>");

            // --- CONTROLS COLUMN ---
            html.append("<div>");
            
            // Stored Procedures Section
            html.append("<div class='card' style='border-top: 4px solid #8e44ad;'><h3>⚙️ Exercise 3: Stored Routines</h3>");
            html.append("<p><b>Procedure:</b> Direct Balance Penalty Engine</p>");
            html.append("<a href='/ex3-proc' class='btn btn-purple'>{CALL Process_Loan_Penalty(ID 1, 5%)}</a><hr style='border:1px solid #f3f3f3;'>");
            html.append("<p><b>Function:</b> Calculation Isolation Yield Engine</p>");
            html.append("<a href='/ex3-func' class='btn btn-purple'>SELECT Calculate_Interest_Yield(ID 2, 6%)</a>");
            html.append("</div>");

            // Previous Exercises
            html.append("<div class='card'><h3>🔗 Legacy Structural Hooks</h3>");
            html.append("<small><b>Exercise 1 Cursors:</b></small><br><a href='/run-s1' class='btn'>Age Promo</a> <a href='/run-s2' class='btn'>VIP Scan</a><br><br>");
            html.append("<small><b>Exercise 2 Exceptions:</b></small><br><a href='/ex2-s1-fail' class='btn btn-danger'>Overdraft Fail</a> <a href='/ex2-s3-fail' class='btn btn-danger'>Duplicate Constraint</a>");
            html.append("</div>");

            // Shared Compilation Engine Console Box
            html.append("<div class='card' style='background:#222; color:white;'><h3>🖥️ Global Engine Terminal Output</h3><div class='console'>").append(ex3ConsoleLogs).append("</div></div>");

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
        BankingEngineEx3 engine = new BankingEngineEx3();
        engine.initializeDatabase();           // Build Ex 1 Table Structs
        engine.initializeAdditionalSchemas();   // Build Ex 2 Table Structs
        engine.registerStoredProcedures();      // Compile Ex 3 Functions into System
        engine.startWebServer();               // Open Multi-Exercise Integrated Window
    }
}