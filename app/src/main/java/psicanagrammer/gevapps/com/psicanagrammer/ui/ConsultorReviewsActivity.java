package psicanagrammer.gevapps.com.psicanagrammer.ui;

import android.app.Activity;
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
import java.util.List;

import psicanagrammer.gevapps.com.psicanagrammer.R;
import psicanagrammer.gevapps.com.psicanagrammer.utils.ActivityUtils;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

public class ConsultorReviewsActivity extends Activity {

    private ListView reviewsListView;
    private List<String> reviewsNames;
    private ArrayAdapter<String> adapterReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_consultor_reviews);

        loadReviews();
    }

    private void loadReviews() {

        reviewsListView = (ListView)findViewById(R.id.reviewsView);
        reviewsNames = new ArrayList<String>();

        File reviewsFolder = new File(Constants.FILE_PATH);

            if(reviewsFolder.exists() &&  reviewsFolder.isDirectory()) {
                reviewsNames.addAll(filterFileInputs(reviewsFolder.list()));
            }

        adapterReviews = new ArrayAdapter<String> ( this, android.R.layout.simple_list_item_1, reviewsNames);
        reviewsListView.setAdapter(adapterReviews);

        reviewsListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fileSelected = reviewsNames.get(position);

                ActivityUtils.showMessageInToast("Has elegido " + fileSelected, getApplicationContext(), getResources().getColor(R.color.white));
                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                    intent.putExtra("fileName", fileSelected);
                    startActivity(intent);
            }
        });

    }



    public void back(final View view) {
        finish();
    }

    private List<String> filterFileInputs(final String[] list) {
        List<String> finalFiles = new ArrayList<>();

        for(String file:list) {
            if(file.endsWith(Constants.XML_EXTENSION)) {
                int posDot = file.lastIndexOf(Constants.XML_EXTENSION);
                finalFiles.add(file.substring(0, posDot));
            }
        }

        return finalFiles;
    }

}
