package com.banking.platform;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;

/**
 * Exercise 6: Compound Triggers Engine (Inherits Exercise 5 down to 1).
 * Implements batch statement state validation mimicking PL/SQL Compound Triggers.
 */
public class BankingEngineEx6 extends BankingEngineEx5 {
    private static String ex6ConsoleLogs = "Compound Trigger System Active. Batch controls initialized.";
    private static int statementUpdateCount = 0; // Mimicking Compound State Variable

    // --- COMPOUND TRIGGER COMPLIANCE LOGIC ---
    
    public static class CompoundTriggerShim implements org.h2.api.Trigger {
        @Override
        public void init(Connection conn, String schemaName, String triggerName, String tableName, boolean before, int type) throws SQLException {}

        @Override
        public void fire(Connection conn, Object[] oldRow, Object[] newRow) throws SQLException {
            // BEFORE STATEMENT / BEFORE ROW logic combo
            // Simulating a state-based block that halts if a high-velocity batch threshold is breached
            statementUpdateCount++;
            if (statementUpdateCount > 10) {
                throw new SQLException("CRITICAL COMPLIANCE FAILURE: Compound Trigger Threshold Violated. Too many updates in a single statement block.");
            }
        }

        @Override
        public void close() throws SQLException {}
        @Override
        public void remove() throws SQLException {}
    }

    public void initializeCompoundSystem() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            // Reset state counter
            statementUpdateCount = 0;
            
            // Bind our compound trigger simulation object
            stmt.execute("DROP TRIGGER IF EXISTS trg_compound_compliance");
            stmt.execute("CREATE TRIGGER trg_compound_compliance BEFORE UPDATE ON Customers FOR EACH ROW CALL \"com.banking.platform.BankingEngineEx6$CompoundTriggerShim\"");
            
            System.out.println("[DB] PL/SQL Compound Trigger Simulation Armed and Listening.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Trigger an intentional high-velocity multi-row batch statement to trip the compound trigger rules
    public void runHighVelocityBatchUpdate() {
        StringBuilder log = new StringBuilder("<b>[Ex 6 - Compound Action Log]</b><br>");
        String batchSQL = "UPDATE Customers SET Balance = Balance + 10"; // Affects multiple rows simultaneously
        
        // Reset our state tracking counter for the start of the execution batch
        statementUpdateCount = 0; 
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            int rowsAffected = stmt.executeUpdate(batchSQL);
            log.append(String.format("✔️ COMPOUND BATCH SUCCESS: %d accounts updated collectively.<br>", rowsAffected));
            log.append("State counter tracked row actions inside a single query block safely.");
        } catch (SQLException e) {
            log.append("<span style='color:#e74c3c;'>Compound Interceptor Fired: " + e.getMessage() + "</span>");
        }
        ex6ConsoleLogs = log.toString();
    }

    // --- OVERRIDDEN INTEGRATED DASHBOARD SYSTEM ---

    @Override
    public void startWebServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/", new CompoundDashboardHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("=========================================================");
        System.out.println(" 🌐 MODULE 3 EXERCISE 6 OPERATIONAL: COMPOUND SYSTEM ARMED");
        System.out.println(" Point your clean web browser window to: http://localhost:8081");
        System.out.println("=========================================================");
    }

    private class CompoundDashboardHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String uri = exchange.getRequestURI().toString();

            // Match previous evolutionary routes
            if (uri.contains("/run-s1")) runScenario1_InterestDiscount();
            else if (uri.contains("/run-s2")) runScenario2_VIPPromotion();
            else if (uri.contains("/run-s3")) runScenario3_LoanReminders();
            else if (uri.contains("/ex2-s1-fail")) runSafeTransferFunds(1, 2, 99999.00);
            else if (uri.contains("/ex2-s1-pass")) runSafeTransferFunds(2, 1, 500.00);
            else if (uri.contains("/ex2-s2-fail")) runUpdateSalary(999, 10.0);
            else if (uri.contains("/ex2-s3-fail")) runAddNewCustomer(1, "Clone John", 500);
            else if (uri.contains("/ex3-proc")) executePenaltyProcedure(1, 5.0);
            else if (uri.contains("/ex3-func")) executeInterestFunction(2, 6.0);
            else if (uri.contains("/ex4-audit")) executePackageAudit(1);
            else if (uri.contains("/ex4-tax")) executePackageTaxCalculation(1);
            else if (uri.contains("/ex5-clear")) clearAuditLogs();
            
            // Exercise 6 route
            else if (uri.contains("/ex6-batch")) runHighVelocityBatchUpdate();

            StringBuilder html = new StringBuilder();
            html.append("<html><head><title>Module 3 Compound Trigger Matrix</title>");
            html.append("<style>body{font-family:'Segoe UI',Arial; margin:30px; background:#f4f6f9; color:#333;} ");
            html.append(".container{max-width:1200px; margin:auto;} ");
            html.append(".header{background:#4d7c0f; color:white; padding:25px; border-radius:6px; margin-bottom:20px; text-align:center;} ");
            html.append(".grid{display:grid; grid-template-columns: 1.4fr 1fr; gap:20px;} .card{background:white; padding:20px; border-radius:6px; box-shadow:0 4px 6px rgba(0,0,0,0.05); margin-bottom:20px;} ");
            html.append("table{width:100%; border-collapse:collapse; margin-top:10px;} th,td{padding:10px; border:1px solid #ddd; text-align:left;} th{background:#f8f9fa;} ");
            html.append(".btn{background:#2980b9; color:white; padding:6px 12px; border:none; border-radius:4px; text-decoration:none; font-weight:bold; display:inline-block; margin-right:5px; margin-top:5px; font-size:12px;} ");
            html.append(".btn-lime{background:#65a30d;} .btn-sky{background:#0284c7;} .btn-teal{background:#0d9488;} .btn-purple{background:#8e44ad;} .btn-danger{background:#e74c3c;} .btn:hover{opacity:0.9;} ");
            html.append(".console{background:#111; color:#00ff66; padding:15px; border-radius:5px; font-family:monospace; min-height:80px; line-height:1.5;}</style></head><body>");

            html.append("<div class='container'><div class='header'><h2>🌐 Module 3: Complete Enterprise PL/SQL Stack</h2><p>Integrated Pipeline Active: <b>Exercises 1-6 (Compound Trigger Processing Active)</b></p></div>");
            html.append("<div class='grid'><div>");

            // Live Database Views
            html.append("<div class='card' style='border-left: 5px solid #2980b9;'><h3>📦 Live Core Account Schema</h3>");
            html.append("<table><tr><th>ID</th><th>Account Profile Name</th><th>Live Balance</th><th>VIP Status</th></tr>");
            try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Customers")) {
                while (rs.next()) html.append(String.format("<tr><td>%d</td><td>%s</td><td>$%.2f</td><td><b>%s</b></td></tr>", rs.getInt("CustomerID"), rs.getString("Name"), rs.getDouble("Balance"), rs.getString("IsVIP")));
            } catch (SQLException ignored) {}
            html.append("</table></div>");

            // Automated Trigger Logs inherited from Ex 5
            html.append("<div class='card' style='border-left: 5px solid #0284c7;'><h3>🚨 Automated Trigger Audit Log Ledger</h3>");
            html.append("<table><tr style='background:#f0f9ff;'><th>Log ID</th><th>Holder Name</th><th>Old Balance</th><th>New Balance</th></tr>");
            try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM AuditLogs ORDER BY LogID DESC LIMIT 3")) {
                while (rs.next()) html.append(String.format("<tr><td><code>#%d</code></td><td>%s</td><td><del>$%.2f</del></td><td><b>$%.2f</b></td></tr>", rs.getInt("LogID"), rs.getString("CustomerName"), rs.getDouble("OldBalance"), rs.getDouble("NewBalance")));
            } catch (SQLException ignored) {}
            html.append("</table></div></div>");

            // Interactive Controls Pane
            html.append("<div>");
            
            // Exercise 6 control panel block
            html.append("<div class='card' style='border-top: 5px solid #65a30d; background:#f7fee7;'><h3>⚡ Exercise 6: Compound Constraints</h3>");
            html.append("<p style='color:#333; font-size:13px;'>Compound structures let state context persist across statements seamlessly to guard against high-frequency database mutations.</p>");
            html.append("<a href='/ex6-batch' class='btn btn-lime'>Execute Statement Batch Update</a>");
            html.append("</div>");

            // Legacy Pipelines
            html.append("<div class='card'><h3>🔗 Combined System Infrastructure Control</h3>");
            html.append("<small style='color:#0d9488;'><b>Ex 4 Packages:</b></small> <a href='/ex4-audit' class='btn btn-teal'>Package Audit</a><br>");
            html.append("<small style='color:#8e44ad;'><b>Ex 3 Procedures:</b></small> <a href='/ex3-proc' class='btn btn-purple'>Penalty Proc</a><br>");
            html.append("<small style='color:#e74c3c;'><b>Ex 2 Handling:</b></small> <a href='/ex2-s1-pass' class='btn btn-danger'>Valid Transfer</a>");
            html.append("</div>");

            // Shared compilation output pane
            html.append("<div class='card' style='background:#1e293b; color:white;'><h3>🖥️ System Interface Output Logs</h3><div class='console'>").append(ex6ConsoleLogs).append("</div></div>");

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
        BankingEngineEx6 engine = new BankingEngineEx6();
        engine.initializeDatabase();           
        engine.initializeAdditionalSchemas();   
        engine.registerStoredProcedures();      
        engine.compileAndRegisterPackage();     
        engine.initializeAuditSystem();         
        engine.initializeCompoundSystem();      // Deploy Exercise 6 multi-state trigger rules
        engine.startWebServer();               
    }
}