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
public class SpringJwtApplication {

    public static void main(String[] args) {
        @SuppressWarnings("unused")
		ConfigurableApplicationContext context = new SpringApplicationBuilder(SpringJwtApplication.class)
                .headless(false)
                .run(args);

        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        Color navyColor = new Color(44, 62, 80);
        Color canvasBg = new Color(245, 247, 250);

        JFrame frame = new JFrame("Mandatory Hands-on: Enterprise JWT Generator Studio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 480);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(canvasBg);

        // Top Banner
        JPanel banner = new JPanel();
        banner.setBackground(navyColor);
        banner.setBorder(new EmptyBorder(12, 10, 12, 10));
        JLabel title = new JLabel("JSON Web Token (JWT) Secure Claims Authentication Station");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));
        banner.add(title);
        frame.add(banner, BorderLayout.NORTH);

        // Control Panel Area (Left Side Form Layout)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(canvasBg);
        leftPanel.setBorder(new EmptyBorder(20, 20, 20, 10));
        leftPanel.setPreferredSize(new Dimension(340, 400));

        JLabel lblFormTitle = new JLabel("--- Account Credentials Log In ---");
        lblFormTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblFormTitle.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel lblUser = new JLabel("Username:");
        JTextField txtUser = new JTextField("user");
        txtUser.setMaximumSize(new Dimension(300, 30));

        JLabel lblPwd = new JLabel("Password:");
        JPasswordField txtPwd = new JPasswordField("pwd");
        txtPwd.setMaximumSize(new Dimension(300, 30));

        JButton btnLogin = new JButton("🔐 Request Secure JWT String");
        btnLogin.setBackground(new Color(46, 204, 113));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLogin.setMaximumSize(new Dimension(300, 38));

        leftPanel.add(lblFormTitle);
        leftPanel.add(lblUser);
        leftPanel.add(txtUser);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        leftPanel.add(lblPwd);
        leftPanel.add(txtPwd);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(btnLogin);
        frame.add(leftPanel, BorderLayout.WEST);

        // Terminal Log Area (Right Panel View Window)
        JTextArea outputArea = new JTextArea("System Initialized. Enter correct parameters to request JWT authorization signature stream...");
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Secured Token Response Body Monitor Log"));
        
        JPanel textWrapper = new JPanel(new BorderLayout());
        textWrapper.setBackground(canvasBg);
        textWrapper.setBorder(new EmptyBorder(15, 10, 20, 20));
        textWrapper.add(scrollPane, BorderLayout.CENTER);
        frame.add(textWrapper, BorderLayout.CENTER);

        // --- AUTHENTICATION INTERACTIVE LOGIC ---
        btnLogin.addActionListener(e -> {
            String userVal = txtUser.getText().trim();
            String pwdVal = new String(txtPwd.getPassword()).trim();

            if (userVal.isEmpty() || pwdVal.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please clear missing parameters first!", "Form Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            outputArea.setText("Connecting to authentication gateway endpoint target http://localhost:8090/authenticate ...");
            try {
                URL url = new URL("http://localhost:8090/authenticate");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Build authentication payload body payload JSON string format map
                String jsonInput = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", userVal, pwdVal);

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int code = conn.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        code == 200 ? conn.getInputStream() : conn.getErrorStream(), StandardCharsets.UTF_8));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                in.close();

                if (code == 200) {
                    outputArea.setText(
                        "HTTP STATUS RESPONSE: " + code + " AUTHORIZED OK\n" +
                        "=======================================================================\n" +
                        "GENERATED JWT ENCODED COMPACT TOKEN STRING:\n\n" + sb.toString()
                    );
                } else {
                    outputArea.setText("HTTP STATUS RESPONSE: " + code + " UNAUTHORIZED\n" + sb.toString());
                }

            } catch (Exception ex) {
                outputArea.setText("Network Transmission Exception Handled:\n" + ex.getMessage());
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}