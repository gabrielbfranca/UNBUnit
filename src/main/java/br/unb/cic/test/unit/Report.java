package br.unb.cic.test.unit;

import java.util.Set;

public abstract class Report {
        public String filePath;
        Set<TestResult> testResults;
        public abstract void export();

        public void setFilePath(String filePath) {
                this.filePath = filePath;
        }
        public void setResult(Set<TestResult> testResults) {this.testResults = testResults;}

}
