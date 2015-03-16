package psicanagrammer.gevapps.com.psicanagrammer.engine;

import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

import psicanagrammer.gevapps.com.psicanagrammer.dto.Report;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

/**
 * Created by Geva on 14/03/2015.
 */
public class FileReporter extends Reporter {

    public FileReporter(final String pacientName, final Report report) {
        super(Constants.FILE_PATH+Constants.FILE_TEXT_EXT.replace("*", pacientName.trim().replace(" ", "_")));
        this.report = report;
        report.setFileReportName(pacientName);
    }

    // write on SD card file data in the text box
    @Override
    public void generateReport() {
        Date timestamp = new Date();
        report.setTimestamp(timestamp);
        String sTimestamp = Constants.SIMPLE_DATE_FORMAT.format(timestamp);
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
            try {
                fileCreated = fileReport.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(fileCreated && fileReport != null) {
            try {
                FileOutputStream fOut = new FileOutputStream(fileReport);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append("---- INFORME ----")
                        .append("Nombre: ")
                        .append(report.getFileReportName())
                        .append("\nFecha: ")
                        .append(sTimestamp)
                        .append(report.getGroup().toString())
                        .append(report.getResultQuestions().toString());
                    myOutWriter.close();
                    fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

}
