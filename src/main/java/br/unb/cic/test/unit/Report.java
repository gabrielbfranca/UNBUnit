package br.unb.cic.test.unit;

import java.util.Set;

public interface Report {
     void export();
     void setResult(Set<TestResult> testResults);
}
