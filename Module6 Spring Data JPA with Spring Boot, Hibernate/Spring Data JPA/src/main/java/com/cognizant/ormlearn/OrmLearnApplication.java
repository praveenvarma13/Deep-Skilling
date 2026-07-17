package com.cognizant.ormlearn;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.service.CountryService;
import com.cognizant.ormlearn.exception.CountryNotFoundException;

@SpringBootApplication
public class OrmLearnApplication {

    public static void main(String[] args) {
        // Start Spring Boot and disable Headless mode to support Swing GUI
        ConfigurableApplicationContext context = new SpringApplicationBuilder(OrmLearnApplication.class)
                .headless(false)
                .run(args);

        SwingUtilities.invokeLater(() -> {
			try {
				createAndShowGUI(context);
			} catch (CountryNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
    }

    private static void createAndShowGUI(ConfigurableApplicationContext context) throws CountryNotFoundException {
        CountryService countryService = context.getBean(CountryService.class);

        // --- GLOBAL STYLING PALETTE ---
        Color primaryColor = new Color(41, 128, 185);   // Modern Blue
        Color successColor = new Color(39, 174, 96);    // Emerald Green
        Color warningColor = new Color(230, 126, 34);   // Orange
        Color dangerColor = new Color(192, 41, 43);     // Crimson Red
        Color darkBg = new Color(52, 73, 94);           // Deep Slate Gray
        Color bgColor = new Color(245, 247, 250);        // Soft Light Gray
        
        Font titleFont = new Font("Segoe UI", Font.BOLD, 18);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 12);
        Font contentFont = new Font("Segoe UI", Font.PLAIN, 14);

        // --- MAIN FRAME SETUP ---
        JFrame frame = new JFrame("Cognizant JPA / Hibernate Unified Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // --- MAIN APPLICATION HEADER ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(primaryColor);
        headerPanel.setBorder(new EmptyBorder(12, 10, 12, 10));
        JLabel titleLabel = new JLabel("Enterprise Country Relational Database Management Hub");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(titleFont);
        headerPanel.add(titleLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        // --- TABBED CONTAINER ---
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(labelFont);

        // ==========================================
        // TAB 1: FULL CRUD MANAGEMENT CONSOLE
        // ==========================================
        JPanel crudTab = new JPanel(new BorderLayout(15, 15));
        crudTab.setBackground(bgColor);

        // Left form inside CRUD tab
        JPanel formPanel = new JPanel(new GridLayout(6, 1, 5, 12));
        formPanel.setBackground(bgColor);
        formPanel.setBorder(new EmptyBorder(15, 20, 15, 10));
        formPanel.setPreferredSize(new Dimension(320, 400));

        JLabel codeLabel = new JLabel("Country Code (2 Letters max):");
        codeLabel.setFont(labelFont);
        JTextField codeField = new JTextField();
        codeField.setFont(contentFont);

        JLabel nameLabel = new JLabel("Country Name:");
        nameLabel.setFont(labelFont);
        JTextField nameField = new JTextField();
        nameField.setFont(contentFont);

        formPanel.add(codeLabel);
        formPanel.add(codeField);
        formPanel.add(nameLabel);
        formPanel.add(nameField);

        // CRUD Action buttons panel grid
        JPanel buttonGrid = new JPanel(new GridLayout(2, 2, 8, 8));
        buttonGrid.setBackground(bgColor);

        JButton btnFind = new JButton("🔍 Find Code");
        JButton btnAdd = new JButton("➕ Add New");
        JButton btnUpdate = new JButton("✏️ Update Name");
        JButton btnDelete = new JButton("❌ Delete Code");

        JButton[] crudButtons = {btnFind, btnAdd, btnUpdate, btnDelete};
        for (JButton btn : crudButtons) {
            btn.setFont(labelFont);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
        }
        btnFind.setBackground(darkBg);
        btnAdd.setBackground(successColor);
        btnUpdate.setBackground(warningColor);
        btnDelete.setBackground(dangerColor);

        buttonGrid.add(btnFind);
        buttonGrid.add(btnAdd);
        buttonGrid.add(btnUpdate);
        buttonGrid.add(btnDelete);
        formPanel.add(buttonGrid);
        crudTab.add(formPanel, BorderLayout.WEST);

        // Right List inside CRUD Tab
        DefaultListModel<String> crudListModel = new DefaultListModel<>();
        JList<String> crudList = new JList<>(crudListModel);
        crudList.setFont(contentFont);
        crudList.setFixedCellHeight(30);

        JScrollPane crudScroll = new JScrollPane(crudList);
        crudScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225), 1), "Live Database Contents"));
        
        JPanel crudListWrapper = new JPanel(new BorderLayout());
        crudListWrapper.setBackground(bgColor);
        crudListWrapper.setBorder(new EmptyBorder(15, 10, 20, 20));
        crudListWrapper.add(crudScroll, BorderLayout.CENTER);
        crudTab.add(crudListWrapper, BorderLayout.CENTER);

        // Helper refresher for CRUD tab view list
        Runnable refreshCrudList = () -> {
            crudListModel.clear();
            try {
                List<Country> list = countryService.getAllCountries();
                for (Country c : list) {
                    crudListModel.addElement("  🏳️   [" + c.getCode() + "]    -    " + c.getName());
                }
            } catch (Exception ex) {
                crudListModel.addElement("MySQL Load Error: " + ex.getMessage());
            }
        };

        // ==========================================
        // TAB 2: ADVANCED QUERY METHODS EXPLORER
        // ==========================================
        JPanel queryTab = new JPanel(new BorderLayout(15, 15));
        queryTab.setBackground(bgColor);

        // Search panel inside Query Tab
        JPanel searchBarPanel = new JPanel(new BorderLayout(10, 10));
        searchBarPanel.setBackground(bgColor);
        searchBarPanel.setBorder(new EmptyBorder(15, 20, 0, 20));
        
        JLabel searchLabel = new JLabel("Live Type Search Box (Matches characters anywhere in name): ");
        searchLabel.setFont(labelFont);
        searchLabel.setForeground(darkBg);
        
        JTextField searchField = new JTextField();
        searchField.setFont(contentFont);
        
        searchBarPanel.add(searchLabel, BorderLayout.NORTH);
        searchBarPanel.add(searchField, BorderLayout.CENTER);

        // Query Results List view
        DefaultListModel<String> queryListModel = new DefaultListModel<>();
        JList<String> queryResultsList = new JList<>(queryListModel);
        queryResultsList.setFont(new Font("Consolas", Font.PLAIN, 13));
        queryResultsList.setSelectionBackground(primaryColor);
        queryResultsList.setSelectionForeground(Color.WHITE);
        queryResultsList.setFixedCellHeight(26);

        JScrollPane queryScroll = new JScrollPane(queryResultsList);
        queryScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225), 1), "Query Engine Log View"));

        JPanel queryCenterPanel = new JPanel(new BorderLayout(0, 15));
        queryCenterPanel.setBackground(bgColor);
        queryCenterPanel.add(searchBarPanel, BorderLayout.NORTH);
        queryCenterPanel.add(queryScroll, BorderLayout.CENTER);
        queryCenterPanel.setBorder(new EmptyBorder(0, 0, 0, 20));
        queryTab.add(queryCenterPanel, BorderLayout.CENTER);

        // Bottom A-Z panel inside Query Tab
        JPanel alphabetPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 4));
        alphabetPanel.setBackground(bgColor);
        
        JPanel bottomContainer = new JPanel(new BorderLayout());
        bottomContainer.setBackground(bgColor);
        bottomContainer.setBorder(new EmptyBorder(0, 20, 15, 20));
        
        JLabel alphabetHint = new JLabel("Filter Globally By Starting Alphabet Index:", JLabel.CENTER);
        alphabetHint.setFont(labelFont);
        alphabetHint.setForeground(darkBg);
        alphabetHint.setBorder(new EmptyBorder(0, 0, 8, 0));
        
        bottomContainer.add(alphabetHint, BorderLayout.NORTH);
        bottomContainer.add(alphabetPanel, BorderLayout.CENTER);
        queryTab.add(bottomContainer, BorderLayout.SOUTH);

        // Helper update worker logic for Text Search
        Runnable updateQueryTabList = () -> {
            queryListModel.clear();
            String queryText = searchField.getText().trim();
            try {
                List<Country> list = queryText.isEmpty() ? 
                        countryService.getAllCountries() : countryService.searchCountriesByNamePart(queryText);
                for (Country c : list) {
                    queryListModel.addElement(String.format(" 🏳️  [%s]   %s", c.getCode(), c.getName()));
                }
            } catch (Exception ex) {
                queryListModel.addElement("Error: " + ex.getMessage());
            }
        };

        // --- WIRE ALL COMPONENT EVENT LISTENERS ---

        // CRUD: Find
        btnFind.addActionListener(e -> {
            String target = codeField.getText().trim().toUpperCase();
            Country found = countryService.findCountryByCode(target);
			nameField.setText(found.getName());
        });

        // CRUD: Add
        btnAdd.addActionListener(e -> {
            try {
                Country c = new Country();
                c.setCode(codeField.getText().trim().toUpperCase());
                c.setName(nameField.getText().trim());
                countryService.addCountry(c);
                refreshCrudList.run();
                codeField.setText(""); nameField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Constraint Violation: " + ex.getMessage());
            }
        });

        // CRUD: Update
        btnUpdate.addActionListener(e -> {
            try {
                countryService.updateCountry(codeField.getText().trim().toUpperCase(), nameField.getText().trim());
                refreshCrudList.run();
                codeField.setText(""); nameField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        });

        // CRUD: Delete
        btnDelete.addActionListener(e -> {
            try {
                countryService.deleteCountry(codeField.getText().trim().toUpperCase());
                refreshCrudList.run();
                codeField.setText(""); nameField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        });

        // Query: Live document typing updates
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateQueryTabList.run(); }
            public void removeUpdate(DocumentEvent e) { updateQueryTabList.run(); }
            public void changedUpdate(DocumentEvent e) { updateQueryTabList.run(); }
        });

        // Query: Dynamically populating buttons A to Z
        for (char alpha = 'A'; alpha <= 'Z'; alpha++) {
            String letter = String.valueOf(alpha);
            JButton alphaBtn = new JButton(letter);
            alphaBtn.setFont(new Font("Segoe UI", Font.BOLD, 11));
            alphaBtn.setBackground(darkBg);
            alphaBtn.setForeground(Color.WHITE);
            alphaBtn.setMargin(new Insets(2, 6, 2, 6));
            alphaBtn.setFocusPainted(false);

            alphaBtn.addActionListener(e -> {
                searchField.setText("");
                queryListModel.clear();
                try {
                    List<Country> list = countryService.findCountriesStartingWith(letter);
                    for (Country c : list) {
                        queryListModel.addElement(String.format(" 📍  [%s]   %s", c.getCode(), c.getName()));
                    }
                    if(list.isEmpty()) queryListModel.addElement("No country names start with '" + letter + "'");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            });
            alphabetPanel.add(alphaBtn);
        }

        // Add both dashboards to the Tab framework
        tabbedPane.addTab("Country CRUD Console", crudTab);
        tabbedPane.addTab("Spring Data JPA Query Methods", queryTab);
        frame.add(tabbedPane, BorderLayout.CENTER);

        // Run baseline lists sync and launch window
        refreshCrudList.run();
        updateQueryTabList.run();
        frame.setVisible(true);
    }
}