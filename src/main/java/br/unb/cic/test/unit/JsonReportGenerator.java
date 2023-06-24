package br.unb.cic.test.unit;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonReportGenerator {

    public void generateReport(List<TestResult> testResults, Map<String, Object> suiteInfo, String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        // Create a JSON object/map to store the report data
        Map<String, Object> reportData = createReportData(testResults, suiteInfo);

        try {
            // Convert the report data to a JSON string
            String reportJson = objectMapper.writeValueAsString(reportData);

            // Write the JSON string to a file
            File reportFile = new File(filePath);
            objectMapper.writeValue(reportFile, reportData);

            System.out.println("JSON report generated successfully!");
        } catch (IOException e) {
            System.err.println("Failed to generate JSON report: " + e.getMessage());
        }
    }

    private Map<String, Object> createReportData(List<TestResult> testResults, Map<String, Object> suiteInfo) {
        // Create a JSON object/map to store the report data
        Map<String, Object> reportData = new HashMap<>();

        // Add test results to the report data
        reportData.put("testResults", testResults);

        // Add suite information to the report data
        reportData.put("suiteInfo", suiteInfo);

        return reportData;
    }
}
