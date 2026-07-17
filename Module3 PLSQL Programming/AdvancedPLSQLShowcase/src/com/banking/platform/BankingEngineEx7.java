package com.banking.platform;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 7: Object Types & Collections Capstone (Inherits Exercises 1 through 6).
 * Emulates PL/SQL User-Defined Object Types and Collection Arrays inside memory structures.
 */
public class BankingEngineEx7 extends BankingEngineEx6 {
    private static String ex7ConsoleLogs = "Capstone System Active. All Module 3 architectures compiled into the pipeline.";
    private static List<TransactionObject> transactionCollection = new ArrayList<>();

    // =========================================================================
    // 📦 SIMULATING PL/SQL OBJECT TYPES & COLLECTIONS
    // =========================================================================
    
    /**
     * Mimics a PL/SQL Custom OBJECT TYPE: CREATE OR REPLACE TYPE TransactionObject AS OBJECT (...)
     */
    public static class TransactionObject {
        public String type;
        public double amount;
        public String status;

        public TransactionObject(String type, double amount, String status) {
            this.type = type;
            this.amount = amount;
            this.status = status;
        }
    }

    // --- COLLECTION MANAGEMENT DATA HOOK ---

    public void populateObjectCollection() {
        // Simulating collecting database row instances into a PL/SQL Nested Table/Collection Array
        transactionCollection.clear();
        transactionCollection.add(new TransactionObject("Interest Sweep", 250.00, "COMPLETED"));
        transactionCollection.add(new TransactionObject("Tax Surtax Check", 125.50, "FLAGGED"));
        transactionCollection.add(new TransactionObject("Compliance Audit Adjust", 15.00, "SYSTEM_FORCED"));
        System.out.println("[DB] PL/SQL Object Type Collection Instantiated with " + transactionCollection.size() + " nested object frames.");
    }

    public void processCollectionPipeline() {
        StringBuilder log = new StringBuilder("<b>[Ex 7 - Object Collection Pipeline Out]</b><br>");
        log.append("Looping through User-Defined Collection Array (Nested Object Types):<br>");
        
        // Iterating over the collection elements just like bulk collecting rows in a PL/SQL loop
        for (int i = 0; i < transactionCollection.size(); i++) {
            TransactionObject obj = transactionCollection.get(i);
            log.append(String.format("• Element Index (%d) -> Type: <b>%s</b> | Sum: $%.2f | Security Code: <span style='color:#3b82f6;'>%s</span><br>", 
                    i, obj.type, obj.amount, obj.status));
        }
        ex7ConsoleLogs = log.toString();
    }

    // --- OVERRIDDEN COMPLETE SYSTEM DASHBOARD ---

    @Override
    public void startWebServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/", new CapstoneDashboardHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("=========================================================");
        System.out.println(" 🌐 COMPLETE CAPSTONE MATRIX OPERATIONAL: 7 OF 7 COMPLETE");
        System.out.println(" Point your final web browser window to: http://localhost:8081");
        System.out.println("=========================================================");
    }

    private class CapstoneDashboardHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String uri = exchange.getRequestURI().toString();

            // Match every single historic tier link safely
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
            else if (uri.contains("/ex6-batch")) runHighVelocityBatchUpdate();
            
            // New Exercise 7 route
            else if (uri.contains("/ex7-collect")) processCollectionPipeline();

            StringBuilder html = new StringBuilder();
            html.append("<html><head><title>Module 3 Capstone Matrix</title>");
            html.append("<style>body{font-family:'Segoe UI',Arial; margin:30px; background:#f4f6f9; color:#333;} ");
            html.append(".container{max-width:1200px; margin:auto;} ");
            html.append(".header{background:#1e3a8a; color:white; padding:25px; border-radius:6px; margin-bottom:20px; text-align:center;} ");
            html.append(".grid{display:grid; grid-template-columns: 1.4fr 1fr; gap:20px;} .card{background:white; padding:20px; border-radius:6px; box-shadow:0 4px 6px rgba(0,0,0,0.05); margin-bottom:20px;} ");
            html.append("table{width:100%; border-collapse:collapse; margin-top:10px;} th,td{padding:10px; border:1px solid #ddd; text-align:left;} th{background:#f8f9fa;} ");
            html.append(".btn{background:#2980b9; color:white; padding:6px 12px; border:none; border-radius:4px; text-decoration:none; font-weight:bold; display:inline-block; margin-right:5px; margin-top:5px; font-size:12px;} ");
            html.append(".btn-indigo{background:#4338ca;} .btn-lime{background:#65a30d;} .btn-teal{background:#0d9488;} .btn-purple{background:#8e44ad;} .btn-danger{background:#e74c3c;} .btn:hover{opacity:0.9;} ");
            html.append(".console{background:#111; color:#00ff66; padding:15px; border-radius:5px; font-family:monospace; min-height:80px; line-height:1.5;}</style></head><body>");

            html.append("<div class='container'><div class='header'><h2>🌐 Module 3: Complete Enterprise PL/SQL Stack</h2><p><b>Capstone Final Phase Active: Exercises 1-7 Fully Compiled</b></p></div>");
            html.append("<div class='grid'><div>");

            // Live Core Account Data Block
            html.append("<div class='card' style='border-left: 5px solid #2980b9;'><h3>📦 Live Core Account Schema</h3>");
            html.append("<table><tr><th>ID</th><th>Account Profile Name</th><th>Live Balance</th><th>VIP Status</th></tr>");
            try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Customers")) {
                while (rs.next()) html.append(String.format("<tr><td>%d</td><td>%s</td><td>$%.2f</td><td><b>%s</b></td></tr>", rs.getInt("CustomerID"), rs.getString("Name"), rs.getDouble("Balance"), rs.getString("IsVIP")));
            } catch (SQLException ignored) {}
            html.append("</table></div>");

            // Live Collection Object Type Output view 
            html.append("<div class='card' style='border-left: 5px solid #4338ca; background:#f5f3ff;'><h3>📦 Packaged Collection Array Registry (Exercise 7 Objects)</h3>");
            html.append("<table><tr style='background:#e0e7ff;'><th>Object Property Type</th><th>Asset Evaluation</th><th>Audit Trace Designation</th></tr>");
            for (TransactionObject tx : transactionCollection) {
                html.append(String.format("<tr><td><code>TYPE_OBJ.%s</code></td><td>$%.2f</td><td><b>%s</b></td></tr>", tx.type.toUpperCase().replace(" ", "_"), tx.amount, tx.status));
            }
            html.append("</table></div></div>");

            // Complete Interactive Controls Board
            html.append("<div>");
            
            // Exercise 7 controls block
            html.append("<div class='card' style='border-top: 5px solid #4338ca; background:#faf5ff;'><h3>💎 Exercise 7: Object Types & Collections</h3>");
            html.append("<p style='color:#333; font-size:13px;'>Executes bulk pipeline validation loops through user-defined object collection instances in local db cache fields.</p>");
            html.append("<a href='/ex7-collect' class='btn btn-indigo'>Process Object Type Collection Pipeline</a>");
            html.append("</div>");

            // Complete Legacy Pipelines 
            html.append("<div class='card'><h3>🔗 Legacy Structural Regression Pipeline Links</h3>");
            html.append("<small style='color:#65a30d;'><b>Ex 6 Triggers:</b></small> <a href='/ex6-batch' class='btn btn-lime'>Batch Update</a><br>");
            html.append("<small style='color:#0d9488;'><b>Ex 4 Packages:</b></small> <a href='/ex4-audit' class='btn btn-teal'>Package Audit</a><br>");
            html.append("<small style='color:#8e44ad;'><b>Ex 3 Procedures:</b></small> <a href='/ex3-proc' class='btn btn-purple'>Penalty Proc</a><br>");
            html.append("<small style='color:#e74c3c;'><b>Ex 2 Exceptions:</b></small> <a href='/ex2-s1-pass' class='btn btn-danger'>Transfer Pass</a><br>");
            html.append("<small style='color:#2980b9;'><b>Ex 1 Cursors:</b></small> <a href='/run-s1' class='btn'>Age Bonus Sweep</a>");
            html.append("</div>");

            // Universal execution output terminal
            html.append("<div class='card' style='background:#1e293b; color:white;'><h3>🖥️ Capstone Central Interface Logs</h3><div class='console'>").append(ex7ConsoleLogs).append("</div></div>");

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
        BankingEngineEx7 engine = new BankingEngineEx7();
        engine.initializeDatabase();           
        engine.initializeAdditionalSchemas();   
        engine.registerStoredProcedures();      
        engine.compileAndRegisterPackage();     
        engine.initializeAuditSystem();         
        engine.initializeCompoundSystem();      
        engine.populateObjectCollection();      // Construct Exercise 7 mock database record instances
        engine.startWebServer();               
    }
}