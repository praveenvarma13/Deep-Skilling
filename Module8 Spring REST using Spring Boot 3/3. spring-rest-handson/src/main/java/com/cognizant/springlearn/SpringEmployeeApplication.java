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

@SuppressWarnings("unused")
@SpringBootApplication
public class SpringEmployeeApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringEmployeeApplication.class)
                .headless(false)
                .run(args);

        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        Color primaryNavy = new Color(44, 62, 80);
        Color lightBg = new Color(245, 247, 250);

        JFrame frame = new JFrame("Hands-on Master Studio: Employees & Departments Enterprise Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 560);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(lightBg);

        // Header Panel Banner
        JPanel banner = new JPanel();
        banner.setBackground(primaryNavy);
        banner.setBorder(new EmptyBorder(12, 10, 12, 10));
        JLabel title = new JLabel("Consolidated Management Center - Plain Text Normal Form Stream Viewer");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));
        banner.add(title);
        frame.add(banner, BorderLayout.NORTH);

        // Form Control Input Box (Left Panel Stacked)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(lightBg);
        leftPanel.setBorder(new EmptyBorder(15, 20, 15, 10));
        leftPanel.setPreferredSize(new Dimension(380, 500));

        // Global Fetch Buttons
        JButton btnFetchEmployees = new JButton("📋 View Employees (Normal Plain Text)");
        btnFetchEmployees.setBackground(new Color(41, 128, 185));
        btnFetchEmployees.setForeground(Color.WHITE);
        btnFetchEmployees.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnFetchEmployees.setMaximumSize(new Dimension(350, 35));

        JButton btnFetchDepartments = new JButton("🏢 View Departments (Normal Plain Text)");
        btnFetchDepartments.setBackground(new Color(155, 89, 182));
        btnFetchDepartments.setForeground(Color.WHITE);
        btnFetchDepartments.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnFetchDepartments.setMaximumSize(new Dimension(350, 35));

        // Separator Form Label
        JLabel lblFormTitle = new JLabel("--- Add New Employee Details ---");
        lblFormTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblFormTitle.setBorder(new EmptyBorder(25, 0, 10, 0));

        JLabel lblName = new JLabel("Employee Name:");
        JTextField txtName = new JTextField();
        txtName.setMaximumSize(new Dimension(350, 28));

        JLabel lblSalary = new JLabel("Salary Amount:");
        JTextField txtSalary = new JTextField();
        txtSalary.setMaximumSize(new Dimension(350, 28));

        JCheckBox chkPermanent = new JCheckBox("Is Permanent Status Employee?");
        chkPermanent.setBackground(lightBg);

        JLabel lblDept = new JLabel("Department ID (1 for IT, 2 for HR):");
        JTextField txtDept = new JTextField("1");
        txtDept.setMaximumSize(new Dimension(350, 28));

        JButton btnAddEmployee = new JButton("💾 Save Employee to Database");
        btnAddEmployee.setBackground(new Color(46, 204, 113));
        btnAddEmployee.setForeground(Color.WHITE);
        btnAddEmployee.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAddEmployee.setMaximumSize(new Dimension(350, 35));

        // Assemble Left Elements Panel
        leftPanel.add(btnFetchEmployees);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(btnFetchDepartments);
        leftPanel.add(lblFormTitle);
        leftPanel.add(lblName);
        leftPanel.add(txtName);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        leftPanel.add(lblSalary);
        leftPanel.add(txtSalary);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        leftPanel.add(chkPermanent);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        leftPanel.add(lblDept);
        leftPanel.add(txtDept);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        leftPanel.add(btnAddEmployee);
        frame.add(leftPanel, BorderLayout.WEST);

        // Plain Text Display Output Console Area (Right Side)
        JTextArea outputArea = new JTextArea("System Ready. Request data streams or insert records to begin...");
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        outputArea.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Plain Normal Form Console Log Output Stream"));
        
        JPanel textWrapper = new JPanel(new BorderLayout());
        textWrapper.setBackground(lightBg);
        textWrapper.setBorder(new EmptyBorder(10, 10, 20, 20));
        textWrapper.add(scrollPane, BorderLayout.CENTER);
        frame.add(textWrapper, BorderLayout.CENTER);

        // --- INTERACTIVE ACTION LISTENERS ---

        // Action 1: Fetch and display Employee list in Normal Form
        btnFetchEmployees.addActionListener(e -> {
            fetchDataFromServer("http://localhost:8086/employees", outputArea);
        });

        // Action 2: Fetch and display Department list in Normal Form (Final Hands-on)
        btnFetchDepartments.addActionListener(e -> {
            fetchDataFromServer("http://localhost:8086/departments", outputArea);
        });

        // Action 3: Add dynamic employee details row records
        btnAddEmployee.addActionListener(e -> {
            String name = txtName.getText().trim();
            String salaryStr = txtSalary.getText().trim();
            boolean isPerm = chkPermanent.isSelected();
            String deptIdStr = txtDept.getText().trim();

            if (name.isEmpty() || salaryStr.isEmpty() || deptIdStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please complete all fields!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                URL url = new URL("http://localhost:8086/employees");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                String payload = String.format(
                    "{\"name\":\"%s\",\"salary\":%s,\"permanent\":%b,\"department\":{\"id\":%s}}",
                    name, salaryStr, isPerm, deptIdStr
                );

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = payload.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int code = conn.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        code == 200 ? conn.getInputStream() : conn.getErrorStream(), StandardCharsets.UTF_8));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                in.close();

                outputArea.setText("Server Status Response: " + code + "\n" + sb.toString());
                
                if (code == 200) {
                    txtName.setText("");
                    txtSalary.setText("");
                    chkPermanent.setSelected(false);
                }

            } catch (Exception ex) {
                outputArea.setText("Execution Exception Handled: " + ex.getMessage());
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void fetchDataFromServer(String targetUrl, JTextArea outputArea) {
        try {
            URL url = new URL(targetUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\n");
            }
            in.close();

            outputArea.setText(sb.toString());
        } catch (Exception ex) {
            outputArea.setText("Transmission failure connecting to " + targetUrl + "\nDetails: " + ex.getMessage());
        }
    }
}