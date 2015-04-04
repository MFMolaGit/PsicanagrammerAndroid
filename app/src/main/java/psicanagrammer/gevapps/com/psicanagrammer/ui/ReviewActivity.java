package psicanagrammer.gevapps.com.psicanagrammer.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

import psicanagrammer.gevapps.com.psicanagrammer.R;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Report;
import psicanagrammer.gevapps.com.psicanagrammer.engine.*;
import psicanagrammer.gevapps.com.psicanagrammer.engine.Readable;
import psicanagrammer.gevapps.com.psicanagrammer.utils.ActivityUtils;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

public class ReviewActivity extends FragmentActivity {

    private FragmentTabHost tabHost;
    private Readable saxReportLoader;
    private String fileReportName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        Report report = loadReport();

        Bundle argsToGroupTab = new Bundle();
            argsToGroupTab.putSerializable("report", report);

        Bundle argsToPhasesTab = new Bundle();
        argsToPhasesTab.putSerializable("group", report.getGroup());

        Bundle argsToQuestionsTab = new Bundle();
        argsToQuestionsTab.putSerializable("questions", report.getResultQuestions());

        tabHost.addTab(tabHost.newTabSpec("group_review_tab").setIndicator("Grupo"),GroupReviewTab.class,argsToGroupTab);
        tabHost.addTab(tabHost.newTabSpec("phases_review_tab").setIndicator("Fases"),PhasesReviewTab.class,argsToPhasesTab);
        tabHost.addTab(tabHost.newTabSpec("questions_review_tab").setIndicator("Preguntas"),QuestionsReviewTab.class,argsToQuestionsTab);

    }

    public Report loadReport() {
        fileReportName = getIntent().getExtras().getString("fileName");
        saxReportLoader = new SAXReportLoader(fileReportName + Constants.XML_EXTENSION);
        return saxReportLoader.readReport();
    }

    public void back(final View view) {
        finish();
    }

    public void sendReviewMail(final View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Psicanagrammer. Informe - " + fileReportName);
            intent.putExtra(Intent.EXTRA_TEXT, "Informe enviado desde app Psicanagrammer");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"belenfelisa.granados@gmail.com", "manuelf.mola@gmail.com"});
			
			String txtFile = Constants.FILE_PATH + Constants.FILE_TEXT_EXT.replace("*", fileReportName);
            File fileTxtIn = new File(txtFile);
            Uri fileUriTxt = Uri.fromFile(fileTxtIn);
            intent.putExtra(Intent.EXTRA_STREAM, fileUriTxt);
			
			/*String xmlFile = Constants.FILE_PATH + Constants.FILE_XML_EXT.replace("*", fileReportName);
			File fileXmlIn = new File(xmlFile);
			Uri fileUriXml= Uri.fromFile(fileXmlIn);
			intent.putExtra(Intent.EXTRA_STREAM, fileUriXml);*/
		
			startActivity(intent);
			
			/*List<Uri> uris = new ArrayList<Uri>();
				uris.add(Uri.parse(txtFile));
				uris.add(Uri.parse(xmlFile));
			intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
			startActivityForResult(Intent.createChooser(intent, "Sending multiple attachment"), 12345);*/
    }
}
