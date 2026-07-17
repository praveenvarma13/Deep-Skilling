package com.spring.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

/**
 * Exercise 2: Mocking Repository Layer with an Embedded Swing Control Panel.
 */
public class UserServiceTest implements ActionListener {

    // Define GUI layout fields safely at class level to avoid any scope errors
    private static JTextField idInputField;
    private static JLabel dataOutputLabel;
    private static JButton fetchButton;

    /**
     * 🤖 AUTOMATED JUNIT TEST
     */
    @Test
    public void testMockedUserRetrieval() {
        System.out.println("🧪 [JUnit 5 Automated] Verifying UserService layer with a Mock Repository...");
        
        // Manual Mock Injection Strategy
        UserRepository mockRepo = id -> {
            if (id.equals(1L)) {
                return Optional.of(new User(1L, "Alice"));
            }
            return Optional.empty();
        };

        UserService service = new UserService(mockRepo);
        User result = service.getUserById(1L);

        assertNotNull(result);
        assertEquals("Alice", result.getName());
    }

    /**
     * 🎮 VISUAL SWING INTERFACE
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("👤 Exercise 2: Mock Repository Panel");
            frame.setSize(480, 260);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel inputPrompt = new JLabel("Enter User ID (Try '1' or '2'):");
            idInputField = new JTextField("1", 8);

            fetchButton = new JButton("Fetch via Mocked Repository");
            dataOutputLabel = new JLabel("Output Status: Idle");
            dataOutputLabel.setFont(new Font("Arial", Font.BOLD, 13));

            // Link the action button cleanly to the handler method below
            fetchButton.addActionListener(new UserServiceTest());

            // Assign layout coordinates
            gbc.gridx = 0; gbc.gridy = 0; panel.add(inputPrompt, gbc);
            gbc.gridx = 1; panel.add(idInputField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; panel.add(fetchButton, gbc);
            gbc.gridy = 2; panel.add(dataOutputLabel, gbc);

            frame.add(panel);
            frame.setVisible(true);
        });
    }

    /**
     * ⚡ ERROR-FREE INTERACTION HANDLER
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Long parsedId = Long.parseLong(idInputField.getText().trim());

            // Mock Data Definition Rules
            UserRepository runtimeMockRepo = id -> {
                if (id.equals(1L)) {
                    return Optional.of(new User(1L, "Alice Smith"));
                } else if (id.equals(2L)) {
                    return Optional.of(new User(2L, "Bob Jones"));
                }
                return Optional.empty();
            };

            // Inject the mock repository setup directly into the service block
            UserService service = new UserService(runtimeMockRepo);
            User mockUserResult = service.getUserById(parsedId);

            if (mockUserResult != null) {
                dataOutputLabel.setText("✔️ User Found: " + mockUserResult.getName() + " (ID: " + mockUserResult.getId() + ")");
                dataOutputLabel.setForeground(new Color(0, 128, 0)); // Passing Green
            } else {
                dataOutputLabel.setText("❌ Repository Return: User not found in database.");
                dataOutputLabel.setForeground(Color.BLUE);
            }

        } catch (NumberFormatException ex) {
            dataOutputLabel.setText("❌ Input Error: Please enter a valid numerical ID.");
            dataOutputLabel.setForeground(Color.RED);
        }
    }
}