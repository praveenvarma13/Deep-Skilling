package com.logging.exercises;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

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
 * Exercise 1: Interactive SLF4J Logging Dashboard and Verification Suite.
 */
public class LoggingExampleTest implements ActionListener {

    private static JTextField errorInputField;
    private static JTextField warnInputField;
    private static JTextArea loggingMonitorConsole;
    private static JButton processLogsButton;

    private final LoggingExample loggingExample = new LoggingExample();

    /**
     * 🤖 AUTOMATED JUNIT TEST
     */
    @Test
    public void testLoggerInitialization() {
        System.out.println("🧪 [JUnit 5 Automated] Verifying SLF4J Logger binding factories...");
        org.slf4j.Logger checkLogger = LoggerFactory.getLogger(LoggingExample.class);
        assertNotNull(checkLogger, "❌ SLF4J Logger initialization failed!");
        System.out.println("✔️ [JUnit 5 Automated] Logger initialization successfully validated.");
    }

    /**
     * 🎮 VISUAL SWING INTERFACE
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("🪵 SLF4J Logger Studio - Exercise 1");
            frame.setSize(550, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(6, 6, 6, 6);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel errLabel = new JLabel("Custom ERROR Payload Message:");
            errorInputField = new JTextField("CRITICAL_ERR: Database row isolation failed!", 25);

            JLabel warnLabel = new JLabel("Custom WARN Payload Message:");
            warnInputField = new JTextField("WARN: High memory garbage collection overhead detected.", 25);

            processLogsButton = new JButton("Dispatch Inputs via SLF4J Logging Subsystem");
            
            loggingMonitorConsole = new JTextArea(10, 42);
            loggingMonitorConsole.setEditable(false);
            loggingMonitorConsole.setText("SLF4J Wiretap Log Stream: Idle.\nClick button to pipe messages through logback classic.");
            loggingMonitorConsole.setBorder(BorderFactory.createEtchedBorder());
            loggingMonitorConsole.setFont(new Font("Monospaced", Font.PLAIN, 12));

            processLogsButton.addActionListener(new LoggingExampleTest());

            gbc.gridx = 0; gbc.gridy = 0; panel.add(errLabel, gbc);
            gbc.gridx = 1; panel.add(errorInputField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 1; panel.add(warnLabel, gbc);
            gbc.gridx = 1; panel.add(warnInputField, gbc);
            
            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; panel.add(processLogsButton, gbc);
            gbc.gridy = 3; gbc.fill = GridBagConstraints.BOTH; panel.add(loggingMonitorConsole, gbc);

            frame.add(panel);
            frame.setVisible(true);
        });
    }

    /**
     * ⚡ DYNAMIC LOGGER METRIC DISPATCHER
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        loggingMonitorConsole.setText("");
        
        String inputError = errorInputField.getText().trim();
        String inputWarn = warnInputField.getText().trim();

        loggingMonitorConsole.append("1. [App Routing] Capturing UI text tokens...\n");
        loggingMonitorConsole.append("2. [SLF4J Core] Routing payloads to logback-classic Appenders...\n\n");

        // Execute direct code and check your Eclipse standard terminal console too!
        loggingExample.logCustomError(inputError);
        loggingExample.logCustomWarning(inputWarn);

        // Printing simulated pattern format inside the dashboard view
        loggingMonitorConsole.append("================ Logback Append Summary ================\n");
        loggingMonitorConsole.append("🔴 [ERROR] - " + inputError + "\n");
        loggingMonitorConsole.append("🟡 [WARN]  - " + inputWarn + "\n");
        loggingMonitorConsole.append("========================================================\n\n");
        loggingMonitorConsole.append("✔️ DISPATCH SUCCESS: Look down at your Eclipse Console view to see the native timestamped logback outputs!");
    }
}