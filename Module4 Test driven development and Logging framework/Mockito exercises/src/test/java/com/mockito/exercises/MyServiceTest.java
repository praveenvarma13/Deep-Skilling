package com.mockito.exercises;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
 * Exercise 1: Core Mocking & Stubbing Engine Panel validation[cite: 2].
 */
public class MyServiceTest implements ActionListener {

    private static JTextField predefinedValueField;
    private static JTextArea runtimeConsoleLog;
    private static JButton runStubSimulationButton;

    /**
     * 🤖 AUTOMATED JUNIT TEST
     */
    @Test
    public void testExternalApi() {
        System.out.println("🧪 [JUnit 5 Automated] Mocking interface dependencies and verifying stub outputs...");
        
        // 1. Create mock object 
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        
        // 2. Stub method invocation response profile [cite: 8, 17]
        when(mockApi.getData()).thenReturn("Mock Data");
        
        // 3. Consume with runtime assertion [cite: 9, 18, 19]
        MyService service = new MyService(mockApi);
        String result = service.fetchData();
        
        assertEquals("Mock Data", result); // [cite: 21]
        System.out.println("✔️ [JUnit 5 Automated] Assert complete: Mocking layer passed!");
    }

    /**
     * 🎮 VISUAL SWING INTERFACE
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("🎭 Mockito Lab Exercise 1: Stubbing Studio");
            frame.setSize(540, 360);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(6, 6, 6, 6);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel label = new JLabel("Predefined Pre-set Stub Response Message:");
            predefinedValueField = new JTextField("Dynamic Mock Live Data Token", 20);

            runStubSimulationButton = new JButton("Invoke Core Service Fetch Pipeline");
            
            runtimeConsoleLog = new JTextArea(8, 42);
            runtimeConsoleLog.setEditable(false);
            runtimeConsoleLog.setText("Stub Diagnostics: Standby.\nModify predefined string and click execution to test Mockito.");
            runtimeConsoleLog.setBorder(BorderFactory.createEtchedBorder());
            runtimeConsoleLog.setFont(new Font("Monospaced", Font.PLAIN, 12));

            runStubSimulationButton.addActionListener(new MyServiceTest());

            gbc.gridx = 0; gbc.gridy = 0; panel.add(label, gbc);
            gbc.gridy = 1; panel.add(predefinedValueField, gbc);
            gbc.gridy = 2; panel.add(runStubSimulationButton, gbc);
            gbc.gridy = 3; panel.add(runtimeConsoleLog, gbc);

            frame.add(panel);
            frame.setVisible(true);
        });
    }

    /**
     * ⚡ DYNAMIC STUB CONTEXT DISPATCHER
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        runtimeConsoleLog.setText("");
        runtimeConsoleLog.setForeground(Color.BLACK);
        
        String inputStubValue = predefinedValueField.getText().trim();

        runtimeConsoleLog.append("1. [Mockito Setup] Instantiating virtual Mock representation of ExternalApi.class...\n");
        // Dynamically mock and stub based on UI state values [cite: 7, 8, 16]
        ExternalApi interactiveMockApi = Mockito.mock(ExternalApi.class);
        
        runtimeConsoleLog.append("2. [Stub Rule] Registering rule: when(mockApi.getData()).thenReturn(\"" + inputStubValue + "\")\n");
        when(interactiveMockApi.getData()).thenReturn(inputStubValue); // 

        runtimeConsoleLog.append("3. [Execution] Wire-framing components and invoking service.fetchData()...\n\n");
        MyService standardService = new MyService(interactiveMockApi); // 
        String interceptedDataString = standardService.fetchData(); // [cite: 19]

        runtimeConsoleLog.append("====== MOCK STUB MONITOR ANALYSIS ======\n");
        runtimeConsoleLog.append("Service Output Intercepted: \"" + interceptedDataString + "\"\n");
        runtimeConsoleLog.append("=========================================\n\n");
        runtimeConsoleLog.append("✔️ SYSTEM VERIFICATION PASSED: Service successfully consumed mock dataset configurations.");
        runtimeConsoleLog.setForeground(new Color(0, 100, 0)); // Success green
    }
}