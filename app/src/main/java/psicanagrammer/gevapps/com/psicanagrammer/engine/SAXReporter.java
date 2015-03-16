package psicanagrammer.gevapps.com.psicanagrammer.engine;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import psicanagrammer.gevapps.com.psicanagrammer.dto.Report;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

/**
 * Created by Geva on 14/03/2015.
 */
public class SAXReporter extends Reporter {

    private String pacientName;

    public SAXReporter(final String pacientName,final Report report) {
        super(Constants.FILE_PATH+Constants.FILE_XML_EXT.replace("*", pacientName.trim().replace(" ", "_")));
        this.pacientName = pacientName;
        this.report = report;
        report.setFileReportName(pacientName);
    }

    @Override
    public void generateReport() {
        Date timestamp = new Date();
        report.setTimestamp(timestamp);
        String sTimestamp = Constants.SIMPLE_DATE_FORMAT.format(timestamp);
        XmlSerializer serializer = Xml.newSerializer();
        try {
            OutputStream out = new FileOutputStream(fileReportName);
            serializer.setOutput(out, "UTF-8");
            serializer.startDocument("UTF-8", true);
            serializer.startTag("","informe");
                serializer.startTag("","nombre");
                    serializer.text(pacientName);
                serializer.endTag("","nombre");
                serializer.startTag("","fecha");
                    serializer.text(sTimestamp);
                serializer.endTag("","fecha");
                report.getGroup().toXML(serializer);
                report.getResultQuestions().toXML(serializer);
            serializer.endTag("", "informe");
            serializer.endDocument();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
