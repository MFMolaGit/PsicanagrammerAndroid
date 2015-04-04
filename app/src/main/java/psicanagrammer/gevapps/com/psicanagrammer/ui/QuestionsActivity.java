package psicanagrammer.gevapps.com.psicanagrammer.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import psicanagrammer.gevapps.com.psicanagrammer.R;
import psicanagrammer.gevapps.com.psicanagrammer.dto.QuestionResults;

public class QuestionsActivity extends ListActivity {

    private QuestionResults questions;
    private QuestionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        questions = new QuestionResults();

        loadFinalQuestions();
    }

    private void loadFinalQuestions() {
        String[] questionsArray = getResources().getStringArray(R.array.questions);
        for(String question:questionsArray) {
            questions.addResponse(question, 0);
        }

        adapter = new QuestionsAdapter(this, new Vector(Arrays.asList(questionsArray)));

        setListAdapter(adapter);
    }

    public void endQuestions(final View view) {
        Map<String,Integer> pointsMap = questions.getQuestions();
        Set<String> questionsSet = pointsMap.keySet();
        Iterator<String> it = questionsSet.iterator();

        for(int point:adapter.getPoints()) {
            pointsMap.put(it.next(),point);
        }

        Intent intent = new Intent();
        intent.putExtra("questionsResults", questions);
        setResult(RESULT_OK, intent);
        finish();
    }

	@Override
	public void onBackPressed() {
		// do nothing.
	}
	
}
