package br.unb.cic.test.unit;

import javax.swing.*;
import java.awt.*;

public class TestResultGUI extends JFrame{
    private TestResult testResult;

    public TestResultGUI(TestResult testResult) {
        this.testResult = testResult;
        initialize();
    }

    private void initialize() {
        setTitle("Test Result");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create the title label
        JLabel titleLabel = new JLabel("Test Case: " + testResult.getTestCaseName());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create the results panel
        JPanel resultsPanel = new JPanel(new GridLayout(3, 1));

        // Create the success label
        JLabel successLabel = new JLabel("Successes: " + testResult.getSuccesses().size());
        resultsPanel.add(successLabel);

        // Create the error label
        JLabel errorLabel = new JLabel("Errors: " + testResult.getErrors().size());
        resultsPanel.add(errorLabel);

        // Create the failure label
        JLabel failureLabel = new JLabel("Failures: " + testResult.getFailures().size());
        resultsPanel.add(failureLabel);

        mainPanel.add(resultsPanel, BorderLayout.CENTER);

        // Add the main panel to the frame
        add(mainPanel);
    }

    public static void main(String[] args) {
        // Example usage
        TestResult testResult = new TestResult("ExampleTestCase");
        testResult.reportSuccess("testMethod1");
        testResult.reportSuccess("testMethod2");
        testResult.reportError("testMethod3");
        testResult.reportFailure("testMethod4");

        SwingUtilities.invokeLater(() -> {
            TestResultGUI gui = new TestResultGUI(testResult);
            gui.setVisible(true);
        });
    }
}
