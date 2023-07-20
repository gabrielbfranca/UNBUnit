package br.unb.cic.test.unit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class JsonReportGenerator extends Report {


    @Override
    public void export() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject reportObject = new JsonObject();

        JsonArray testResultsArray = new JsonArray();
        for (TestResult testResult : testResults) {
            JsonObject testResultObject = new JsonObject();
            testResultObject.addProperty("testCase", testResult.getTestCaseName());
            testResultObject.add("successes", toJsonArray(testResult.getSuccesses()));
            testResultObject.add("errors", toJsonArray(testResult.getErrors()));
            testResultObject.add("failures", toJsonArray(testResult.getFailures()));
            testResultsArray.add(testResultObject);
        }

        reportObject.add("testResults", testResultsArray);

        try (FileWriter writer = new FileWriter(filePath + ".json")) {
            gson.toJson(reportObject, writer);
        } catch (IOException e) {
            System.err.println("Error exporting JSON report: " + e.getMessage());
        }
    }

    private JsonArray toJsonArray(Set<String> strings) {
        JsonArray jsonArray = new JsonArray();
        for (String string : strings) {
            jsonArray.add(string);
        }
        return jsonArray;
    }

}
