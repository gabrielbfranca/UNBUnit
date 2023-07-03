package br.unb.cic.test.unit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;


import java.util.HashSet;
import java.util.Set;

/**
 * All test runners must inherit from this class and
 * override the abstract method <code>listTestCases()</code>.
 */
public abstract class TestRunner {
    public abstract Set<TestCase> listTestCases();
    public abstract List<JsonObject> listReports();

    public void printAllReports() {
        List<JsonObject> reports = listReports();
        for (JsonObject report : reports) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println(gson.toJson(report));
        }
    }
    public JsonArray toJsonArray(Set<String> strings) {
        JsonArray jsonArray = new JsonArray();
        for (String string : strings) {
            jsonArray.add(string);
        }
        return jsonArray;
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
        return result;
    }
}
