package com.banking.platform;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;

/**
 * Exercise 4: Packages & Complete Integration Engine (Inherits Exercise 3, 2 & 1).
 * Emulates full PL/SQL Package Structures (Specification + Body) and binds global states.
 */
public class BankingEngineEx4 extends BankingEngineEx3 {
    private static String ex4ConsoleLogs = "Select a Packaged API Module function below to execute.";

    // =========================================================================
    // 📦 SIMULATING PL/SQL PACKAGE SPECIFICATION & BODY
    // =========================================================================
    public static class BankingPackage {
        // Package Specification Variables (Global constants across sessions)
        public static final double MIN_BAL_REQUIRED = 100.00;
        public static final double PREMIUM_TAX_RATE = 2.5; // 2.5% Tax State
        
        /**
         * Package Body Function: Calculates specialized compliance tax on rich profiles.
         */
        public static double computeAccountTax(double balance) {
            if (balance > 5000) {
                return balance * (PREMIUM_TAX_RATE / 100);
            }
            return 0.0;
        }

        /**
         * Package Body Procedure: Force-audits an account to match minimum compliance requirements.
         */
        public static void enforceComplianceAudit(Connection conn, int customerId) throws SQLException {
            String auditSQL = "UPDATE Customers SET IsVIP = 'FALSE', Balance = Balance - 15.00 " +
                             "WHERE CustomerID = ? AND Balance < ?";
            try (PreparedStatement ps = conn.prepareStatement(auditSQL)) {
                ps.setInt(1, customerId);
                ps.setDouble(2, MIN_BAL_REQUIRED);
                ps.executeUpdate();
            }
        }
    }

    // --- DB PACKAGE INITIALIZATION & ALIAS BINDINGS ---

    public void compileAndRegisterPackage() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            // Register Packaged Function Component
            stmt.execute("DROP ALIAS IF EXISTS Banking_Pkg_Compute_Tax");
            stmt.execute("CREATE ALIAS Banking_Pkg_Compute_Tax FOR \"com.banking.platform.BankingEngineEx4$BankingPackage.computeAccountTax\"");
            
            // Register Packaged Procedure Component
            stmt.execute("DROP ALIAS IF EXISTS Banking_Pkg_Enforce_Audit");
            stmt.execute("CREATE ALIAS Banking_Pkg_Enforce_Audit FOR \"com.banking.platform.BankingEngineEx4$BankingPackage.enforceComplianceAudit\"");
            
            System.out.println("[DB] PL/SQL Package 'BankingPackage' Compiled and Bound into Memory Cache.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Trigger Packaged Procedure Scenario 1
    public void executePackageAudit(int customerId) {
        StringBuilder log = new StringBuilder("<b>[Ex 4 - Packaged Procedure Call Log]</b><br>");
        String callSQL = "{CALL Banking_Pkg_Enforce_Audit(?)}";
        
        String callProcedure = null;
		try (Connection conn = DriverManager.getConnection(DB_URL);
        	     CallableStatement cs = conn.prepareCall(callProcedure)) {            
            cs.setInt(1, customerId);
            cs.execute();
            
            log.append(String.format("✔️ PACKAGE ACTION: {CALL BankingPackage.Enforce_Audit(ID %d)} executed.<br>", customerId));
            log.append("If account fell short of package minimum, penalty constraints have been processed.");
        } catch (SQLException e) {
            log.append("<span style='color:#e74c3c;'>Package Execution Error: " + e.getMessage() + "</span>");
        }
        ex4ConsoleLogs = log.toString();
    }

    // Trigger Packaged Function Scenario 2
    public void executePackageTaxCalculation(int customerId) {
        StringBuilder log = new StringBuilder("<b>[Ex 4 - Packaged Function Select Log]</b><br>");
        String querySQL = "SELECT Name, Balance, Banking_Pkg_Compute_Tax(Balance) AS TaxAmount FROM Customers WHERE CustomerID = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(querySQL)) {
            
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("Name");
                    double balance = rs.getDouble("Balance");
                    double tax = rs.getDouble("TaxAmount");
                    
                    log.append(String.format("✔️ PACKAGE MODULE RETURN: %s (ID %d)<br>", name, customerId));
                    log.append(String.format("Live Assets Evaluated: $%.2f<br>", balance));
                    log.append(String.format("<b>Computed Package Tax (2.5%%): <span style='color:#e74c3c;'>$%.2f</span></b>", tax));
                }
            }
        } catch (SQLException e) {
            log.append("<span style='color:#e74c3c;'>Package Function Error: " + e.getMessage() + "</span>");
        }
        ex4ConsoleLogs = log.toString();
    }

    // --- OVERRIDDEN FULL-STACK INTERACTION CONTROLLER ---

    @Override
    public void startWebServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/", new IntegratedStackDashboardHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("=========================================================");
        System.out.println(" 🌐 FULL STACK SYSTEM OPERATIONAL: INTEGRATED MATRIX LIVE");
        System.out.println(" Point your clean web browser window to: http://localhost:8081");
        System.out.println("=========================================================");
    }

    private class IntegratedStackDashboardHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String uri = exchange.getRequestURI().toString();

            // Direct route matching for every level of the evolution
            if (uri.contains("/run-s1")) runScenario1_InterestDiscount();
            else if (uri.contains("/run-s2")) runScenario2_VIPPromotion();
            else if (uri.contains("/run-s3")) runScenario3_LoanReminders();
            else if (uri.contains("/ex2-s1-fail")) runSafeTransferFunds(1, 2, 99999.00);
            else if (uri.contains("/ex2-s1-pass")) runSafeTransferFunds(2, 1, 500.00);
            else if (uri.contains("/ex2-s2-fail")) runUpdateSalary(999, 10.0);
            else if (uri.contains("/ex2-s3-fail")) runAddNewCustomer(1, "Clone John", 500);
            else if (uri.contains("/ex3-proc")) executePenaltyProcedure(1, 5.0);
            else if (uri.contains("/ex3-func")) executeInterestFunction(2, 6.0);
            
            // New Exercise 4 Package hooks
            else if (uri.contains("/ex4-audit")) executePackageAudit(1); 
            else if (uri.contains("/ex4-tax")) executePackageTaxCalculation(1); 

            StringBuilder html = new StringBuilder();
            html.append("<html><head><title>Module 3 Integrated Stack</title>");
            html.append("<style>body{font-family:'Segoe UI',Arial; margin:30px; background:#f4f6f9; color:#333;} ");
            html.append(".container{max-width:1200px; margin:auto;} ");
            html.append(".header{background:#0f172a; color:white; padding:25px; border-radius:6px; margin-bottom:20px; text-align:center;} ");
            html.append(".grid{display:grid; grid-template-columns: 1.4fr 1fr; gap:20px;} .card{background:white; padding:20px; border-radius:6px; box-shadow:0 4px 6px rgba(0,0,0,0.05); margin-bottom:20px;} ");
            html.append("table{width:100%; border-collapse:collapse; margin-top:10px;} th,td{padding:10px; border:1px solid #ddd; text-align:left;} th{background:#f8f9fa;} ");
            html.append(".btn{background:#2980b9; color:white; padding:6px 12px; border:none; border-radius:4px; text-decoration:none; font-weight:bold; display:inline-block; margin-right:5px; margin-top:5px; font-size:12px;} ");
            html.append(".btn-teal{background:#0d9488;} .btn-purple{background:#8e44ad;} .btn-danger{background:#e74c3c;} .btn:hover{opacity:0.9;} ");
            html.append(".console{background:#111; color:#00ff66; padding:15px; border-radius:5px; font-family:monospace; min-height:90px; line-height:1.5;}</style></head><body>");

            html.append("<div class='container'><div class='header'><h2>🌐 Module 3: Complete Enterprise PL/SQL Stack</h2><p>Integrated Pipeline Active: <b>Exercises 1-4 Complete Matrix</b></p></div>");
            html.append("<div class='grid'><div>");

            // Global Data Views
            html.append("<div class='card' style='border-left: 5px solid #2980b9;'><h3>📦 Live Core Account Schema</h3>");
            html.append("<table><tr><th>ID</th><th>Account Profile Name</th><th>Live Balance</th><th>VIP Status</th></tr>");
            try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Customers")) {
                while (rs.next()) html.append(String.format("<tr><td>%d</td><td>%s</td><td>$%.2f</td><td><b>%s</b></td></tr>", rs.getInt("CustomerID"), rs.getString("Name"), rs.getDouble("Balance"), rs.getString("IsVIP")));
            } catch (SQLException ignored) {}
            html.append("</table></div>");

            html.append("<div class='card' style='border-left: 5px solid #e67e22;'><h3>📋 Live Corporate Employee Schema</h3>");
            html.append("<table><tr><th>ID</th><th>Name</th><th>Salary Matrix</th><th>Department</th></tr>");
            try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Employees")) {
                while (rs.next()) html.append(String.format("<tr><td>%d</td><td>%s</td><td>$%.2f</td><td>%s</td></tr>", rs.getInt("EmployeeID"), rs.getString("Name"), rs.getDouble("Salary"), rs.getString("Department")));
            } catch (SQLException ignored) {}
            html.append("</table></div></div>");

            // Controls Matrix Right Pane
            html.append("<div>");
            
            // Exercise 4 Packaged Elements Hook Control
            html.append("<div class='card' style='border-top: 5px solid #0d9488; background:#fafdfc;'><h3>📦 Exercise 4: Packaged API Modules</h3>");
            html.append("<p><b>Packaged Specification Constants:</b><br>• Minimum Compliant Bal: <code>$100.00</code><br>• Asset Surtax Rate: <code>2.5%</code></p>");
            html.append("<a href='/ex4-audit' class='btn btn-teal'>{CALL BankingPackage.Enforce_Audit(ID 1)}</a>");
            html.append("<a href='/ex4-tax' class='btn btn-teal'>SELECT BankingPackage.Compute_Tax(ID 1)</a>");
            html.append("</div>");

            // Legacy Integration Blocks
            html.append("<div class='card'><h3>🔗 Inherited Core Operational Blocks</h3>");
            html.append("<small style='color:#8e44ad;'><b>Exercise 3 Routines:</b></small><br><a href='/ex3-proc' class='btn btn-purple'>Penalty Procedure</a> <a href='/ex3-func' class='btn btn-purple'>Yield Function</a><br><br>");
            html.append("<small style='color:#e74c3c;'><b>Exercise 2 Error Catchers:</b></small><br><a href='/ex2-s1-fail' class='btn btn-danger'>Overdraft Exception</a> <a href='/ex2-s3-fail' class='btn btn-danger'>Constraint Violate</a><br><br>");
            html.append("<small style='color:#2980b9;'><b>Exercise 1 Active Cursors:</b></small><br><a href='/run-s1' class='btn'>Age Bonus Sweep</a> <a href='/run-s2' class='btn'>VIP Status Batch</a>");
            html.append("</div>");

            // Unified Compilation Console Window Output Box
            html.append("<div class='card' style='background:#1e293b; color:white;'><h3>🖥️ System Core Execution Logs</h3><div class='console'>").append(ex4ConsoleLogs).append("</div></div>");

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
        BankingEngineEx4 engine = new BankingEngineEx4();
        engine.initializeDatabase();           
        engine.initializeAdditionalSchemas();   
        engine.registerStoredProcedures();      
        engine.compileAndRegisterPackage();     
        engine.startWebServer();               
    }
}