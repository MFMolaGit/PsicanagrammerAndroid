package psicanagrammer.gevapps.com.psicanagrammer.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;

import java.util.Arrays;
import java.util.List;

import psicanagrammer.gevapps.com.psicanagrammer.R;
import psicanagrammer.gevapps.com.psicanagrammer.utils.ActivityUtils;

public class MainMenuActivity extends ActionBarActivity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getBooleanExtra("EXIT",false)) {
            finish();
        }

        setContentView(R.layout.initial_menu_activity);
	}
	
	public void inspect(final View view) {
        Intent intent = new Intent(this, ConsultorReviewsActivity.class);
        startActivity(intent);
	}
	
	public void configurateTest(final View view) {
		Intent intent = new Intent(this, Psicanagram.class);
		startActivity(intent);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            ActivityUtils.showMessageInToast(String.valueOf(getResources().getText(R.string.aboutText)), getBaseContext(),
                    getResources().getColor(R.color.white), 20, true);
        }

        return super.onOptionsItemSelected(item);
    }

}
