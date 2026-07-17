package com.library;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class LibraryBootApplication {

    public static void main(String[] args) {
        // Bootstrap Spring Boot and disable headless mode to support Swing GUI components
        ConfigurableApplicationContext context = new SpringApplicationBuilder(LibraryBootApplication.class)
                .headless(false)
                .run(args);

        // Run UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> createAndShowGUI(context));
    }

    private static void createAndShowGUI(ConfigurableApplicationContext context) {
        final BookRepository repository = context.getBean(BookRepository.class);

        // Pre-seed some default rows in the database
        repository.save(new Book("Spring Boot in Action"));
        repository.save(new Book("Clean Code"));

        JFrame frame = new JFrame("Spring Boot + Swing Database App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout(10, 10));

        final DefaultListModel<String> listModel = new DefaultListModel<>();
        repository.findAll().forEach(book -> listModel.addElement(book.getTitle()));
        JList<String> bookList = new JList<>(listModel);
        frame.add(new JScrollPane(bookList), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        final JTextField bookInputField = new JTextField();
        JButton addButton = new JButton("Save to Database");

        inputPanel.add(new JLabel(" New Book Title: "), BorderLayout.WEST);
        inputPanel.add(bookInputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Save entry dynamically to H2 Database and refresh UI window layout view
        addButton.addActionListener(e -> {
            String title = bookInputField.getText().trim();
            if (!title.isEmpty()) {
                repository.save(new Book(title)); 
                listModel.addElement(title);     
                bookInputField.setText("");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}