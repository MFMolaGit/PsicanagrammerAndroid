package psicanagrammer.gevapps.com.psicanagrammer.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Map;
import java.util.Vector;

import psicanagrammer.gevapps.com.psicanagrammer.R;
import psicanagrammer.gevapps.com.psicanagrammer.dto.QuestionResults;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

/**
 * Created by Geva on 01/03/2015.
 */
public class QuestionsReviewAdapter extends BaseAdapter {

    private final Activity activity;
    private final Vector<String> list;
    private Map<String, Integer> questionsMap;

    public QuestionsReviewAdapter(final Activity activity, Vector<String> list, Map<String, Integer> questionsMap) {
        super();
        this.activity = activity;
        this.list = list;
        this.questionsMap = questionsMap;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.elementAt(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.question_review_element,null,true);
        TextView questionView = (TextView) view.findViewById(R.id.questionView);

        String questionKey = list.elementAt(position);

                 questionView.setText(questionKey);

        final TextView pointsView = (TextView) view.findViewById(R.id.actualPointsView);

        pointsView.setText(String.valueOf(questionsMap.get(questionKey)));

            if(position % 2 == 0) {
                view.setBackgroundColor(view.getResources().getColor(R.color.odd_row));
            } else {
                view.setBackgroundColor(view.getResources().getColor(R.color.even_row));
            }

        return view;
    }

}
