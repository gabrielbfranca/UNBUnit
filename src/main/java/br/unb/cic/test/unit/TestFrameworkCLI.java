package br.unb.cic.test.unit;


import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestFrameworkCLI {
    private static final String TYPE_OPTION = "type";
    private static final String SILENT_OPTION = "silent";
    private static final String OUT_OPTION = "out";
    private static final String FILE_OPTION = "file";

    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Options options = createOptions();

        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption(TYPE_OPTION)) {
                String type = cmd.getOptionValue(TYPE_OPTION);

                if (type.equalsIgnoreCase("default")) {
                    runDefaultTests(cmd);
                } else if (type.equalsIgnoreCase("suite")) {
                    runSuiteTests(cmd);
                } else {
                    System.err.println("Invalid test type: " + type);
                }
            } else {
                System.err.println("Test type not specified.");
                printHelp(options);
            }
        } catch (ParseException e) {
            System.err.println("Error parsing command-line arguments: " + e.getMessage());
            printHelp(options);
        }
    }

    private static Options createOptions() {
        Options options = new Options();

        options.addOption(Option.builder(TYPE_OPTION)
                .longOpt("type")
                .argName("testType")
                .desc("Specify the type of test (default or suite)")
                .hasArg()
                .required()
                .build());

        options.addOption(Option.builder(SILENT_OPTION)
                .longOpt("silent")
                .desc("Run the tests silently without printing the default report")
                .build());

        options.addOption(Option.builder(OUT_OPTION)
                .longOpt("out")
                .argName("fileType")
                .desc("Specify the type of output file for the report")
                .hasArg()
                .build());

        options.addOption(Option.builder(FILE_OPTION)
                .longOpt("file")
                .argName("path")
                .desc("Specify the file path for the report")
                .hasArg()
                .required()
                .build());

        return options;
    }

    private static void runDefaultTests(CommandLine cmd) {
        boolean silent = cmd.hasOption(SILENT_OPTION);
        String fileType = cmd.getOptionValue(OUT_OPTION, "");
        String filePath = cmd.getOptionValue(FILE_OPTION);

        Set<Report> reports = new HashSet<>();
        TestRunner testRunner = new DefaultRunner(reports);

        Set<TestResult> results = testRunner.runAllTests();


        Map<String, Class<? extends Report>> reportTypeMap = getReportTypemap();


        if (!silent) {
            testRunner.exportReport(DefaultReport.class, filePath, results);
        }

        if (fileType.isEmpty()) {

            for (Class<? extends Report> reportClass : reportTypeMap.values()) {
                if (reportClass.equals(DefaultReport.class)) {
                    continue; // Skip exporting DefaultReport when silent mode is enabled
                }

                testRunner.exportReport(reportClass, filePath, results);
            }
        } else {
            Class<? extends Report> reportClass = reportTypeMap.get(fileType);
            if (reportClass == null) {
                System.err.println("Unsupported file type: " + fileType);
                return;
            }

            testRunner.exportReport(reportClass, filePath, results);

        }


    }

    private static void runSuiteTests(CommandLine cmd) {
        // TODO: Implement running suite tests
    }

    private static Map<String, Class<? extends Report>> getReportTypemap() {
        Map<String, Class<? extends Report>> reportTypeMap = new HashMap<>();
        reportTypeMap.put("default", DefaultReport.class);
        reportTypeMap.put("json", JsonReportGenerator.class);
        // add report type here

        return reportTypeMap;
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("test-framework-cli", options, true);
    }
}

