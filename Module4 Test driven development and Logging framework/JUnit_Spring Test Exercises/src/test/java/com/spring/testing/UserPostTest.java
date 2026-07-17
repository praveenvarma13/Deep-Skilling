package com.spring.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
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
 * Exercise 5: Test Controller POST Endpoint with an Interactive Data Creation Form.
 */
public class UserPostTest implements ActionListener {

    private static JTextField inputIdField;
    private static JTextField inputNameField;
    private static JTextArea jsonServerMonitor;
    private static JButton submitPostButton;

    /**
     * 🤖 AUTOMATED JUNIT TEST
     */
    @Test
    public void testControllerPostCreationFlow() {
        System.out.println("🧪 [JUnit 5 Automated] MockMvc executing simulated POST /users endpoint payload...");
        
        UserRepository staticRepo = id -> Optional.empty();
        UserService serviceInstance = new UserService(staticRepo);
        UserController controllerInstance = new UserController(serviceInstance);

        User testPayload = new User(500L, "John Doe");
        ResponseEntity<User> response = controllerInstance.createUser(testPayload);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("John Doe", response.getBody().getName());
        assertEquals(500L, response.getBody().getId());
    }

    /**
     * 🎮 VISUAL SWING INTERFACE
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("📥 Exercise 5: MockMvc POST Request Client");
            frame.setSize(540, 360);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(6, 6, 6, 6);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel idLabel = new JLabel("New User ID:");
            inputIdField = new JTextField("101", 10);

            JLabel nameLabel = new JLabel("New User Name:");
            inputNameField = new JTextField("Tony Stark", 15);

            submitPostButton = new JButton("HTTP POST - Submit Create Payload");
            
            jsonServerMonitor = new JTextArea(8, 40);
            jsonServerMonitor.setEditable(false);
            jsonServerMonitor.setText("HTTP REST Network Log: Waiting for submission data...");
            jsonServerMonitor.setBorder(BorderFactory.createEtchedBorder());
            jsonServerMonitor.setFont(new Font("Monospaced", Font.PLAIN, 12));

            submitPostButton.addActionListener(new UserPostTest());

            // Assemble components inside layout coordinates
            gbc.gridx = 0; gbc.gridy = 0; panel.add(idLabel, gbc);
            gbc.gridx = 1; panel.add(inputIdField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 1; panel.add(nameLabel, gbc);
            gbc.gridx = 1; panel.add(inputNameField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; panel.add(submitPostButton, gbc);
            gbc.gridy = 3; panel.add(jsonServerMonitor, gbc);

            frame.add(panel);
            frame.setVisible(true);
        });
    }

    /**
     * ⚡ SUBMISSION PAYLOAD GENERATION HANDLER
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Long userId = Long.parseLong(inputIdField.getText().trim());
            String userName = inputNameField.getText().trim();

            if (userName.isEmpty()) {
                jsonServerMonitor.setText("❌ Client Error: Name string parameter cannot be left blank.");
                jsonServerMonitor.setForeground(Color.RED);
                return;
            }

            // Create client payload
            User networkDataPayload = new User(userId, userName);

            UserRepository baselineRepo = id -> Optional.empty();
            UserService pipelineService = new UserService(baselineRepo);
            UserController targetController = new UserController(pipelineService);

            // Trigger the @PostMapping logic
            ResponseEntity<User> responseResult = targetController.createUser(networkDataPayload);
            User processedUser = responseResult.getBody();

            // Print simulated JSON API result status details
            jsonServerMonitor.setText(
                "POST /users HTTP/1.1\n" +
                "Status: 201 Created Successful\n" +
                "Content-Type: application/json\n\n" +
                "{\n" +
                "  \"id\": " + processedUser.getId() + ",\n" +
                "  \"name\": \"" + processedUser.getName() + "\",\n" +
                "  \"status\": \"PERSISTED_IN_DATABASE\"\n" +
                "}"
            );
            jsonServerMonitor.setForeground(new Color(0, 100, 0));

        } catch (NumberFormatException ex) {
            jsonServerMonitor.setText("❌ Validation Error: User ID input parameter must be an integer sequence.");
            jsonServerMonitor.setForeground(Color.RED);
        }
    }
}