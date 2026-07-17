package com.mockito.exercises;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.Test;

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

/**
 * Exercise 3: Argument Matcher Verification Dashboard.
 */
public class MyServiceMatchTest implements ActionListener {

    private static JTextField argumentInputField;
    private static JTextArea matcherConsoleLog;
    private static JButton verifyArgumentButton;

    /**
     * 🤖 AUTOMATED JUNIT TEST
     */
    @Test
    public void testArgumentMatching() {
        System.out.println("🧪 [JUnit 5 Automated] Running argument matcher verification test...");
        
        // 1. Create a mock object
        ExternalApi mockApi = mock(ExternalApi.class);
        
        // Stub behavior using an argument matcher
        when(mockApi.formatData(anyString())).thenReturn("Processed Payload");
        
        // 2. Call the method with specific arguments
        MyService service = new MyService(mockApi);
        service.processQuery("SecretKey_123");
        
        // 3. Use argument matchers to verify the interaction
        verify(mockApi).formatData(eq("SecretKey_123"));
        System.out.println("✔️ [JUnit 5 Automated] Argument matcher criteria matched completely!");
    }

    /**
     * 🎮 VISUAL SWING INTERFACE
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("🎯 Mockito Lab Exercise 3: Argument Matcher Studio");
            frame.setSize(540, 360);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(6, 6, 6, 6);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel promptLabel = new JLabel("Type Test Argument String Payload:");
            argumentInputField = new JTextField("Mockito_Rules_2026", 20);

            verifyArgumentButton = new JButton("Execute processQuery() & Run verify()");
            
            matcherConsoleLog = new JTextArea(8, 42);
            matcherConsoleLog.setEditable(false);
            matcherConsoleLog.setText("Matcher Monitor: Standby.\nEnter arguments above and test verification constraints.");
            matcherConsoleLog.setBorder(BorderFactory.createEtchedBorder());
            matcherConsoleLog.setFont(new Font("Monospaced", Font.PLAIN, 12));

            verifyArgumentButton.addActionListener(new MyServiceMatchTest());

            gbc.gridx = 0; gbc.gridy = 0; panel.add(promptLabel, gbc);
            gbc.gridy = 1; panel.add(argumentInputField, gbc);
            gbc.gridy = 2; panel.add(verifyArgumentButton, gbc);
            gbc.gridy = 3; panel.add(matcherConsoleLog, gbc);

            frame.add(panel);
            frame.setVisible(true);
        });
    }

    /**
     * ⚡ DYNAMIC ARGUMENT MATCHING DISPATCHER
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        matcherConsoleLog.setText("");
        matcherConsoleLog.setForeground(Color.BLACK);
        
        String inputArgument = argumentInputField.getText().trim();

        if (inputArgument.isEmpty()) {
            matcherConsoleLog.setText("❌ Input Error: Argument payload text string cannot be blank.");
            matcherConsoleLog.setForeground(Color.RED);
            return;
        }

        matcherConsoleLog.append("1. [Setup] Initializing Mockito mock proxy configuration...\n");
        ExternalApi interactiveMock = mock(ExternalApi.class);

        matcherConsoleLog.append("2. [Stubbing] Registering dynamic matcher rule: anyString()\n");
        when(interactiveMock.formatData(anyString())).thenReturn("COMPLETED_SUCCESS");

        matcherConsoleLog.append("3. [Execution] Dispatched query: service.processQuery(\"" + inputArgument + "\")\n");
        MyService appService = new MyService(interactiveMock);
        appService.processQuery(inputArgument);

        matcherConsoleLog.append("4. [Audit Check] Testing: verify(mockApi).formatData(eq(\"" + inputArgument + "\"))\n\n");

        try {
            // Verify that the mock received the precise value entered by the user
            verify(interactiveMock).formatData(eq(inputArgument));

            matcherConsoleLog.append("============= ARGUMENT MATCH MATRIX =============");
            matcherConsoleLog.append("\n Matcher Engine State : 100% CAPTURED\n");
            matcherConsoleLog.append(" Captured Value       : \"" + inputArgument + "\"\n");
            matcherConsoleLog.append(" Argument Verification: Checked via eq()\n");
            matcherConsoleLog.append("=================================================\n\n");
            matcherConsoleLog.append("✔️ ARGUMENT MATCH SUCCESSFUL: Mockito verified the strict argument mapping loop.");
            matcherConsoleLog.setForeground(new Color(0, 100, 0));

        } catch (Exception ex) {
            matcherConsoleLog.append("❌ MATCH EXCEPTION: Argument parameter collection capture missed!\n" + ex.getMessage());
            matcherConsoleLog.setForeground(Color.RED);
        }
    }
}