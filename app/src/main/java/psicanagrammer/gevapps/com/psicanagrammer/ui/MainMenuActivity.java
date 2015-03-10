package psicanagrammer.gevapps.com.psicanagrammer.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import psicanagrammer.gevapps.com.psicanagrammer.R;

public class MainMenuActivity extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
}
