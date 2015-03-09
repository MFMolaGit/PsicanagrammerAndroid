package psicanagrammer.gevapps.com.psicanagrammer.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import psicanagrammer.gevapps.com.psicanagrammer.R;
import psicanagrammer.gevapps.com.psicanagrammer.dto.ConfigInit;
import psicanagrammer.gevapps.com.psicanagrammer.dto.QuestionResults;
import psicanagrammer.gevapps.com.psicanagrammer.utils.ActivityUtils;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

public class InstructionsActivity extends Activity {

    private Button startTestButton;
    private TextView intructionsTextView;
    private Class<?> nextActivity;
    private boolean isEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instruction_anagrams_activity);

        startTestButton = (Button)findViewById(R.id.startAnagramsButton);
        intructionsTextView = (TextView)findViewById(R.id.intructionsTextView);

        intructionsTextView.setText(this.getIntent().getExtras().getString("instructionMessage"));

        String sNextActivity = this.getIntent().getExtras().getString("nextActivity");

        try {
            nextActivity = Class.forName("psicanagrammer.gevapps.com.psicanagrammer.ui." + sNextActivity);
        } catch (ClassNotFoundException e) {
            System.out.println("Error al generar clase de próxima actividad");
        }

        if(getIntent().getBooleanExtra("EXIT", false)) {
            startTestButton.setText(getString(R.string.closeApp));
            isEnd = true;
        }
    }

    public void beginTest(final View view) {
         if(isEnd){
            Intent intent = new Intent(getApplicationContext(), nextActivity);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        } else if(nextActivity != null) {
            Intent intent = new Intent(this, nextActivity);
            Object objConfig = this.getIntent().getExtras().getSerializable("config");
            if(objConfig != null) {
                //Viene de actividad inicial
                intent.putExtra("config", (ConfigInit) this.getIntent().getExtras().getSerializable("config"));
                startActivity(intent);
            } else {
                //Viene de actividad Anagrammer
                startActivityForResult(intent,1);
            }
        } else {
            ActivityUtils.showMessageInToast("Se produjo un error en la aplicación. Reiniciela.", getBaseContext(), getResources().getColor(R.color.red_fail));
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK) {
            Intent intent = new Intent();
            intent.putExtra("questionsResults", (QuestionResults)data.getExtras().getSerializable("questionsResults"));
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
