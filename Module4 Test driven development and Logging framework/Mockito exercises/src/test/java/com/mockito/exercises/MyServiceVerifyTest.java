package com.mockito.exercises;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
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

/**
 * Exercise 2: Verifying Interactions with an Interactive Dashboard.
 */
public class MyServiceVerifyTest implements ActionListener {

    private static JTextArea verificationConsole;
    private static JButton triggerVerifyButton;

    /**
     * 🤖 AUTOMATED JUNIT TEST
     */
    @Test
    public void testVerifyInteraction() {
        System.out.println("🧪 [JUnit 5 Automated] Running interaction verification test...");
        
        // 1. Create a mock object [cite: 27, 37]
        ExternalApi mockApi = mock(ExternalApi.class);
        
        // 2. Call the method via the service layer [cite: 28, 38, 39]
        MyService service = new MyService(mockApi);
        service.fetchData();
        
        // 3. Verify the interaction occurred [cite: 29, 41]
        verify(mockApi).getData();
        System.out.println("✔️ [JUnit 5 Automated] Interaction successfully verified!");
    }

    /**
     * 🎮 VISUAL SWING INTERFACE
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("🕵️ Mockito Lab Exercise 2: Interaction Auditor");
            frame.setSize(540, 360);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(6, 6, 6, 6);
            gbc.fill = GridBagConstraints.BOTH;

            JLabel titleLabel = new JLabel("Mockito Mock Behavior Verification Sandbox");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 13));
            titleLabel.setHorizontalAlignment(JLabel.CENTER);

            triggerVerifyButton = new JButton("Execute Service & Run verify()");
            
            verificationConsole = new JTextArea(8, 42);
            verificationConsole.setEditable(false);
            verificationConsole.setText("Auditor Status: Standby.\nClick the button to test interaction verification.");
            verificationConsole.setBorder(BorderFactory.createEtchedBorder());
            verificationConsole.setFont(new Font("Monospaced", Font.PLAIN, 12));

            triggerVerifyButton.addActionListener(new MyServiceVerifyTest());

            gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0; gbc.weighty = 0.0; panel.add(titleLabel, gbc);
            gbc.gridy = 1; panel.add(triggerVerifyButton, gbc);
            gbc.gridy = 2; gbc.weighty = 1.0; panel.add(verificationConsole, gbc);

            frame.add(panel);
            frame.setVisible(true);
        });
    }

    /**
     * ⚡ DYNAMIC INTERACTION AUDITOR HANDLER
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        verificationConsole.setText("");
        verificationConsole.setForeground(Color.BLACK);

        verificationConsole.append("1. [Initialization] Creating isolated Mockito mock instance...\n");
        ExternalApi interactiveMock = mock(ExternalApi.class); // [cite: 37]

        verificationConsole.append("2. [Execution] Injecting mock into service and invoking operation...\n");
        MyService serviceInstance = new MyService(interactiveMock); // [cite: 38]
        serviceInstance.fetchData(); // [cite: 39]

        verificationConsole.append("3. [Auditing] Running evaluation: verify(mockApi, times(1)).getData()\n\n");
        
        try {
            // Use Mockito to verify the interaction happened exactly once 
            verify(interactiveMock, times(1)).getData();

            verificationConsole.append("============= AUDITOR VERIFICATION =============");
            verificationConsole.append("\n Interaction Status: 100% VERIFIED\n");
            verificationConsole.append(" Method Target     : externalApi.getData()\n");
            verificationConsole.append(" Invocations Found : Exactly 1 time\n");
            verificationConsole.append("================================================\n\n");
            verificationConsole.append("✔️ SUCCESS: Mockito confirmed the service successfully collaborated with the API dependency!");
            verificationConsole.setForeground(new Color(0, 100, 0)); // Success green

        } catch (Exception ex) {
            verificationConsole.append("❌ AUDIT FAILURE: Expected interaction was missing or mismatched!\n" + ex.getMessage());
            verificationConsole.setForeground(Color.RED);
        }
    }
}