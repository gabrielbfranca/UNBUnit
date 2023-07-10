package br.unb.cic.test.unit;

import br.unb.cic.test.unit.eh.TestCaseInstantiationError;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A <code>TestRunner</code> that someone has to explicitly
 * state the TestCases, through the <code>withTestCase</code>
 * method.
 */
public class SuiteRunner extends TestRunner {
    private Set<Class<? extends TestCase>> testClasses;

    public SuiteRunner(Set<Report> reports) {
        super(reports);
        testClasses = new HashSet<>();
    }
    /**
     * Records a TestCase class in the test suite.
     * @param c A class that inherits from <code>TestCase</code>
     *
     * @return An instance of <code>SuiteRunner</code>
     */
    public SuiteRunner withTestCase(Class<? extends TestCase> c) {
        testClasses.add(c);
        return this;
    }

    @Override
    public Set<TestCase> listTestCases() {
        Set<TestCase> testCases = new HashSet<>();
        for (Class<? extends TestCase> c : testClasses) {
            try {
                testCases.add(c.newInstance());
            } catch (Throwable e) {
                throw new TestCaseInstantiationError(c);
            }
        }
        return testCases;
    }


}
