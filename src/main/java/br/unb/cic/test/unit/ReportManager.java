package br.unb.cic.test.unit;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ReportManager {

    private final Map<String, Class<? extends Report>> reportTypeMap;

    public ReportManager() {
        reportTypeMap = new HashMap<>();
        reportTypeMap.put("default", DefaultReport.class);
        reportTypeMap.put("json", JsonReportGenerator.class);
        // Add more report types here
    }

    public Map<String, Class<? extends Report>> getReportTypemap() {

        return reportTypeMap;
    }

    public void exportReport(Set<Report> reports, String reportType, String filePath, Set<TestResult> results) {
        Class<? extends Report> reportClass = reportTypeMap.get(reportType);

        if (reportClass == null) {
            throw new IllegalArgumentException("Unsupported report type: " + reportType);
        }



        for (Report report : reports) {
            if (reportClass.isInstance(report)) {
                report.setFilePath(filePath);
                report.setResult(results);
                report.export();
                return;
            }
        }

        throw new IllegalArgumentException("Report of type " + reportType + " not found in the reports list.");
    }

}
