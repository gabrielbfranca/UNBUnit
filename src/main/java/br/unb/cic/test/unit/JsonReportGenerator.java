package br.unb.cic.test.unit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JsonReportGenerator extends Report {

    @Override
    public void export() {
        List<JsonObject> reports = listReports();
        for (JsonObject report : reports) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println(gson.toJson(report));
        }
    }


    private List<JsonObject> listReports() {

        List<JsonObject> reports = new ArrayList<>();

        for (TestResult testResult : testResults) {


            JsonObject report = new JsonObject();
            report.addProperty("testCase", testResult.getTestCaseName());
            report.add("successes", toJsonArray(testResult.getSuccesses()));
            report.add("errors", toJsonArray(testResult.getErrors()));
            report.add("failures", toJsonArray(testResult.getFailures()));
            reports.add(report);
        }

        return reports;
    }

    private JsonArray toJsonArray(Set<String> strings) {
        JsonArray jsonArray = new JsonArray();
        for (String string : strings) {
            jsonArray.add(string);
        }
        return jsonArray;
    }

}
