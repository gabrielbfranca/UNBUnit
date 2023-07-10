package br.unb.cic.test.unit;

import java.util.HashSet;
import java.util.Set;

public class TestFrameworkCLI {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Error: Insufficient arguments.");
            System.err.println("Usage: java TestFrameworkCLI <reportType> <testType>");
            System.exit(1);
        }

        // Get the report type and test type from command-line arguments
        String reportType = args[0];
        String testType = args[1];

        // Create an instance of the selected test runner
        TestRunner runner = createTestRunner(testType);

        // Export the report based on the specified report type
        exportReport(runner, reportType);

    }

    private static TestRunner createTestRunner(String testType) {
        // Implement your logic to create an instance of the test runner
        // Return the appropriate TestRunner object based on the specified test type
        Set<Report> reports = new HashSet<>();
        reports.add(new JsonReportGenerator());

        if (testType.equalsIgnoreCase("suite")) {
            return new SuiteRunner(reports);
        } else if (testType.equalsIgnoreCase("default")) {
            return new DefaultRunner(reports);
        } else {
            throw new IllegalArgumentException("Invalid test type: " + testType);
        }
    }

    private static void exportReport(TestRunner runner, String reportType) {
        Set<TestResult> result = runner.runAllTests();
        if (reportType == null) {
            System.out.println("No report type specified. Skipping report export.");
            printTestResults(result);
            return;
        }

        if (reportType.equalsIgnoreCase("json")) {
            runner.exportReport(JsonReportGenerator.class);
        } else {
            System.out.println("Unsupported report type: " + reportType);
        }
    }

    private static void printTestResults(Set<TestResult> results) {
        // Print the test results as per your desired format
        for (TestResult result : results) {
            System.out.println("Test Case: " + result.getTestCaseName());
            System.out.println("Successes: " + result.getSuccesses());
            System.out.println("Errors: " + result.getErrors());
            System.out.println("Failures: " + result.getFailures());
            System.out.println();
        }
    }
}
