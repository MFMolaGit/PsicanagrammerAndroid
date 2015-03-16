package psicanagrammer.gevapps.com.psicanagrammer.engine;

import psicanagrammer.gevapps.com.psicanagrammer.dto.Report;

/**
 * Created by Geva on 15/03/2015.
 */
public abstract class Reporter implements Reportable {

    protected Report report;
    protected String fileReportName;

    public Reporter(final String fileReportName) {
        this.fileReportName = fileReportName;
    }

    @Override
    public void setReport(Report report) {
        this.report = report;
    }
}
