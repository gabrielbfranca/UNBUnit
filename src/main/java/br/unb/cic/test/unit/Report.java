package br.unb.cic.test.unit;

import java.util.Set;

public abstract class Report {

        Set<TestResult> testResults;
        public abstract void export();
        public void setResult(Set<TestResult> testResults) {this.testResults = testResults;}

}
