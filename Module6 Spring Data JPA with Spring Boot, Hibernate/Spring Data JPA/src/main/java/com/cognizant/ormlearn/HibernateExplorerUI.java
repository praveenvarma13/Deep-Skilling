package com.cognizant.ormlearn;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class HibernateExplorerUI extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Color primaryColor = new Color(44, 62, 80);    // Charcoal Dark Slate Blue
    private final Color accentColor = new Color(230, 126, 34);   // Hibernate Orange accent
    private final Color textBgColor = new Color(245, 246, 248);   // Soft Light Gray
    
    private JTextArea explanationArea;
    private JLabel componentTitle;
    private Map<String, String> topicData;

    public HibernateExplorerUI() {
        super("Hands-on 2: Hibernate XML Configuration & Core Objects");
        initializeData();
        setupLayout();
    }

    private void initializeData() {
        topicData = new HashMap<>();
        
        // --- Populating Data from Hands-on 2 Specifications ---
        topicData.put("XML Mapping", 
            "How Object-Relational Mapping is configured via XML:\n\n" +
            "• Class-to-Table Mapping: Done using the <class name=\"...\" table=\"...\"> tag.\n" +
            "• Primary Key Mapping: Defined via the <id name=\"...\" column=\"...\"> tag with an optional generator.\n" +
            "• Property Mapping: Uses <property name=\"...\" column=\"...\"> to link Java fields to database table columns.");
        
        topicData.put("SessionFactory", 
            "• Definition: A thread-safe, immutable cache of compiled mappings for a single database.\n" +
            "• Role: It is heavyweight because it reads configuration definitions (<mapping resource=\"...\" />) and instantiates connection pools.\n" +
            "• Lifecycle: Created once during application startup from the Configuration object.");
            
        topicData.put("Session", 
            "• Definition: A single-threaded, short-lived object representing a conversation between the application and the database.\n" +
            "• Role: Wraps a JDBC connection. Acts as a First-Level Cache for persistence operations.\n" +
            "• Methods: Opened via factory.openSession() and must be closed after operations complete.");
            
        topicData.put("Transaction", 
            "• Definition: A single-threaded, short-lived object used by the application to specify atomic units of work.\n" +
            "• Methods:\n" +
            "  - beginTransaction(): Starts a database transaction wrapper.\n" +
            "  - commit(): Flushes session data states and writes permanently to MySQL.\n" +
            "  - rollback(): Discards changes if a HibernateException occurs.");

        topicData.put("DML Actions", 
            "Core CRUD Operations on the Session API Object:\n\n" +
            "• session.save(entity): Persists transient objects into rows (Generates INSERT SQL).\n" +
            "• session.get(Class, id): Retrieves row into a persistence state by primary key (Generates SELECT SQL).\n" +
            "• session.createQuery().list(): Executes HQL (Hibernate Query Language) independent queries across tables.\n" +
            "• session.delete(entity): Removes records from active tables (Generates DELETE SQL).");
    }

    private void setupLayout() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // 1. HEADER PANEL
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Hibernate Framework Framework Core Objects & XML Specs");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Hands-on 2: Interactive Concept Walkthrough Dashboard");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(200, 214, 229));
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        // 2. WEST NAV PANEL (Sidebar Navigation with customized styled buttons)
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(52, 73, 94));
        sidebarPanel.setBorder(new EmptyBorder(20, 10, 20, 10));

        String[] buttons = {"XML Mapping", "SessionFactory", "Session", "Transaction", "DML Actions"};
        for (String label : buttons) {
            JButton btn = createStyledButton(label);
            sidebarPanel.add(btn);
            sidebarPanel.add(Box.createVerticalStrut(10)); // Spacer spacing
            
            btn.addActionListener(e -> displayTopic(label));
        }
        add(sidebarPanel, BorderLayout.WEST);

        // 3. CENTER CONTENT DISPLAY PANEL
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);

        componentTitle = new JLabel("Select a Core Hibernate Object to Inspect");
        componentTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        componentTitle.setForeground(primaryColor);
        contentPanel.add(componentTitle, BorderLayout.NORTH);

        explanationArea = new JTextArea();
        explanationArea.setEditable(false);
        explanationArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        explanationArea.setBackground(textBgColor);
        explanationArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 230), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        explanationArea.setLineWrap(true);
        explanationArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(explanationArea);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        // Initialize with default topic view
        displayTopic("XML Mapping");
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(160, 40));
        button.setPreferredSize(new Dimension(160, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(primaryColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(accentColor, 1));
        return button;
    }

    private void displayTopic(String topicKey) {
        componentTitle.setText(topicKey + " Specification Analysis");
        explanationArea.setText(topicData.get(topicKey));
    }
}