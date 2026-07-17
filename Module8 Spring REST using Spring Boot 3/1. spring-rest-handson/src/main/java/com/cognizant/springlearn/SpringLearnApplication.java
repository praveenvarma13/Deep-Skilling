package com.cognizant.springlearn;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringLearnApplication {

    public static void main(String[] args) {
        @SuppressWarnings("unused")
		ConfigurableApplicationContext context = new SpringApplicationBuilder(SpringLearnApplication.class)
                .headless(false)
                .run(args);

        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        Color darkBlue = new Color(44, 62, 80);
        Color lightGray = new Color(240, 244, 248);

        JFrame frame = new JFrame("Hands-on 2 & 4: Spring REST & Core IoC Studio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 480);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(lightGray);

        // Top Banner Title Panel
        JPanel bannerPanel = new JPanel();
        bannerPanel.setBackground(darkBlue);
        bannerPanel.setBorder(new EmptyBorder(12, 10, 12, 10));
        JLabel titleLabel = new JLabel("Spring Boot 3 Multi-Tasking Rest Endpoint Console Panel");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bannerPanel.add(titleLabel);
        frame.add(bannerPanel, BorderLayout.NORTH);

        // Input and Control Panel (Left Side)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(lightGray);
        inputPanel.setBorder(new EmptyBorder(20, 25, 20, 15));
        inputPanel.setPreferredSize(new Dimension(360, 400));

        // Hands-on 2 Controls
        JLabel lblSection1 = new JLabel("--- Hands-on 2: SimpleDateFormat Bean ---");
        lblSection1.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblSection1.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField txtDateInput = new JTextField("31/12/2018"); 
        txtDateInput.setMaximumSize(new Dimension(340, 30));
        txtDateInput.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnSubmitDate = new JButton("📡 Parse Date REST Request");
        btnSubmitDate.setBackground(new Color(41, 128, 185));
        btnSubmitDate.setForeground(Color.WHITE);
        btnSubmitDate.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Hands-on 4 Controls
        JLabel lblSection2 = new JLabel("--- Hands-on 4: Load Country Bean ---");
        lblSection2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblSection2.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnGetCountry = new JButton("🌍 Retrieve Country XML Bean");
        btnGetCountry.setBackground(new Color(39, 174, 96));
        btnGetCountry.setForeground(Color.WHITE);
        btnGetCountry.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Assemble Left panel layout
        inputPanel.add(lblSection1);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        inputPanel.add(txtDateInput);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        inputPanel.add(btnSubmitDate);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 35))); // Space spacer separator
        inputPanel.add(lblSection2);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(btnGetCountry);
        frame.add(inputPanel, BorderLayout.WEST);

        // Console Response Logger Area (Right Side)
        JTextArea txtOutputConsole = new JTextArea();
        txtOutputConsole.setEditable(false);
        txtOutputConsole.setFont(new Font("Consolas", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(txtOutputConsole);
        scrollPane.setBorder(BorderFactory.createTitledBorder("REST Server Response Output (JSON Layout Stream)"));

        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.setBackground(lightGray);
        centerContainer.setBorder(new EmptyBorder(15, 10, 20, 20));
        centerContainer.add(scrollPane, BorderLayout.CENTER);
        frame.add(centerContainer, BorderLayout.CENTER);

        // --- INTERACTION BUTTON LISTENERS ---

        // Action 1: Parse Date (Hands-on 2)
        btnSubmitDate.addActionListener(e -> {
            txtOutputConsole.setText("Connecting to server for date parsing...");
            try {
                String inputDate = txtDateInput.getText().trim();
                String encodedDate = URLEncoder.encode(inputDate, StandardCharsets.UTF_8.toString());
                URL url = new URL("http://localhost:8085/api/date/parse?dateStr=" + encodedDate);
                executeGetRequest(url, txtOutputConsole);
            } catch (Exception ex) {
                txtOutputConsole.setText("Error matching runtime request:\n" + ex.getMessage());
            }
        });

        // Action 2: Load Country Bean (Hands-on 4)
        btnGetCountry.addActionListener(e -> {
            txtOutputConsole.setText("Fetching country bean mapping graph from IoC Context...");
            try {
                URL url = new URL("http://localhost:8085/api/country");
                executeGetRequest(url, txtOutputConsole);
            } catch (Exception ex) {
                txtOutputConsole.setText("Error matching runtime request:\n" + ex.getMessage());
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void executeGetRequest(URL url, JTextArea displayConsole) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(
                conn.getResponseCode() == 200 ? conn.getInputStream() : conn.getErrorStream()));
        
        String inputLine;
        StringBuilder responseJson = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            responseJson.append(inputLine).append("\n");
        }
        in.close();

        displayConsole.setText("HTTP Status Code: " + conn.getResponseCode() + "\n\n" + responseJson.toString());
    }
}