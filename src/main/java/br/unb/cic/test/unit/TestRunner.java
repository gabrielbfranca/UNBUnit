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
    public Set<Report> reports;
    @Inject
    public TestRunner(Set<Report> reports) {
        this.reports = reports;
        reports.add(new DefaultReport());
        reports.add(new JsonReportGenerator());

    }

    public void exportReport(Class<? extends Report> reportType, String filePath, Set<TestResult> results) {
        for (Report report : reports) {
            if (reportType.isInstance(report)) {
                report.setFilePath(filePath);
                report.setResult(results);
                report.export();
                return;
            }
        }

        throw new IllegalArgumentException("Report of type " + reportType.getSimpleName() + " not found in the reports list.");
    }

    public void exportAllReports(String filePath, Set<TestResult> results, boolean silent) {
        for (Report report : reports) {
            if (silent && report instanceof DefaultReport) {
                continue;
            }
            report.setFilePath(filePath);
            report.setResult(results);
            report.export();
        }
    }

    public void removeReport(Class<? extends Report> reportType) {

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

        for (Report report : reports) {
            report.setResult(result);
        }

        return result;
    }


}
