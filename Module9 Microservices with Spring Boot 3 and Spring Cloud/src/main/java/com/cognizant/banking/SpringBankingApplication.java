package com.cognizant.banking;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableEurekaServer // Activates the dynamic Service Discovery Server Registry
public class SpringBankingApplication {

    public static void main(String[] args) {
        // Safe cross-thread initialization block for context routing window
        @SuppressWarnings("unused")
		ConfigurableApplicationContext context = new SpringApplicationBuilder(SpringBankingApplication.class)
                .headless(false)
                .run(args);

        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        Color navyColor = new Color(44, 62, 80);
        Color canvasBg = new Color(245, 247, 250);

        JFrame frame = new JFrame("Hands-on: Eureka Discovery Registry Cluster Console");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(950, 520);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(canvasBg);

        // UI Header Top Panel Banner Banner
        JPanel banner = new JPanel();
        banner.setBackground(navyColor);
        banner.setBorder(new EmptyBorder(12, 10, 12, 10));
        JLabel title = new JLabel("Enterprise Core Banking Network - Dynamic Service Discovery Dashboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));
        banner.add(title);
        frame.add(banner, BorderLayout.NORTH);

        // Control Center Left Action Selection Panel Area Stack
        JPanel leftPanel = new JPanel(new GridLayout(3, 1, 10, 20));
        leftPanel.setBackground(canvasBg);
        leftPanel.setBorder(new EmptyBorder(25, 20, 25, 10));
        leftPanel.setPreferredSize(new Dimension(350, 400));

        JButton btnAccount = new JButton("💳 View Account Microservice (Normal Text)");
        btnAccount.setBackground(new Color(41, 128, 185));
        btnAccount.setForeground(Color.WHITE);
        btnAccount.setFont(new Font("Segoe UI", Font.BOLD, 12));

        JButton btnLoan = new JButton("💰 View Loan Microservice (Normal Text)");
        btnLoan.setBackground(new Color(39, 174, 96));
        btnLoan.setForeground(Color.WHITE);
        btnLoan.setFont(new Font("Segoe UI", Font.BOLD, 12));

        JButton btnEureka = new JButton("📡 Check Eureka Registration Map");
        btnEureka.setBackground(new Color(155, 89, 182));
        btnEureka.setForeground(Color.WHITE);
        btnEureka.setFont(new Font("Segoe UI", Font.BOLD, 12));

        leftPanel.add(btnAccount);
        leftPanel.add(btnLoan);
        leftPanel.add(btnEureka);
        frame.add(leftPanel, BorderLayout.WEST);

        // Monitor Plain Text Display Frame Window Log Output
        JTextArea consoleOutput = new JTextArea("Eureka Discovery Server Running. Press a control button to monitor routing maps...");
        consoleOutput.setFont(new Font("Consolas", Font.PLAIN, 13));
        consoleOutput.setEditable(false);
        frame.add(new JScrollPane(consoleOutput), BorderLayout.CENTER);

        // Wire Action Dispatch Interceptors
        btnAccount.addActionListener(e -> queryService("http://localhost:8761/accounts/details", consoleOutput));
        btnLoan.addActionListener(e -> queryService("http://localhost:8761/loans/details", consoleOutput));
        btnEureka.addActionListener(e -> queryService("http://localhost:8761/eureka/status", consoleOutput));

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void queryService(String urlStr, JTextArea output) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\n");
            }
            in.close();
            output.setText(sb.toString());
        } catch (Exception ex) {
            output.setText("Connection Trace Failure to target endpoint " + urlStr + "\nVerify server boot conditions: " + ex.getMessage());
        }
    }
}