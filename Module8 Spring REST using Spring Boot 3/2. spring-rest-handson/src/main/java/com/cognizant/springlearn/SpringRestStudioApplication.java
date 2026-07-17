package com.cognizant.springlearn;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringRestStudioApplication {

    public static void main(String[] args) {
        @SuppressWarnings("unused")
		ConfigurableApplicationContext context = new SpringApplicationBuilder(SpringRestStudioApplication.class)
                .headless(false)
                .run(args);

        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        Color navyColor = new Color(52, 73, 94);
        Color canvasBg = new Color(245, 247, 250);

        JFrame frame = new JFrame("Hands-on 1, 2 & 3: Master Spring REST Studio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(920, 560);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(canvasBg);

        // Title Header Banner
        JPanel bannerPanel = new JPanel();
        bannerPanel.setBackground(navyColor);
        bannerPanel.setBorder(new EmptyBorder(12, 10, 12, 10));
        JLabel titleLabel = new JLabel("Unified Spring Boot 3 Web REST API Dashboard & Client");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bannerPanel.add(titleLabel);
        frame.add(bannerPanel, BorderLayout.NORTH);

        // Control Panel Area (Left Side Stacked layouts)
        JPanel leftControlPanel = new JPanel();
        leftControlPanel.setLayout(new BoxLayout(leftControlPanel, BoxLayout.Y_AXIS));
        leftControlPanel.setBackground(canvasBg);
        leftControlPanel.setBorder(new EmptyBorder(15, 20, 15, 10));
        leftControlPanel.setPreferredSize(new Dimension(360, 500));

        // Hands-on 1 & 2 Global Read actions
        JButton btnFetchAll = new JButton("🌍 Fetch All Countries (GET /countries)");
        btnFetchAll.setBackground(new Color(41, 128, 185));
        btnFetchAll.setForeground(Color.WHITE);
        btnFetchAll.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnFetchAll.setMaximumSize(new Dimension(330, 32));

        // Hands-on 3: Path Variable Input Fields
        JLabel lblSectionSearch = new JLabel("--- Hands-on 3: Find By Code (PathVariable) ---");
        lblSectionSearch.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblSectionSearch.setBorder(new EmptyBorder(15, 0, 5, 0));
        
        JPanel searchRow = new JPanel(new BorderLayout(5, 0));
        searchRow.setBackground(canvasBg);
        searchRow.setMaximumSize(new Dimension(330, 30));
        JTextField txtSearchCode = new JTextField("IN");
        JButton btnSearchCode = new JButton("🔍 Find Country");
        btnSearchCode.setBackground(new Color(155, 89, 182));
        btnSearchCode.setForeground(Color.WHITE);
        searchRow.add(txtSearchCode, BorderLayout.CENTER);
        searchRow.add(btnSearchCode, BorderLayout.EAST);

        // Hands-on 2 Form Section for Insertion
        JLabel lblSectionForm = new JLabel("--- Add New Country Record (POST /countries) ---");
        lblSectionForm.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblSectionForm.setBorder(new EmptyBorder(25, 0, 5, 0));

        JLabel lblCode = new JLabel("New Country Code:");
        JTextField txtCode = new JTextField();
        txtCode.setMaximumSize(new Dimension(330, 28));

        JLabel lblName = new JLabel("New Country Name:");
        JTextField txtName = new JTextField();
        txtName.setMaximumSize(new Dimension(330, 28));

        JButton btnAddCountry = new JButton("💾 Insert New Country Into Database");
        btnAddCountry.setBackground(new Color(39, 174, 96));
        btnAddCountry.setForeground(Color.WHITE);
        btnAddCountry.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnAddCountry.setMaximumSize(new Dimension(330, 32));

        // Construct Left Element Tree
        leftControlPanel.add(btnFetchAll);
        leftControlPanel.add(lblSectionSearch);
        leftControlPanel.add(searchRow);
        leftControlPanel.add(lblSectionForm);
        leftControlPanel.add(lblCode);
        leftControlPanel.add(txtCode);
        leftControlPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        leftControlPanel.add(lblName);
        leftControlPanel.add(txtName);
        leftControlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftControlPanel.add(btnAddCountry);
        frame.add(leftControlPanel, BorderLayout.WEST);

        // Console Logging Terminal Panel (Right Side Window)
        JTextArea consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
        consoleOutput.setFont(new Font("Consolas", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(consoleOutput);
        scrollPane.setBorder(BorderFactory.createTitledBorder("REST Network Response Body View Stream"));

        JPanel textWrapper = new JPanel(new BorderLayout());
        textWrapper.setBackground(canvasBg);
        textWrapper.setBorder(new EmptyBorder(15, 10, 20, 20));
        textWrapper.add(scrollPane, BorderLayout.CENTER);
        frame.add(textWrapper, BorderLayout.CENTER);

        // --- GRAPHICAL INTERACTION LISTENERS ---

        // Action 1: Get All Countries
        btnFetchAll.addActionListener(e -> {
            executeRequest("http://localhost:8085/countries", "GET", null, consoleOutput);
        });

        // Action 2: Get Single Country via Path Variable (Hands-on 3)
        btnSearchCode.addActionListener(e -> {
            String targetCode = txtSearchCode.getText().trim();
            if (targetCode.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a country code first!", "Input Missing", JOptionPane.WARNING_MESSAGE);
                return;
            }
            executeRequest("http://localhost:8085/countries/" + targetCode, "GET", null, consoleOutput);
        });

        // Action 3: Post/Save New Country
        btnAddCountry.addActionListener(e -> {
            String codeVal = txtCode.getText().trim().toUpperCase();
            String nameVal = txtName.getText().trim();

            if (codeVal.isEmpty() || nameVal.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please complete all fields!", "Form Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String jsonPayload = String.format("{\"code\": \"%s\", \"name\": \"%s\"}", codeVal, nameVal);
            executeRequest("http://localhost:8085/countries", "POST", jsonPayload, consoleOutput);
            
            txtCode.setText("");
            txtName.setText("");
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void executeRequest(String urlString, String method, String payload, JTextArea output) {
        output.setText("Connecting to server URL target: " + urlString + " ...");
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Accept", "application/json");

            if ("POST".equalsIgnoreCase(method) && payload != null) {
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = payload.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            int code = conn.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    code >= 200 && code < 300 ? conn.getInputStream() : conn.getErrorStream(), StandardCharsets.UTF_8));
            
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\n");
            }
            in.close();

            output.setText(
                "HTTP Status Code: " + code + "\n" +
                "=========================================\n" +
                "Response Data JSON Layout:\n\n" + sb.toString()
            );

        } catch (Exception ex) {
            output.setText("Network Exception Handled:\n" + ex.getMessage());
        }
    }
}