package com.platform.core;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * The Interactive Web UI Prototype Layer wrapping all 7 DSA Exercises.
 */
public class EnterpriseWebDashboard {

    private static final FinalEnterpriseEngine backendEngine = new FinalEnterpriseEngine(10);

    public static void main(String[] args) throws IOException {
        // Seed initial data baseline into the engine structures
        backendEngine.addProduct(new Product("P001", "MacBook Pro M3", 12, 1999.99, "Electronics"));
        backendEngine.addProduct(new Product("P002", "Mechanical Keyboard", 45, 129.50, "Accessories"));
        backendEngine.addProduct(new Product("P003", "UltraWide Monitor 34", 8, 599.00, "Electronics"));
        
        backendEngine.addEmployee(new Employee("E001", "Alice Vance", "Chief Architect", 165000));
        backendEngine.addEmployee(new Employee("E002", "Gordon Freeman", "Research Lead", 95000));
        
        backendEngine.addTask(new Task("T101", "Optimize DB Indexing", "In-Progress"));
        backendEngine.addTask(new Task("T102", "Deploy Security Patches", "Pending"));

        // Spin up the web server on port 8081 (to avoid any conflicts)
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/", new DashboardHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("=========================================================");
        System.out.println(" 🌐 PROTOTYPE DASHBOARD IS LIVE!");
        System.out.println(" Open your browser and go to: http://localhost:8081");
        System.out.println("=========================================================");
    }

    static class DashboardHandler implements HttpHandler {
        // Core mock data tracking for runtime interaction
        private static Order[] dynamicOrders = {
            new Order("O101", "Customer A", 150.00),
            new Order("O102", "Customer B", 2400.50),
            new Order("O103", "Customer C", 45.99),
            new Order("O104", "Customer D", 899.00)
        };
        private static String searchResult = "Enter an ID to search.";
        private static String recursiveResult = "Enter parameters to run forecast.";
        private static boolean isSorted = false;

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String uri = exchange.getRequestURI().toString();

            // --- INTERACTION ROUTER ---
            if (uri.contains("/sort-orders")) {
                backendEngine.quickSortOrders(dynamicOrders, 0, dynamicOrders.length - 1);
                isSorted = true;
            } else if (uri.contains("/search-product")) {
                String queryId = getParam(uri, "id");
                Product match = backendEngine.binarySearchById(queryId);
                searchResult = (match != null) ? "FOUND: " + match.toString() : "Product ID not found.";
            } else if (uri.contains("/run-forecast")) {
                try {
                    double capital = Double.parseDouble(getParam(uri, "capital"));
                    int years = Integer.parseInt(getParam(uri, "years"));
                    double finalVal = backendEngine.calculateFutureValue(capital, 0.08, years);
                    recursiveResult = String.format("Future Value after %d years (at 8%% growth): $%.2f", years, finalVal);
                } catch (Exception e) {
                    recursiveResult = "Invalid inputs provided.";
                }
            }

            // --- HTML VIEW GENERATION ---
            StringBuilder html = new StringBuilder();
            html.append("<html><head><title>DSA Enterprise Control Engine</title>");
            html.append("<style>body{font-family:'Segoe UI',Arial; margin:30px; background:#f4f7f6; color:#333;} ");
            html.append(".container{max-width:1100px; margin:auto;} .header{background:#2c3e50; color:white; padding:20px; border-radius:8px; margin-bottom:20px;} ");
            html.append(".grid{display:grid; grid-template-columns: 1fr 1fr; gap:20px;} ");
            html.append(".card{background:white; padding:20px; border-radius:8px; box-shadow:0 4px 6px rgba(0,0,0,0.05);} ");
            html.append("h3{color:#2c3e50; border-bottom:2px solid #ecf0f1; padding-bottom:8px;} ");
            html.append("table{width:100%; border-collapse:collapse; margin-top:10px;} th,td{padding:10px; border:1px solid #ddd; text-align:left;} th{background:#f8f9fa;} ");
            html.append(".btn{background:#3498db; color:white; padding:8px 15px; border:none; border-radius:4px; text-decoration:none; font-weight:bold; display:inline-block; margin-top:10px;} ");
            html.append(".btn:hover{background:#2980b9;} .alert{background:#e8f4fd; padding:12px; border-left:4px solid #3498db; margin-top:10px; font-family:monospace;} ");
            html.append("input[type=text]{padding:8px; width:200px; margin-right:10px; border:1px solid #ccc; border-radius:4px;}</style></head><body>");

            html.append("<div class='container'><div class='header'><h2>Module 1 Data Structures & Algorithms Working Prototype</h2><p>All exercises interacting inside a single live runtime ecosystem.</p></div>");
            
            html.append("<div class='grid'>");

            // Card 1: HashMap Inventory (Exercise 1)
            html.append("<div class='card'><h3>📦 Ex 1: Inventory Live View (O(1) HashMap)</h3><table><tr><th>ID</th><th>Name</th><th>Category</th><th>Price</th></tr>");
            for (Product p : backendEngine.productMap.values()) {
                html.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>$%.2f</td></tr>", p.getProductId(), p.getProductName(), p.getCategory(), p.getPrice()));
            }
            html.append("</table></div>");

            // Card 2: Binary Search Engine (Exercise 2 & 6)
            html.append("<div class='card'><h3>🔍 Ex 2 & 6: Optimized Search (O(log n) Binary Search)</h3>");
            html.append("<form action='/search-product' method='GET'><input type='text' name='id' placeholder='Enter Product ID (e.g. P002)' required><input type='submit' class='btn' value='Run Binary Search'></form>");
            html.append("<div class='alert'>").append(searchResult).append("</div></div>");

            // Card 3: Quick Sort Orders (Exercise 3)
            html.append("<div class='card'><h3>📊 Ex 3: Order Prioritization (O(n log n) Quick Sort)</h3>");
            html.append("<table><tr><th>Order ID</th><th>Customer</th><th>Value</th></tr>");
            for (Order o : dynamicOrders) {
                html.append(String.format("<tr><td>%s</td><td>%s</td><td>$%.2f</td></tr>", o.getOrderId(), o.getCustomerName(), o.getTotalPrice()));
            }
            html.append("</table>");
            html.append("<a href='/sort-orders' class='btn' style='background:#2ecc71;'>").append(isSorted ? "Orders Prioritized!" : "Run Quick Sort").append("</a></div>");

            // Card 4: Financial Forecasting (Exercise 7)
            html.append("<div class='card'><h3>📈 Ex 7: Capital Forecaster (O(n) Stack Recursion)</h3>");
            html.append("<form action='/run-forecast' method='GET'>");
            html.append("<input type='text' name='capital' placeholder='Initial Capital ($)' style='width:130px;' required> ");
            html.append("<input type='text' name='years' placeholder='Years' style='width:70px;' required> ");
            html.append("<input type='submit' class='btn' value='Run Recursion'></form>");
            html.append("<div class='alert'>").append(recursiveResult).append("</div></div>");

            html.append("</div></div></body></html>");

            // Send HTTP response headers and body
            byte[] bytes = html.toString().getBytes();
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }

        // Helper to extract clean query string variables from URL
        private String getParam(String uri, String paramName) {
            try {
                String[] pairs = uri.split("\\?")[1].split("&");
                for (String pair : pairs) {
                    String[] kv = pair.split("=");
                    if (kv[0].equals(paramName)) return kv[1];
                }
            } catch (Exception e) { return ""; }
            return "";
        }
    }
}