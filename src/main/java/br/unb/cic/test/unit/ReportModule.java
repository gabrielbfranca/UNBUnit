package br.unb.cic.test.unit;

import com.google.inject.AbstractModule;

public class ReportModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Report.class).to(JsonReportGenerator.class);
        // Bind other report types
    }
}

