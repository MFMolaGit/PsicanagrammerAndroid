package psicanagrammer.gevapps.com.psicanagrammer.engine;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.Date;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Group;
import psicanagrammer.gevapps.com.psicanagrammer.dto.QuestionResults;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Record;
import psicanagrammer.gevapps.com.psicanagrammer.dto.State;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

/**
 * Created by Geva on 20/02/2015.
 */
public class ReportGenerator implements Serializable {

    private String pacientName;
    private String fileReportName;
    private Group groupsLoaded;
    private Group report;
    private QuestionResults resultQuestions;

    public ReportGenerator(final Group activeGroup, final String pacientName) {
        groupsLoaded = activeGroup;
        this.pacientName = pacientName;
        fileReportName = Constants.FILE_PATH+Constants.FILE_EXT.replace("*", pacientName.trim().replace(" ", "_"));
        try {
            report = (Group) groupsLoaded.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Error en el clone");
        }
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

    public String getFileReportName() {
        return fileReportName;
    }

    // write on SD card file data in the text box
    public void generateReportFile() throws Exception {
        String folderPath = fileReportName.substring(0, fileReportName.lastIndexOf("/"));
        File folderReport = new File(folderPath);

        boolean folderCreated = false;
        if(!folderReport.exists()) {
            folderCreated = folderReport.mkdirs();
        }

        boolean fileCreated = false;
        File fileReport = null;
        if(folderReport.exists() || folderCreated) {
            fileReport = new File(fileReportName);
            fileCreated = fileReport.createNewFile();
        }

        if(fileCreated && fileReport != null) {
            FileOutputStream fOut = new FileOutputStream(fileReport);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append("---- INFORME ----")
                .append("Nombre: ")
                .append(fileReportName)
                .append("\nFecha: ")
                .append(Constants.SIMPLE_DATE_FORMAT.format(new Date()))
                .append(report.toString())
                .append(resultQuestions.toString());
            myOutWriter.close();
            fOut.close();
        } else {
            System.out.println("Error al generar el reporte. El fichero o la carpeta no se pudo crear.");
        }

    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void setResultQuestions(QuestionResults resultQuestions) {
        this.resultQuestions = resultQuestions;
    }
}
