package psicanagrammer.gevapps.com.psicanagrammer.dto;

import java.io.Serializable;
import java.util.Date;

import psicanagrammer.gevapps.com.psicanagrammer.engine.Reportable;

/**
 * Created by Geva on 14/03/2015.
 */
public class Report implements Serializable {
    protected String fileReportName;
    protected Date timestamp;
    protected Group group;
    protected QuestionResults resultQuestions;

    public Report() {}

    public Report(final Group group) {
        this.group = group;
    }

    public void addRegister(int phaseIndex, Record record) {
        group.addRegister(phaseIndex,record);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public QuestionResults getResultQuestions() {
        return resultQuestions;
    }

    public void setResultQuestions(QuestionResults resultQuestions) {
        this.resultQuestions = resultQuestions;
    }

    public String getFileReportName() {
        return fileReportName;
    }

    public void setFileReportName(String fileReportName) {
        this.fileReportName = fileReportName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
