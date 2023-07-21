package br.unb.cic.test.unit;



import com.google.inject.Inject;

import java.util.HashSet;
import java.util.Set;

/**
 * All test runners must inherit from this class and
 * override the abstract method <code>listTestCases()</code>.
 */
public abstract class TestRunner {
    public abstract Set<TestCase> listTestCases(String packagePath);
    public Set<Report> reports;

    @Inject
    public TestRunner(Set<Report> reports) {

        this.reports = reports;

    }

    public Set<Report> getReports() {
        return reports;
    }

    /**
     * This is a (template) method that executes all
     * test cases coming from <code>listTestCases</code>.
     *
     * @return Return a set of test results---one for each test case
     * in run.
     */
    public Set<TestResult> runAllTests(String packagePath) {
        Set<TestCase> testCases = listTestCases(packagePath);
        Set<TestResult> result = new HashSet<>();
        for(TestCase tc: testCases) {
            result.add(tc.run());

        }

        return result;
    }


}
