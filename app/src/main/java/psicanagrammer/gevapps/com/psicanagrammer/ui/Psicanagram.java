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

import psicanagrammer.gevapps.com.psicanagrammer.R;
import psicanagrammer.gevapps.com.psicanagrammer.dto.ConfigInit;
import psicanagrammer.gevapps.com.psicanagrammer.utils.ActivityUtils;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

public class Psicanagram extends Activity {

    private Spinner comboGroup;
    private EditText personFieldName;
    private NumberPicker timeLimit;
    private CheckBox totalTimeCheck,timeleftCheck;
    private CheckBox correctCheck,correctsOverTotalCheck,failsCheck,failsOverTotalCheck;
    private ConfigInit config;
    private int indexSecondsSelected;
    String[] values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getBooleanExtra("EXIT",false)) {
            finish();
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

            if(!pacientName.isEmpty() && !getString(R.string.pacientName).equals(pacientName)){
                config.setPacientName(pacientName);
                config.setTimeLimit(timeLimitOnSeconds);
                config.setTotaltimeEnabled(totalTimeCheck.isChecked());
                config.setTimeleftEnabled(timeleftCheck.isChecked());
                config.setCorrectsEnabled(correctCheck.isChecked());
                config.setCorrectsOverTotalEnabled(correctsOverTotalCheck.isChecked());
                config.setFailsEnabled(failsCheck.isChecked());
                config.setFailsOverTotalEnabled(failsOverTotalCheck.isChecked());

                Intent intent = new Intent(this, InstructionsActivity.class);
                    intent.putExtra("config", config);
                    intent.putExtra("instructionMessage",getString(R.string.instructions1, timeLimitOnSeconds));
                    intent.putExtra("nextActivity", "AnagrammerActivity");
                    startActivity(intent);
            } else {
                ActivityUtils.showMessageInToast(getString(R.string.configError), getBaseContext(), getResources().getColor(R.color.yellow));
            }
    }

	public void back(final View view) {
		Intent intent = new Intent(this, MainMenuActivity.class);
		startActivity(intent);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initial_config, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
