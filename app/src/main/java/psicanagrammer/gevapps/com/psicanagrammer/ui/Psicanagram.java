package psicanagrammer.gevapps.com.psicanagrammer.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import psicanagrammer.gevapps.com.psicanagrammer.R;
import psicanagrammer.gevapps.com.psicanagrammer.dto.ConfigInit;
import psicanagrammer.gevapps.com.psicanagrammer.utils.ActivityUtils;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;
import java.util.*;

public class Psicanagram extends Activity {

    private Spinner comboGroup;
    private EditText personFieldName;
    private NumberPicker timeLimit;
    private CheckBox totalTimeCheck,timeleftCheck, vibrateCheck;
    private CheckBox correctCheck,correctsOverTotalCheck,failsCheck,failsOverTotalCheck;
    private ConfigInit config;
    private int indexSecondsSelected;
    String[] values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getBooleanExtra("EXIT",false)) {
            Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
            startActivity(intent);
        }

        setContentView(R.layout.initial_config_activity);

        config = new ConfigInit();

        comboGroup = (Spinner) findViewById(R.id.spinnerView);
        personFieldName = (EditText) findViewById(R.id.personFieldName);
        timeLimit = (NumberPicker)  findViewById(R.id.numberPicker);
        totalTimeCheck = (CheckBox)  findViewById(R.id.enableTotalTimeCheck);
        timeleftCheck = (CheckBox)  findViewById(R.id.enableTimeleftCheck);
        correctCheck = (CheckBox)  findViewById(R.id.enableCorrectsCheck);
        correctsOverTotalCheck = (CheckBox)  findViewById(R.id.enableCorrectsOverTotalCheck);
        failsCheck = (CheckBox)  findViewById(R.id.enableFailsCheck);
        failsOverTotalCheck = (CheckBox)  findViewById(R.id.enableFailsOverTotalCheck);
		vibrateCheck = (CheckBox)  findViewById(R.id.enabledVibrateCheck);

            values=new String[12];

            for(int i= Constants.MIN_TIMELIMIT, j = 0;j<values.length;j++,i+=10){
                values[j]=Integer.toString(i);
            }

        timeLimit.setMaxValue(values.length-1);
        timeLimit.setMinValue(0);
        timeLimit.setDisplayedValues(values);
        timeLimit.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                indexSecondsSelected = newVal;
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.groups));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comboGroup.setAdapter(adapter);
        comboGroup.setSelection(0);
        comboGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                config.setGroupSelected(position+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void initializeProgram(final View view) {
        String pacientName = personFieldName.getText().toString();
        int timeLimitOnSeconds = Integer.parseInt(values[indexSecondsSelected]);
        File reviewsFolder = new File(Constants.FILE_PATH);
		List<String> filenames = new ArrayList<>();
		
			if(reviewsFolder.exists() &&  reviewsFolder.isDirectory()) {
				filenames = Arrays.asList(reviewsFolder.list());
			}
		
			if(!filenames.contains(pacientName+Constants.XML_EXTENSION)) {
            	if(!pacientName.isEmpty() && !getString(R.string.pacientName).equals(pacientName)){
               	 config.setPacientName(pacientName);
               	 config.setTimeLimit(timeLimitOnSeconds);
              	  config.setTotaltimeEnabled(totalTimeCheck.isChecked());
               	 config.setTimeleftEnabled(timeleftCheck.isChecked());
               	 config.setCorrectsEnabled(correctCheck.isChecked());
              	  config.setCorrectsOverTotalEnabled(correctsOverTotalCheck.isChecked());
              	  config.setFailsEnabled(failsCheck.isChecked());
              	  config.setFailsOverTotalEnabled(failsOverTotalCheck.isChecked());
				 config.setVibrateEnabled(vibrateCheck.isChecked());
                	Intent intent = new Intent(this, InstructionsActivity.class);
                  	  intent.putExtra("config", config);
                 	   intent.putExtra("instructionMessage",getString(R.string.instructions1, timeLimitOnSeconds));
             	       intent.putExtra("nextActivity", "AnagrammerActivity");
                   	 startActivity(intent);
            	} else {
                	ActivityUtils.showMessageInToast(getString(R.string.configError), getBaseContext(), getResources().getColor(R.color.yellow), null, false);
           		}
			} else {
				ActivityUtils.showMessageInToast(getString(R.string.configErrorFileRepeat), getBaseContext(), getResources().getColor(R.color.yellow), null, false);
			}
    }

	public void back(final View view) {
		Intent intent = new Intent(this, MainMenuActivity.class);
		startActivity(intent);
	}
	
}
