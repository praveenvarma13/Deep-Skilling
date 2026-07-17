package com.library1;

import com.library.service.BookService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.awt.*;

public class LibraryManagementApplication {
    public static void main(String[] args) {
        // Load context
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookService bookService = context.getBean("bookService", BookService.class);

        SwingUtilities.invokeLater(() -> createAndShowGUI(bookService, context));
    }

    private static void createAndShowGUI(BookService bookService, ClassPathXmlApplicationContext context) {
        // Dynamically pulling the frame title from Constructor Injection data!
        JFrame frame = new JFrame(bookService.getLibraryName() + " Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout(10, 10));

        // Header Label showing system capacity limits
        JLabel headerLabel = new JLabel("System Capacity: " + bookService.getMaxCapacity() + " books maximum.", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        frame.add(headerLabel, BorderLayout.NORTH);

        // Book List Display
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String book : bookService.getAvailableBooks()) {
            listModel.addElement(book);
        }
        JList<String> bookList = new JList<>(listModel);
        frame.add(new JScrollPane(bookList), BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        JTextField bookInputField = new JTextField();
        JButton addButton = new JButton("Add Book");

        inputPanel.add(new JLabel(" Enter Book Title: "), BorderLayout.WEST);
        inputPanel.add(bookInputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Event Handling
        addButton.addActionListener(e -> {
            String title = bookInputField.getText().trim();
            if (!title.isEmpty()) {
                bookService.registerNewBook(title);
                listModel.addElement(title);
                bookInputField.setText("");
            }
        });

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                context.close();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}