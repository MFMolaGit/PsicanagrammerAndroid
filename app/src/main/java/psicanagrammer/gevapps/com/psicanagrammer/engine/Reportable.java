package psicanagrammer.gevapps.com.psicanagrammer.engine;

import java.io.IOException;

import psicanagrammer.gevapps.com.psicanagrammer.dto.Group;
import psicanagrammer.gevapps.com.psicanagrammer.dto.QuestionResults;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Report;

/**
 * Created by Geva on 14/03/2015.
 */
public interface Reportable {

    public void generateReport();

    public void setReport(final Report report);

}
