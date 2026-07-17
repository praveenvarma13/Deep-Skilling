package com.banking.platform;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;

/**
 * Exercise 5: Database Triggers Engine (Inherits Exercise 4, 3, 2 & 1).
 * Registers an automated audit log trigger that captures all live balance changes.
 */
public class BankingEngineEx5 extends BankingEngineEx4 {
    private static String ex5ConsoleLogs = "Trigger Engine Active. Modify any account to see automated trigger logs below.";

    // --- AUTOMATED AUDIT TRIGGER IMPLEMENTATION ---
    
    /**
     * H2-compatible Trigger class. This acts exactly like an AFTER UPDATE PL/SQL trigger.
     */
    public static class AuditTrigger implements org.h2.api.Trigger {
        @Override
        public void init(Connection conn, String schemaName, String triggerName, String tableName, boolean before, int type) throws SQLException {}

        @Override
        public void fire(Connection conn, Object[] oldRow, Object[] newRow) throws SQLException {
            // Check if it's an UPDATE operation and if the Balance has actually changed
            if (oldRow != null && newRow != null) {
                int customerId = (Integer) oldRow[0];
                String name = (String) oldRow[1];
                double oldBalance = (Double) oldRow[3];
                double newBalance = (Double) newRow[3];

                if (oldBalance != newBalance) {
                    String logSQL = "INSERT INTO AuditLogs (CustomerID, CustomerName, OldBalance, NewBalance, LogTimestamp) VALUES (?, ?, ?, ?, NOW())";
                    try (PreparedStatement ps = conn.prepareStatement(logSQL)) {
                        ps.setInt(1, customerId);
                        ps.setString(2, name);
                        ps.setDouble(3, oldBalance);
                        ps.setDouble(4, newBalance);
                        ps.executeUpdate();
                    }
                }
            }
        }

        @Override
        public void close() throws SQLException {}

        @Override
        public void remove() throws SQLException {}
    }

    // --- SETUP AUDITING STORAGE & INTERCEPTOR LINK ---

    public void initializeAuditSystem() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            // Create Audit Table to store captured rows
            stmt.execute("CREATE TABLE IF NOT EXISTS AuditLogs (" +
                         "LogID INT AUTO_INCREMENT PRIMARY KEY, " +
                         "CustomerID INT, " +
                         "CustomerName VARCHAR(100), " +
                         "OldBalance NUMBER, " +
                         "NewBalance NUMBER, " +
                         "LogTimestamp TIMESTAMP)");
            
            // Clear prior session audit records
            stmt.execute("DELETE FROM AuditLogs");
            
            // Register our Java AuditTrigger as an AFTER UPDATE database trigger hook
            stmt.execute("DROP TRIGGER IF EXISTS trg_account_balance_audit");
            stmt.execute("CREATE TRIGGER trg_account_balance_audit AFTER UPDATE ON Customers FOR EACH ROW CALL \"com.banking.platform.BankingEngineEx5$AuditTrigger\"");
            
            System.out.println("[DB] PL/SQL Trigger 'trg_account_balance_audit' Compiled and Armed Successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Manual Reset option for the UI
    public void clearAuditLogs() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM AuditLogs");
            ex5ConsoleLogs = "🧹 Audit Log Table Purged Successfully.";
        } catch (SQLException e) {
            ex5ConsoleLogs = "Error clearing logs: " + e.getMessage();
        }
    }

    // --- OVERRIDDEN FULL-STACK INTERACTION CONTROLLER ---

    @Override
    public void startWebServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/", new TriggerStackDashboardHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("=========================================================");
        System.out.println(" 🌐 MODULE 3 EXERCISE 5 OPERATIONAL: TRIGGER ARMED LIVE");
        System.out.println(" Point your browser tab to: http://localhost:8081");
        System.out.println("=========================================================");
    }

    private class TriggerStackDashboardHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String uri = exchange.getRequestURI().toString();

            // Match all inherited routes seamlessly
            if (uri.contains("/run-s1")) runScenario1_InterestDiscount();
            else if (uri.contains("/run-s2")) runScenario2_VIPPromotion();
            else if (uri.contains("/run-s3")) runScenario3_LoanReminders();
            else if (uri.contains("/ex2-s1-fail")) runSafeTransferFunds(1, 2, 99999.00);
            else if (uri.contains("/ex2-s1-pass")) runSafeTransferFunds(2, 1, 500.00);
            else if (uri.contains("/ex2-s2-fail")) runUpdateSalary(999, 10.0);
            else if (uri.contains("/ex2-s3-fail")) runAddNewCustomer(1, "Clone John", 500);
            else if (uri.contains("/ex3-proc")) { executePenaltyProcedure(1, 5.0); ex5ConsoleLogs = "Executed Ex 3 Penalty Procedure."; }
            else if (uri.contains("/ex3-func")) { executeInterestFunction(2, 6.0); ex5ConsoleLogs = "Executed Ex 3 Yield Function."; }
            else if (uri.contains("/ex4-audit")) { executePackageAudit(1); ex5ConsoleLogs = "Executed Ex 4 Package Audit."; }
            else if (uri.contains("/ex4-tax")) { executePackageTaxCalculation(1); ex5ConsoleLogs = "Executed Ex 4 Package Tax Utility."; }
            
            // New Exercise 5 routes
            else if (uri.contains("/ex5-clear")) clearAuditLogs();

            StringBuilder html = new StringBuilder();
            html.append("<html><head><title>Module 3 Trigger Matrix</title>");
            html.append("<style>body{font-family:'Segoe UI',Arial; margin:30px; background:#f4f6f9; color:#333;} ");
            html.append(".container{max-width:1200px; margin:auto;} ");
            html.append(".header{background:#0369a1; color:white; padding:25px; border-radius:6px; margin-bottom:20px; text-align:center;} ");
            html.append(".grid{display:grid; grid-template-columns: 1.4fr 1fr; gap:20px;} .card{background:white; padding:20px; border-radius:6px; box-shadow:0 4px 6px rgba(0,0,0,0.05); margin-bottom:20px;} ");
            html.append("table{width:100%; border-collapse:collapse; margin-top:10px;} th,td{padding:10px; border:1px solid #ddd; text-align:left;} th{background:#f8f9fa;} ");
            html.append(".btn{background:#2980b9; color:white; padding:6px 12px; border:none; border-radius:4px; text-decoration:none; font-weight:bold; display:inline-block; margin-right:5px; margin-top:5px; font-size:12px;} ");
            html.append(".btn-sky{background:#0284c7;} .btn-teal{background:#0d9488;} .btn-purple{background:#8e44ad;} .btn-danger{background:#e74c3c;} .btn:hover{opacity:0.9;} ");
            html.append(".console{background:#111; color:#00ff66; padding:15px; border-radius:5px; font-family:monospace; min-height:80px; line-height:1.5;}</style></head><body>");

            html.append("<div class='container'><div class='header'><h2>🌐 Module 3: Complete Enterprise PL/SQL Stack</h2><p>Integrated Pipeline Active: <b>Exercises 1-5 (Automated Triggers Enabled)</b></p></div>");
            html.append("<div class='grid'><div>");

            // --- DATA DISPLAY 1: CORE CUSTOMERS ---
            html.append("<div class='card' style='border-left: 5px solid #2980b9;'><h3>📦 Live Core Account Schema</h3>");
            html.append("<table><tr><th>ID</th><th>Account Profile Name</th><th>Live Balance</th><th>VIP Status</th></tr>");
            try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Customers")) {
                while (rs.next()) html.append(String.format("<tr><td>%d</td><td>%s</td><td>$%.2f</td><td><b>%s</b></td></tr>", rs.getInt("CustomerID"), rs.getString("Name"), rs.getDouble("Balance"), rs.getString("IsVIP")));
            } catch (SQLException ignored) {}
            html.append("</table></div>");

            // --- NEW DATA DISPLAY 2: ACTIVE TRIGGER AUDIT TRACKER LOGS ---
            html.append("<div class='card' style='border-left: 5px solid #0369a1; background:#f0f9ff;'><h3>🚨 Automated PL/SQL Trigger Audit Log View</h3>");
            html.append("<p style='margin-top: -10px; color:#555;'><small>Fires instantly <code>AFTER UPDATE OF Balance</code> on any table modification hook.</small></p>");
            html.append("<table><tr style='background:#e0f2fe;'><th>Log ID</th><th>Holder Name (ID)</th><th>Old Balance</th><th>New Balance</th><th>Timestamp</th></tr>");
            try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM AuditLogs ORDER BY LogID DESC")) {
                boolean recordsExist = false;
                while (rs.next()) {
                    recordsExist = true;
                    html.append(String.format("<tr><td><code>#%d</code></td><td>%s (ID %d)</td><td><del>$%.2f</del></td><td><b style='color:#0369a1;'>$%.2f</b></td><td><small>%s</small></td></tr>",
                            rs.getInt("LogID"), rs.getString("CustomerName"), rs.getInt("CustomerID"), rs.getDouble("OldBalance"), rs.getDouble("NewBalance"), rs.getTimestamp("LogTimestamp").toString().substring(11, 19)));
                }
                if (!recordsExist) {
                    html.append("<tr><td colspan='5' style='text-align:center; color:#777;'><i>No triggers tripped yet. Fire an operational action on the right panel to test!</i></td></tr>");
                }
            } catch (SQLException ignored) {}
            html.append("</table><br><a href='/ex5-clear' class='btn btn-danger' style='padding:4px 8px; font-size:11px;'>Clear Audit Ledger</a></div></div>");

            // --- CONTROLS COLUMN ---
            html.append("<div>");
            
            // Exercise 5 Information Block
            html.append("<div class='card' style='border-top: 5px solid #0369a1;'><h3>⚡ Exercise 5: Trigger System</h3>");
            html.append("<p style='color:#444; font-size:13px;'>The trigger is globally bound directly to the database layer. Clicking buttons below from earlier assignments will automatically fire the logging event!</p>");
            html.append("</div>");

            // Legacy Integration Blocks (All previous exercises remaining completely active)
            html.append("<div class='card'><h3>🔗 Combined System Infrastructure Control</h3>");
            html.append("<small style='color:#0d9488;'><b>Ex 4 Packaged API Modules:</b></small><br><a href='/ex4-audit' class='btn btn-teal'>Package Balance Audit</a> <a href='/ex4-tax' class='btn btn-teal'>Compute Package Tax</a><br><br>");
            html.append("<small style='color:#8e44ad;'><b>Ex 3 Stored Routines:</b></small><br><a href='/ex3-proc' class='btn btn-purple'>Penalty Procedure</a> <a href='/ex3-func' class='btn btn-purple'>Yield Function</a><br><br>");
            html.append("<small style='color:#e74c3c;'><b>Ex 2 Exception Controls:</b></small><br><a href='/ex2-s1-pass' class='btn'>Valid Transfer (Pass)</a><br><br>");
            html.append("<small style='color:#2980b9;'><b>Ex 1 Active Cursor Loops:</b></small><br><a href='/run-s1' class='btn'>Age Bonus Sweep</a> <a href='/run-s2' class='btn'>VIP Status Batch</a>");
            html.append("</div>");

            // Core Dashboard Console Terminal
            html.append("<div class='card' style='background:#1e293b; color:white;'><h3>🖥️ System Interface Output Logs</h3><div class='console'>").append(ex5ConsoleLogs).append("</div></div>");

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
        BankingEngineEx5 engine = new BankingEngineEx5();
        engine.initializeDatabase();           // Deploy Ex 1 Baseline schemas
        engine.initializeAdditionalSchemas();   // Deploy Ex 2 Staff tables
        engine.registerStoredProcedures();      // Inject Ex 3 Stored Procedures 
        engine.compileAndRegisterPackage();     // Assemble Ex 4 System Package structures
        engine.initializeAuditSystem();         // Bind and arm the Exercise 5 Trigger system
        engine.startWebServer();               // Fire up the unified engine view
    }
}