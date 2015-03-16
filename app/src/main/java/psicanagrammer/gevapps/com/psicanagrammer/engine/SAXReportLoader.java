package psicanagrammer.gevapps.com.psicanagrammer.engine;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import psicanagrammer.gevapps.com.psicanagrammer.dto.Report;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

/**
 * Created by Geva on 14/03/2015.
 */
public class SAXReportLoader implements Readable {

    private String fileReportName;
    private String pacientName;

    public SAXReportLoader(final String pacientName) {
        this.pacientName = pacientName;
        this.fileReportName = Constants.FILE_PATH+"/"+pacientName;
    }

    @Override
    public Report readReport() {
        ReportXMLHandler reportXMLHandler = new ReportXMLHandler();
        try {
            InputStream in = new FileInputStream(fileReportName);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setContentHandler(reportXMLHandler);
            reader.parse(new InputSource(in));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reportXMLHandler.getReport();
    }
}
