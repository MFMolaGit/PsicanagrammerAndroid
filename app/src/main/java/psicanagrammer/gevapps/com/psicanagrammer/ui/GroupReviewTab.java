package psicanagrammer.gevapps.com.psicanagrammer.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import psicanagrammer.gevapps.com.psicanagrammer.R;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Report;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

public class GroupReviewTab extends Fragment {

    private static final String REPORT_PARAM = "report";
    private Report report;
    private TextView reportName,reportDate,groupName,groupTime,
            correctsOverResponses, failsOverResponses,
            timeoutsOverResponses,correctsOverWords, timeoutsOverWords,
        latencyCorrects,latencyGeneral,timesCriterial,timeCriterial,
        ratioCriterial;

//    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment GroupReviewTab.
     */
    public static GroupReviewTab newInstance(Report param1) {
        GroupReviewTab fragment = new GroupReviewTab();
        Bundle args = new Bundle();
            args.putSerializable(REPORT_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public GroupReviewTab() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            report = (Report)getArguments().getSerializable(REPORT_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.group_review_tab, container, false);
        
        reportName = (TextView)view.findViewById(R.id.reportName);
        reportDate = (TextView)view.findViewById(R.id.reportDate);
        groupName = (TextView)view.findViewById(R.id.groupName);
        groupTime = (TextView)view.findViewById(R.id.groupTime);
        correctsOverResponses = (TextView)view.findViewById(R.id.correctsOverResponses);
        failsOverResponses = (TextView)view.findViewById(R.id.failsOverResponses);
        timeoutsOverResponses = (TextView)view.findViewById(R.id.timeoutsOverResponses);
        correctsOverWords = (TextView)view.findViewById(R.id.correctsOverWords);
        timeoutsOverWords = (TextView)view.findViewById(R.id.timeoutsOverWords);
        latencyCorrects = (TextView)view.findViewById(R.id.latencyCorrects);
        latencyGeneral = (TextView)view.findViewById(R.id.latencyGeneral);
        timesCriterial = (TextView)view.findViewById(R.id.timesCriterial);
        timeCriterial = (TextView)view.findViewById(R.id.timeCriterial);
        ratioCriterial = (TextView)view.findViewById(R.id.ratioCriterial);

        if (report != null) {
            reportName.setText(report.getFileReportName());
            reportDate.setText(Constants.SIMPLE_DATE_FORMAT.format(report.getTimestamp()));
            groupName.setText(report.getGroup().getName());
            groupTime.setText(String.valueOf(report.getGroup().getSeconds()));
            correctsOverResponses.setText(report.getGroup().getCorrectsCount()
                    + "/" + report.getGroup().getTotal() + " (" + report.getGroup().getOkPercent() + "%)");
            failsOverResponses.setText(report.getGroup().getFailsCount()
                    + "/" + report.getGroup().getTotal() + " (" + report.getGroup().getKoPercent() + "%)");
            timeoutsOverResponses.setText(report.getGroup().getTimeoutsCount()
                    + "/" + report.getGroup().getTotal() + " (" + report.getGroup().getToPercent() + "%)");
            correctsOverWords.setText(report.getGroup().getCorrectsCount()
                    + "/" + report.getGroup().getTotalLoaded() + " (" + report.getGroup().getOk2Percent() + "%)");
            timeoutsOverWords.setText(report.getGroup().getTimeoutsCount()
                    + "/" + report.getGroup().getTotalLoaded() + " (" + report.getGroup().getTo2Percent() + "%)");
            latencyCorrects.setText(String.valueOf(report.getGroup().getCorrectResponseLatency()));
            latencyGeneral.setText(String.valueOf(report.getGroup().getResponseLatency()));
            timesCriterial.setText(String.valueOf(Constants.CORRECT_TIMES_CRITERIAL));
            timeCriterial.setText(String.valueOf(Constants.TIMERATE_CRITERIAL));
            ratioCriterial.setText(String.valueOf(report.getGroup().getGeneralCriterialTraining()));
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }*/

}
