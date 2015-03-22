package psicanagrammer.gevapps.com.psicanagrammer.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import psicanagrammer.gevapps.com.psicanagrammer.R;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Group;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Phase;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

public class PhasesReviewTab extends Fragment {

    private static final String GROUP_PARAM = "group";
    private Group group;
    private ExpandableListView expListView;
    private ExpandablePhaseReviewAdapter listAdapter;
    private List<String> headerPhaseList;
    private Map<String,List<Phase>> itemPhaseMap;

//    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment GroupReviewTab.
     */
    public static PhasesReviewTab newInstance(Group param1) {
        PhasesReviewTab fragment = new PhasesReviewTab();
        Bundle args = new Bundle();
            args.putSerializable(GROUP_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public PhasesReviewTab() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        headerPhaseList = new ArrayList<>();
        itemPhaseMap = new HashMap<>();

        if (getArguments() != null) {
            group = (Group)getArguments().getSerializable(GROUP_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.phases_review_tab, container, false);

        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.phaseExpandableLabel);
        // preparing list data
        prepareListData();
        listAdapter = new ExpandablePhaseReviewAdapter(view.getContext(), headerPhaseList, itemPhaseMap);
        // setting list adapter
        expListView.setAdapter(listAdapter);

        return view;
    }

    private void prepareListData() {
        if (group != null) {
            List<Phase> phases = group.getRegisterList();
            for(Phase phase:phases) {
                String phaseName = phase.getName();

                if(headerPhaseList.size() < phases.size()) {
                   String traducedPhaseName = Constants.phaseNameMap.get(phaseName);
                   String phaseNameKey = traducedPhaseName != null?traducedPhaseName:phaseName;
                        headerPhaseList.add(phaseNameKey);
                        itemPhaseMap.put(phaseNameKey, Arrays.asList(phase));
                }
            }
        }
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
