package psicanagrammer.gevapps.com.psicanagrammer.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import java.util.Date;

import psicanagrammer.gevapps.com.psicanagrammer.R;
import psicanagrammer.gevapps.com.psicanagrammer.dto.ConfigInit;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Group;
import psicanagrammer.gevapps.com.psicanagrammer.dto.QuestionResults;
import psicanagrammer.gevapps.com.psicanagrammer.dto.State;
import psicanagrammer.gevapps.com.psicanagrammer.engine.Loader;
import psicanagrammer.gevapps.com.psicanagrammer.engine.ReportGenerator;
import psicanagrammer.gevapps.com.psicanagrammer.utils.ActivityUtils;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

public class AnagrammerActivity extends Activity {

    private Chronometer chrono;
    private TextView infoView, anagram;
    private TextView timesView, timeoutsView, failsView, correctsView, groupCountView, phaseCountView, anagramCountView, letterCountView, timeLeftView;
    private Button[] letterButtons;
    private int times, timeouts, fails, corrects, lettersPushed, timeLeft;
    private ConfigInit config;
    private boolean initied;
    private Loader loader;
    private ReportGenerator reportGenerator;

    private StringBuilder actualResponse;
    private int timeLimit;
    private Date timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anagrammer);

        chrono = (Chronometer) findViewById(R.id.chronometer);
        infoView = (TextView) findViewById(R.id.infoView);
        anagram = (TextView) findViewById(R.id.anagram);
        timesView = (TextView) findViewById(R.id.timesView);
        timeoutsView = (TextView) findViewById(R.id.timeoutsView);
        failsView = (TextView) findViewById(R.id.failsView);
        correctsView = (TextView) findViewById(R.id.correctsView);
        groupCountView = (TextView) findViewById(R.id.groupCountView);
        phaseCountView = (TextView) findViewById(R.id.phaseCountView);
        anagramCountView = (TextView) findViewById(R.id.anagramCountView);
        letterCountView = (TextView) findViewById(R.id.letterCountView);
        timeLeftView = (TextView) findViewById(R.id.timeLeftView);
        config = (ConfigInit)this.getIntent().getExtras().getSerializable("config");

        timeLimit = config.getTimeLimit();

        performVisibility();

        final String resStringId = "id";
        StringBuilder buttonId = new StringBuilder("letter");

        letterButtons = new Button[Constants.CHARACTERS_LIMIT];

        for(int i = 1; i <= Constants.CHARACTERS_LIMIT; i++) {
            buttonId.append(i);
            int id = ActivityUtils.getIdentifier(this, resStringId, buttonId.toString());
            letterButtons[i-1] = (Button) findViewById(id);
            buttonId.deleteCharAt(buttonId.length()-1);
        }

        loader = new Loader(this,config.getGroupSelected());
        reportGenerator = new ReportGenerator(loader.getActualGroup(), config.getPacientName());
        infoView.setText(loader.getActualAnagram());
        loadButtons();


        times = timeouts = fails = corrects = lettersPushed = 0;
        timeLeft = timeLimit;
        initied = true;
        actualResponse = new StringBuilder();

        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

            @Override
            public void onChronometerTick(Chronometer chronometer) {
                String time = chronometer.getText().toString();
                String[] minSec = time.split(":");
                int seconds = Integer.parseInt(minSec[1]);

                groupCountView.setText(Integer.toString(loader.getActualCounterGroup()));
                phaseCountView.setText(Integer.toString(loader.getActualCounterPhase()));
                anagramCountView.setText(Integer.toString(loader.getActualCounterAnagram()));

                timeLeftView.setTextColor(decideColor(timeLeft));
                timeLeftView.setText(Integer.toString(timeLeft--));

                if (timeLeft == 0 && !initied) {
                    ActivityUtils.showMessageInToast("¡Se acabó el tiempo!", getBaseContext(), getResources().getColor(R.color.yellow), null, false);
                    timeLeft = timeLimit;
                    timestamp = new Date();
                    generateRecord(State.TIMEOUT);
                    String next = loader.nextAnagram();
                    System.out.println("*************** Nuevo anagrama "+next+" - por TIMEOUT ******************");

                    if(next != null) {
                        infoView.setText(next);
                        System.out.println("*************** Solucion anagrama "+next+" actual:"+loader.getSolution()+" por TIMEOUT ******************");
                        timesView.setText(Integer.toString(++times));

                        StringBuilder timeout = new StringBuilder(Integer.toString(++timeouts));
                            if(config.isFailsOverTotalEnabled()) {
                                timeout.append("/").append(calculateTotalAnagrams());
                            }

                        timeoutsView.setText(timeout);
                        loadButtons();
                    } else {
                        goToInstructions2();
                    }
                }

                initied = false;
            }
        });

        chrono.start();
    }

    private int decideColor(final int timeleft) {
        Double min = Double.valueOf(0.2*config.getTimeLimit());
        Double med = Double.valueOf(0.5*config.getTimeLimit());
        int color;

        if(timeleft <= min) {
            color = getResources().getColor(R.color.red);
        } else if(timeleft > min && timeleft <= med ) {
            color = getResources().getColor(R.color.yellow);
        } else {
            color = getResources().getColor(R.color.green);
        }

        return color;
    }

    private int calculateTotalAnagrams() {
        Group actualGroup = loader.getActualGroup();
        int totalAnagramsOnGroup = 0;

            for(int phaseIndex = 0; phaseIndex < actualGroup.getSizePhasesByGroup(); phaseIndex++) {
                totalAnagramsOnGroup += actualGroup.getSizeAnagramsByPhase(phaseIndex);
            }

        return totalAnagramsOnGroup;
    }

    private void performVisibility() {
        if(!config.isTotaltimeEnabled()) {
            chrono.setVisibility(View.INVISIBLE);
        }

        if(!config.isTimeleftEnabled()) {
            timeLeftView.setVisibility(View.INVISIBLE);
        }

        if(!config.isCorrectsEnabled() && !config.isCorrectsOverTotalEnabled()) {
            correctsView.setVisibility(View.INVISIBLE);
        }

        if(!config.isFailsEnabled() && !config.isFailsOverTotalEnabled()) {
            timeoutsView.setVisibility(View.INVISIBLE);
        }
    }

    private void generateRecord(final State response) {
        int deltaTime;

        if(response.equals(State.TIMEOUT))
            deltaTime = timeLimit;
        else {
            deltaTime = timeLimit - timeLeft;
            if (response.equals(State.OK))
                timeLeft = timeLimit;
        }

        if(!loader.isEndAnagrams() && !loader.isEndPhases()) {
            reportGenerator.createRecord(loader.getActualCounterPhase(),
                    loader.getActualCounterAnagram()-1, actualResponse.toString(), deltaTime, timestamp, response);
        }
    }

    private void goToInstructions2() {
        chrono.stop();

        Intent intent = new Intent(this, InstructionsActivity.class);
        intent.putExtra("instructionMessage",getString(R.string.instructions2));
        intent.putExtra("nextActivity", "QuestionsActivity");
        startActivityForResult(intent, 1);
    }

    private void goToEnd() {
        Intent intent = new Intent(this, InstructionsActivity.class);
        intent.putExtra("instructionMessage",getString(R.string.endMessage));
        intent.putExtra("nextActivity", "Psicanagram");
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK) {
            QuestionResults responses = (QuestionResults)data.getExtras().getSerializable("questionsResults");
            reportGenerator.setResultQuestions(responses);
            generateFile();
        }
    }

    private void generateFile() {

        try {
            System.out.println("*************** Generando fichero de informe ******************");
            reportGenerator.generateReport();
            System.out.println("*************** Generando fichero de informe - OK ******************");
        } catch (Exception e) {
            System.out.println("*************** Generando fichero de informe - KO ******************");
            ActivityUtils.showMessageInToast(e.getMessage(), getBaseContext(), getResources().getColor(R.color.red_fail), null, false);
        }

        goToEnd();
    }

    public void addLetter(View view) {

        boolean responseOk = false;

        if(lettersPushed <= Constants.CHARACTERS_LIMIT) {
            Button letterPressed = (Button) view;
            actualResponse.append(letterPressed.getText());
            anagram.setText(actualResponse.toString());
            letterPressed.setEnabled(false);
            lettersPushed++;
            letterCountView.setText(Integer.toString(lettersPushed));
        }

        if(lettersPushed == Constants.CHARACTERS_LIMIT) {

            String solution = loader.getSolution();
            timestamp = new Date();

            if(actualResponse.toString().equals(solution)) {
                ActivityUtils.showMessageInToast("¡Has respondido correctamente!", getBaseContext(), getResources().getColor(R.color.green_correct), null, false);
                timesView.setText(Integer.toString(++times));

                StringBuilder correct = new StringBuilder(Integer.toString(++corrects));
                if(config.isCorrectsOverTotalEnabled()) {
                    correct.append("/").append(calculateTotalAnagrams());
                }

                correctsView.setText(correct);
                responseOk = true;
            } else {
                ActivityUtils.showMessageInToast("No es al palabra buscada. Sigue intentádolo.", getBaseContext(), getResources().getColor(R.color.red_fail), null,false);
                failsView.setText(Integer.toString(++fails));
            }

            generateRecord(responseOk?State.OK:State.KO);

            lettersPushed = 0;

            if(actualResponse.length() > 0)
                actualResponse.setLength(0);

            delayBeforeNext(2, responseOk);
        }
    }

    private void delayBeforeNext(final int seconds, final boolean responseOk) {
        String text;

        if(responseOk){
            text = loader.nextAnagram();
            infoView.setText(text);
            System.out.println("*************** Nuevo anagrama "+text+"  - por OK ******************");
        } else {
            text = actualResponse.toString();
            System.out.println("*************** Mismo anagrama "+text+"  - por KO ******************");
        }

        if(text != null) {
            System.out.println("*************** Solucion anagrama "+text+" actual:"+loader.getSolution()+" por OK/KO ******************");
            loadButtons();

        } else {
            goToInstructions2();
        }
    }

    private void loadButtons() {
        for(int i = 1; i <= Constants.CHARACTERS_LIMIT; i++) {
            Button letterButton = letterButtons[i - 1];
            letterButton.setText(loader.getLetterActualAnagram(i));
            letterButton.setEnabled(true);
        }

        lettersPushed = 0;
        if(actualResponse != null && actualResponse.length() > 0)
            actualResponse.setLength(0);
    }

}
