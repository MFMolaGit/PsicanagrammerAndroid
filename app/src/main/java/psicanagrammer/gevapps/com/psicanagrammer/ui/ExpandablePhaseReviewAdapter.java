package psicanagrammer.gevapps.com.psicanagrammer.ui;

import java.util.List;
import java.util.Map;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import psicanagrammer.gevapps.com.psicanagrammer.R;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Phase;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Record;
import psicanagrammer.gevapps.com.psicanagrammer.dto.State;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

/**
 * Created by Geva on 20/03/2015.
 */
public class ExpandablePhaseReviewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private Map<String, List<Phase>> listDataChild;
    private TextView phaseName, phaseTime,
            correctsOverResponses, failsOverResponses,
            timeoutsOverResponses,correctsOverWords, timeoutsOverWords,
            latencyCorrects,latencyGeneral,timesCriterial,timeCriterial,
            ratioCriterial,registersExpander;
    private TableRow tableContainer;
    private TableLayout registersTable;

    public ExpandablePhaseReviewAdapter(Context context, List<String> listDataHeader,
                                        Map<String, List<Phase>> listDataChild) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listDataChild;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Phase phaseSelected = (Phase) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.phases_review_item, null);
        }

        phaseName = (TextView)convertView.findViewById(R.id.phaseName);
        phaseTime = (TextView)convertView.findViewById(R.id.phaseTime);
        correctsOverResponses = (TextView)convertView.findViewById(R.id.correctsOverResponses);
        failsOverResponses = (TextView)convertView.findViewById(R.id.failsOverResponses);
        timeoutsOverResponses = (TextView)convertView.findViewById(R.id.timeoutsOverResponses);
        correctsOverWords = (TextView)convertView.findViewById(R.id.correctsOverWords);
        timeoutsOverWords = (TextView)convertView.findViewById(R.id.timeoutsOverWords);
        latencyCorrects = (TextView)convertView.findViewById(R.id.latencyCorrects);
        latencyGeneral = (TextView)convertView.findViewById(R.id.latencyGeneral);
        timesCriterial = (TextView)convertView.findViewById(R.id.timesCriterial);
        timeCriterial = (TextView)convertView.findViewById(R.id.timeCriterial);
        ratioCriterial = (TextView)convertView.findViewById(R.id.ratioCriterial);
        registersExpander = (TextView)convertView.findViewById(R.id.reviewRegistersSubtitle);
        tableContainer = (TableRow) convertView.findViewById(R.id.registersTableRowContainer);
        registersTable = (TableLayout) convertView.findViewById(R.id.registersTable);

        final View finalConvertView = convertView;
        registersExpander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int actualTableVisibility = tableContainer.getVisibility();
                if(actualTableVisibility == View.GONE) {
                    tableContainer.setVisibility(View.VISIBLE);
                } else {
                    tableContainer.setVisibility(View.GONE);
                }
            }
        });
        
        loadPhase(phaseSelected);

        setColor(groupPosition, convertView);

        return convertView;
    }

    private void setColor(final int position, View view) {
        if(position % 2 == 0) {
            view.setBackgroundColor(view.getResources().getColor(R.color.odd_row));
        } else {
            view.setBackgroundColor(view.getResources().getColor(R.color.even_row));
        }
    }

    private void setColor(final int position, TableRow row, final State state) {
        if(state == State.TIMEOUT) {
                row.setBackgroundColor(row.getResources().getColor(R.color.yellow_odd_row_timeout));
        } else if(state == State.OK) {
               row.setBackgroundColor(row.getResources().getColor(R.color.green_odd_row_correct));
        } else if(state == State.KO) {
                row.setBackgroundColor(row.getResources().getColor(R.color.red_odd_row_incorrect));
        }
    }

    private void loadPhase(final Phase phaseSelected) {

            phaseName.setText(phaseSelected.getName());
            phaseTime.setText(String.valueOf(phaseSelected.getSeconds()));
            correctsOverResponses.setText(phaseSelected.getCorrectsCount()
                    + "/" + phaseSelected.getTotal() + " (" + phaseSelected.getOkPercent() + "%)");
            failsOverResponses.setText(phaseSelected.getFailsCount()
                    + "/" + phaseSelected.getTotal() + " (" + phaseSelected.getKoPercent() + "%)");
            timeoutsOverResponses.setText(phaseSelected.getTimeoutsCount()
                    + "/" + phaseSelected.getTotal() + " (" + phaseSelected.getToPercent() + "%)");
            correctsOverWords.setText(phaseSelected.getCorrectsCount()
                    + "/" + phaseSelected.getTotalLoaded() + " (" + phaseSelected.getOk2Percent() + "%)");
            timeoutsOverWords.setText(phaseSelected.getTimeoutsCount()
                    + "/" + phaseSelected.getTotalLoaded() + " (" + phaseSelected.getTo2Percent() + "%)");
            latencyCorrects.setText(String.valueOf(phaseSelected.getCorrectResponseLatency()));
            latencyGeneral.setText(String.valueOf(phaseSelected.getResponseLatency()));
            timesCriterial.setText(String.valueOf(Constants.CORRECT_TIMES_CRITERIAL));
            timeCriterial.setText(String.valueOf(Constants.TIMERATE_CRITERIAL));
            ratioCriterial.setText(String.valueOf(phaseSelected.getCriterialTraining()));

            loadRegistersTable(phaseSelected.getRegisterList());

    }

    private void loadRegistersTable(final List<Record> recordsOnPhase) {

        registersTable.removeViews(1, registersTable.getChildCount()-1);

        int position = 0;
        for(Record record:recordsOnPhase) {
            TableRow newRow = new TableRow(context);
                     newRow.setGravity(Gravity.CENTER);

            TableRow.LayoutParams baseParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
            
            TextView timestampView = new TextView(context);
                timestampView.setText(Constants.SIMPLE_HOUR_FORMAT.format(record.getTimestamp()));
                timestampView.setLayoutParams(baseParams);
                timestampView.setGravity(Gravity.CENTER);
                newRow.addView(timestampView);
            TextView anagramView = new TextView(context);
                anagramView.setText(record.getAnagram());
                anagramView.setLayoutParams(baseParams);
                anagramView.setGravity(Gravity.CENTER);
                newRow.addView(anagramView);
            TextView solutionView = new TextView(context);
                solutionView.setText(record.getSolution());
                solutionView.setLayoutParams(baseParams);
                solutionView.setGravity(Gravity.CENTER);
                newRow.addView(solutionView);
            TextView responseView = new TextView(context);
                responseView.setText(record.getResponse());
                responseView.setLayoutParams(baseParams);
                responseView.setGravity(Gravity.CENTER);
                newRow.addView(responseView);
            TextView secondsView = new TextView(context);
                secondsView.setText(String.valueOf(record.getSeconds()));
                secondsView.setLayoutParams(baseParams);
                secondsView.setGravity(Gravity.CENTER);
                newRow.addView(secondsView);
/*            TextView stateView = new TextView(context);
                stateView.setText(record.getState().value());
                newRow.addView(stateView);*/

            setColor(position++, newRow, record.getState());

            registersTable.addView(newRow);
        }

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.phases_header_review_tab, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.phaseHeaderLabel);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
