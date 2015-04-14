package psicanagrammer.gevapps.com.psicanagrammer.engine;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Group;
import psicanagrammer.gevapps.com.psicanagrammer.dto.QuestionResults;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Record;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Report;
import psicanagrammer.gevapps.com.psicanagrammer.dto.State;

/**
 * Created by Geva on 20/02/2015.
 */
public class ReportGenerator implements Serializable {

    private Group groupsLoaded;
    private Report report;
    private Reportable generatorSAX, generatorFile;

    public ReportGenerator(final Report report, final String pacientName) {
        this.report = report;
        report.setFileReportName(pacientName);

        generatorSAX = new SAXReporter(pacientName,report);
        generatorFile  = new FileReporter(pacientName,report);
    }

    public ReportGenerator(final Group activeGroup, final String pacientName) {
        groupsLoaded = activeGroup;

        report = reportClone(groupsLoaded);

        generatorSAX = new SAXReporter(pacientName,report);
        generatorFile  = new FileReporter(pacientName,report);
    }

    private Report reportClone(final Group groupLoaded) {
     Report report = null;

        try {
            report = new Report((Group) groupsLoaded.clone());
        } catch (CloneNotSupportedException e) {
            System.out.println("Error en el clone");
        }

     return report;
    }

    public void createRecord(final int phaseIndex, final int anagramIndex, final String response, final int seconds, final Date timestamp, final State state) {

        Record record;

         try {
            record = (Record)groupsLoaded.getRegister(phaseIndex,anagramIndex).clone();

                record.setTimestamp(timestamp);
                record.setResponse(response);
                record.setSeconds(seconds);
                record.setCorrect(state);

            report.addRegister(phaseIndex, record);
            System.out.println("*************** Genera reporte ******************");
            System.out.println("*************** "+record+" ******************");

        } catch (CloneNotSupportedException e) {
            System.out.println("Error en el clone record. No se insertó registro.");
        }

    }

    public void generateReport() throws IOException {
        generatorSAX.setReport(report);
        generatorSAX.generateReport();
        generatorFile.setReport(report);
        generatorFile.generateReport();
    }

    public void setResultQuestions(QuestionResults resultQuestions) {
        report.setResultQuestions(resultQuestions);
    }

    public void setTimestamp(final Date timestamp) {
        report.setTimestamp(timestamp);
    }
}
