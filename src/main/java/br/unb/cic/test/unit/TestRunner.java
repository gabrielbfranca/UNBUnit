package br.unb.cic.test.unit;


import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

/**
 * All test runners must inherit from this class and
 * override the abstract method <code>listTestCases()</code>.
 */
public abstract class TestRunner {
    public abstract Set<TestCase> listTestCases();
    public Set<Report> Reports;
    @Inject
    public TestRunner(Set<Report> reports) {
        this.Reports = reports;

    }

    public void exportReport(Class<? extends Report> reportType) {
        for (Report report : Reports) {
            if (reportType.isInstance(report)) {
                report.export();
                return;
            }
        }

        throw new IllegalArgumentException("Report of type " + reportType.getSimpleName() + " not found in the reports list.");
    }

    /**
     * This is a (template) method that executes all
     * test cases coming from <code>listTestCases</code>.
     *
     * @return Return a set of test results---one for each test case
     * in run.
     */
    public Set<TestResult> runAllTests() {
        Set<TestCase> testCases = listTestCases();
        Set<TestResult> result = new HashSet<>();
        for(TestCase tc: testCases) {
            result.add(tc.run());

        }

        for (Report report : Reports) {
            report.setResult(result);
        }

        return result;
    }
}
