package br.unb.cic.test.unit;

import java.util.Set;

public class DefaultReport extends Report {

    @Override
    public void export() {
        for (TestResult result : testResults) {
            System.out.println("Test Case: " + result.getTestCaseName());
            System.out.println("Successes: " + result.getSuccesses());
            System.out.println("Errors: " + result.getErrors());
            System.out.println("Failures: " + result.getFailures());
            System.out.println();
        }
    }
}

