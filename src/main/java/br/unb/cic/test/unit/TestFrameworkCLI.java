package br.unb.cic.test.unit;

import org.apache.commons.cli.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


// Exemplo de commando --type default --out json --file F:\\Users\\<usuario>\\Downloads\\report.json
public class TestFrameworkCLI {
    private static final String TYPE_OPTION = "type";
    private static final String SILENT_OPTION = "silent";
    private static final String OUT_OPTION = "out";
    private static final String FILE_OPTION = "file";

    private static final ReportManager reportManager = new ReportManager();



    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Options options = createOptions();

        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption(TYPE_OPTION)) {

                String type = cmd.getOptionValue(TYPE_OPTION);

                Set<Report> reports = new HashSet<>();

                if (type.equalsIgnoreCase("default")) {

                    DefaultRunner runner = new DefaultRunner(reports);
                    runTests(cmd, runner);
                } else if (type.equalsIgnoreCase("suite")) {
                    SuiteRunner runner = new SuiteRunner(reports);
                    runTests(cmd, runner);
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
                .desc("Specify the file path for the report, include the name of the file and type you want to export")
                .hasArg()
                .required()
                .build());

        return options;
    }

    private static void runTests(CommandLine cmd, TestRunner runner) {
        boolean silent = cmd.hasOption(SILENT_OPTION);
        String fileType = cmd.getOptionValue(OUT_OPTION, "");
        String filePath = cmd.getOptionValue(FILE_OPTION);


        Set<TestResult> results = runner.runAllTests();


        Map<String, Class<? extends Report>> reportTypeMap = reportManager.getReportTypemap();


        if (!silent) {

            reportManager.exportReport(runner.getReports(), "json", filePath, results);
        }

        if (fileType.isEmpty()) {

            for (String reportType : reportTypeMap.keySet()) {
                if (reportType.equals("default")) {
                    continue; // Skip exporting DefaultReport when silent mode is enabled
                }

                reportManager.exportReport(runner.getReports(), reportType, filePath, results);
            }
        } else {

            reportManager.exportReport(runner.getReports(), fileType, filePath, results);

        }

    }


    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("test-framework-cli", options, true);
    }
}

