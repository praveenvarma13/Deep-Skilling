package com.cognizant.ormlearn;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.model.Skill;
import com.cognizant.ormlearn.service.EmployeeService;

@SpringBootApplication
public class OrmLearnApplication {

    public static void main(String[] args) {
        // Run Spring Boot disabling Headless environment rules for Swing windows compatibility
        ConfigurableApplicationContext context = new SpringApplicationBuilder(OrmLearnApplication.class)
                .headless(false)
                .run(args);

        // Render graphical interface
        SwingUtilities.invokeLater(() -> createAndShowAdvancedGUI(context));
    }

    private static void createAndShowAdvancedGUI(ConfigurableApplicationContext context) {
        EmployeeService employeeService = context.getBean(EmployeeService.class);

        // UI Flat Theme Colors
        Color headerBg = new Color(52, 73, 94);       // Dark Graphite Gray
        Color buttonBlue = new Color(41, 128, 185);   // Pure HQL Blue
        Color buttonPurple = new Color(142, 68, 173); // Aggregate Purple
        Color buttonOrange = new Color(211, 84, 0);   // Native Query Orange
        Color lightGray = new Color(245, 247, 250);   // Canvas Screen Base Background

        Font subFont = new Font("Segoe UI", Font.BOLD, 12);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 14);

        JFrame frame = new JFrame("Advanced Hands-on: Hibernate Query Language (HQL) & Native Matrix");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(950, 560);
        frame.setLayout(new BorderLayout(15, 15));
        frame.getContentPane().setBackground(lightGray);

        // 1. Header Frame Status Bar
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(headerBg);
        headerPanel.setBorder(new EmptyBorder(12, 10, 12, 10));
        JLabel titleLabel = new JLabel("Enterprise JPA Query Performance Studio (HQL, Joins, Aggregates & Native)");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        // 2. Query Dashboard Toolbar Left Panel
        JPanel actionPanel = new JPanel(new GridLayout(8, 1, 5, 12));
        actionPanel.setBackground(lightGray);
        actionPanel.setBorder(new EmptyBorder(20, 20, 20, 10));
        actionPanel.setPreferredSize(new Dimension(340, 400));

        JButton btnPermanentHql = new JButton("🚀 Execute Optimized HQL Fetch Join");
        btnPermanentHql.setBackground(buttonBlue);
        btnPermanentHql.setForeground(Color.WHITE);
        btnPermanentHql.setFont(subFont);

        JLabel lblDeptId = new JLabel("Target Department ID for Avg Salary:");
        lblDeptId.setFont(subFont);
        JTextField txtDeptId = new JTextField("2"); // Defaulting to Engineering
        txtDeptId.setFont(textFont);

        JButton btnAvgSalary = new JButton("📊 Compute Department Avg Salary");
        btnAvgSalary.setBackground(buttonPurple);
        btnAvgSalary.setForeground(Color.WHITE);
        btnAvgSalary.setFont(subFont);

        JButton btnNativeSql = new JButton("💾 Bypass Framework via Native Query");
        btnNativeSql.setBackground(buttonOrange);
        btnNativeSql.setForeground(Color.WHITE);
        btnNativeSql.setFont(subFont);

        actionPanel.add(btnPermanentHql);
        actionPanel.add(new JSeparator());
        actionPanel.add(lblDeptId);
        actionPanel.add(txtDeptId);
        actionPanel.add(btnAvgSalary);
        actionPanel.add(new JSeparator());
        actionPanel.add(btnNativeSql);
        frame.add(actionPanel, BorderLayout.WEST);

        // 3. Logger Stream Output Center View Panel
        JTextArea queryConsole = new JTextArea();
        queryConsole.setEditable(false);
        queryConsole.setFont(new Font("Consolas", Font.PLAIN, 13));
        queryConsole.setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(queryConsole);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 205, 215), 1), 
                "Live Hibernate Compilation Trace Logs"
        ));

        JPanel textWrapper = new JPanel(new BorderLayout());
        textWrapper.setBackground(lightGray);
        textWrapper.setBorder(new EmptyBorder(15, 10, 20, 20));
        textWrapper.add(scrollPane, BorderLayout.CENTER);
        frame.add(textWrapper, BorderLayout.CENTER);

        // --- BUTTON EVENT RECEIPT WIRES ---

        // Action 1: HQL Join Fetch Path execution
        btnPermanentHql.addActionListener(e -> {
            queryConsole.setText("");
            try {
                @SuppressWarnings("unchecked")
				List<Employee> permanentEmployees = (List<Employee>) employeeService.getAllPermanentEmployees();
                StringBuilder sb = new StringBuilder();
                sb.append("================= HQL FETCH JOIN EXECUTED SUCCESSFULY =================\n");
                sb.append("Query: SELECT e FROM Employee e LEFT JOIN FETCH e.department d LEFT JOIN FETCH e.skillList...\n");
                sb.append("Benefit: Single query generated. Extinguishes classic Hibernate N+1 select loop performance traps.\n\n");
                
                if (permanentEmployees.isEmpty()) {
                    sb.append("No active employees marked as permanent (permanent = true) exist in database tables.");
                } else {
                    for (Employee emp : permanentEmployees) {
                        sb.append(String.format("• [Permanent Employee] ID: %d | Name: %s | Salary: $%.2f\n", 
                                emp.getId(), emp.getName(), emp.getSalary()));
                        sb.append(String.format("  ├── Department Object -> %s\n", 
                                (emp.getDepartment() != null) ? emp.getDepartment().getName() : "None"));
                        
                        sb.append("  └── Skills Sub-graph Graph: ");
                        if (emp.getSkillList() != null && !emp.getSkillList().isEmpty()) {
                            for (Skill sk : emp.getSkillList()) {
                                sb.append("[").append(sk.getName()).append("] ");
                            }
                        } else {
                            sb.append("[No Skill Associations assigned]");
                        }
                        sb.append("\n\n");
                    }
                }
                queryConsole.setText(sb.toString());
            } catch (Exception ex) {
                queryConsole.setText("HQL Compilation Failure:\n" + ex.getMessage());
            }
        });

        // Action 2: HQL Aggregate Function
        btnAvgSalary.addActionListener(e -> {
            queryConsole.setText("");
            try {
                int deptId = Integer.parseInt(txtDeptId.getText().trim());
                Double avgSalary = employeeService.getAverageSalary(deptId);
                
                StringBuilder sb = new StringBuilder();
                sb.append("================= HQL AGGREGATE FUNCTION COMPILED =================").append("\n");
                sb.append("Query: SELECT AVG(e.salary) FROM Employee e WHERE e.department.id = :id").append("\n\n");
                sb.append(String.format("Calculated Metric Results:\n -> Average Salary for Department ID [%d] = $%.2f\n", 
                        deptId, avgSalary));
                queryConsole.setText(sb.toString());
            } catch (Exception ex) {
                queryConsole.setText("Aggregate Computation Error: " + ex.getMessage());
            }
        });

        // Action 3: Native Query Extraction
        btnNativeSql.addActionListener(e -> {
            queryConsole.setText("");
            try {
                @SuppressWarnings("unchecked")
				List<Employee> allEmployees = (List<Employee>) employeeService.getAllEmployeesNative();
                StringBuilder sb = new StringBuilder();
                sb.append("================= DIRECT SQL NATIVE QUERY BYPASS =================").append("\n");
                sb.append("Query: SELECT * FROM employee (Raw Relational Database Mode)").append("\n");
                sb.append("Warning Note: Native operations limit your database portabilities across dialects. Use carefully!\n\n");
                
                for (Employee emp : allEmployees) {
                    sb.append(String.format("-> Row Data Extract => ID: %d | Base Entity Name Data: %s\n", 
                            emp.getId(), emp.getName()));
                }
                queryConsole.setText(sb.toString());
            } catch (Exception ex) {
                queryConsole.setText("Native SQL execution runtime breakdown:\n" + ex.getMessage());
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        btnPermanentHql.doClick(); // Run a default optimized HQL trace on startup
    }
}