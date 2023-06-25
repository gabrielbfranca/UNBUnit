package br.unb.cic.test.unit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Set;

public class JsonReportGenerator {
    public static String generateReport(Set<TestResult> testResults) {
        JsonObject report = new JsonObject();
        report.addProperty("framework", "MinimalistTestFramework");
        report.add("results", serializeResults(testResults));

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(report);
    }

    private static JsonArray serializeResults(Set<TestResult> testResults) {
        JsonArray resultsArray = new JsonArray();
        for (TestResult result : testResults) {
            JsonObject resultObject = new JsonObject();
            resultObject.addProperty("testCase", result.getTestCaseName());
            resultObject.add("successes", toJsonArray(result.getSuccesses()));
            resultObject.add("errors", toJsonArray(result.getErrors()));
            resultObject.add("failures", toJsonArray(result.getFailures()));
            resultsArray.add(resultObject);
        }
        return resultsArray;
    }

    private static JsonArray toJsonArray(Set<String> strings) {
        JsonArray jsonArray = new JsonArray();
        for (String string : strings) {
            jsonArray.add(string);
        }
        return jsonArray;
    }
}
