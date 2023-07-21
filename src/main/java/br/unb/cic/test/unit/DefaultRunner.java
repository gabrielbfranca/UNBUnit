package br.unb.cic.test.unit;

import br.unb.cic.test.unit.eh.Failure;

import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;

/**
 * A <code>TestRunner</code> that searches for all classes that inherit
 * from <code>TestCase</code>.
 */
public class DefaultRunner extends TestRunner {

    public DefaultRunner(Set<Report> reports) {
        super(reports);
    }

    @Override
    public Set<TestCase> listTestCases(String packagePath) {
        try {

            Reflections reflections = new Reflections(packagePath);
            Set<Class<? extends TestCase>> testCaseClasses = reflections.getSubTypesOf(TestCase.class);
            Set<TestCase> testCases = new HashSet<>();
            for (Class<? extends TestCase> c : testCaseClasses) {
                testCases.add(c.newInstance());
            }

            Set<TestResult> testResults = new HashSet<>();
            for (TestCase testCase : testCases) {
                TestResult result = new TestResult(testCase.getClass().getName());
                try {
                    testCase.run();
                    // Report success if the test case execution completes without errors or failures
                    result.reportSuccess(testCase.getClass().getName());
                } catch (AssertionError error) {
                    // Report failure if an assertion error occurs during the test case execution
                    result.reportFailure(testCase.getClass().getName());
                } catch (Exception e) {
                    // Report error if any other exception occurs during the test case execution
                    result.reportError(testCase.getClass().getName());
                }
                testResults.add(result);
            }

            return testCases;
        }
        catch(Throwable e) {
            throw new Failure(e.getMessage());
        }
    }

}
