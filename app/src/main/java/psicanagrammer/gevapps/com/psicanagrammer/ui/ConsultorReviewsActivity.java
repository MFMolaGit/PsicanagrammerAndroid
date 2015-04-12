package psicanagrammer.gevapps.com.psicanagrammer.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import psicanagrammer.gevapps.com.psicanagrammer.R;
import psicanagrammer.gevapps.com.psicanagrammer.utils.ActivityUtils;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

public class ConsultorReviewsActivity extends ListActivity {

    private Vector<String> reviewsNames;
    private ConsultorReviewAdapter adapterReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_consultor_reviews);

        loadReviews();

    }

    private void loadReviews() {

        reviewsNames = new Vector<String>();

        File reviewsFolder = new File(Constants.FILE_PATH);

            if(reviewsFolder.exists() &&  reviewsFolder.isDirectory()) {
                reviewsNames.addAll(filterFileInputs(reviewsFolder.list()));
            }

        adapterReviews = new ConsultorReviewAdapter(this, reviewsNames,getApplicationContext());
		setListAdapter(adapterReviews);
	
    }

    public void back(final View view) {
        finish();
    }

    private Vector<String> filterFileInputs(final String[] list) {
        Vector<String> finalFiles = new Vector<>();

        for(String file:list) {
            if(file.endsWith(Constants.XML_EXTENSION)) {
                int posDot = file.lastIndexOf(Constants.XML_EXTENSION);
                finalFiles.add(file.substring(0, posDot));
            }
        }

        return finalFiles;
    }

}
