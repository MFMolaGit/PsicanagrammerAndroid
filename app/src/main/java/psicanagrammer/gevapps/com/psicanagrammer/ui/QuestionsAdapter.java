package psicanagrammer.gevapps.com.psicanagrammer.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.Arrays;
import java.util.Vector;

import psicanagrammer.gevapps.com.psicanagrammer.R;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

/**
 * Created by Geva on 01/03/2015.
 */
public class QuestionsAdapter extends BaseAdapter {

    private final Activity activity;
    private final Vector<String> list;
    private int[] progress;

    public QuestionsAdapter(final Activity activity, Vector<String> list) {
        super();
        this.activity = activity;
        this.list = list;
        progress = new int[list.size()];
        Arrays.fill(progress, Constants.MIN_POINTS);
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
        View view = inflater.inflate(R.layout.question_element,null,true);
        TextView questionView = (TextView) view.findViewById(R.id.questionView);
                 questionView.setText(list.elementAt(position));
        final TextView pointsView = (TextView) view.findViewById(R.id.actualPointsView);

        SeekBar seekPointsBar = (SeekBar) view.findViewById(R.id.seekPointsBar);
                seekPointsBar.setProgress(progress[position]);

        pointsView.setText(obtainProgress(seekPointsBar, position));

        seekPointsBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int lastProgress, boolean fromUser) {
                progress[position] = Constants.MIN_POINTS + lastProgress;
                pointsView.setText(obtainProgress(seekBar,position));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }

    private String obtainProgress(final SeekBar seekBar, final int position) {
        return Integer.toString(progress[position]) + "/" + Integer.toString(Constants.MIN_POINTS + seekBar.getMax());
    }

    public int[] getPoints() {
        return progress;
    }
}
