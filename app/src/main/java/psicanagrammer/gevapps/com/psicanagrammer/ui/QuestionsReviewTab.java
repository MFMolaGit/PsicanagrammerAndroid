package psicanagrammer.gevapps.com.psicanagrammer.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.Set;
import java.util.Vector;
import psicanagrammer.gevapps.com.psicanagrammer.R;
import psicanagrammer.gevapps.com.psicanagrammer.dto.QuestionResults;

public class QuestionsReviewTab extends ListFragment {

    private static final String QUESTIONS_PARAM = "questions";
    private QuestionResults questionResults;
    private QuestionsReviewAdapter questionsAdapter;
    private boolean finalQuestionsLoaded;

//    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment GroupReviewTab.
     */
    public static QuestionsReviewTab newInstance(QuestionResults param1) {
        QuestionsReviewTab fragment = new QuestionsReviewTab();
        Bundle args = new Bundle();
            args.putSerializable(QUESTIONS_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public QuestionsReviewTab() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            questionResults = (QuestionResults)getArguments().getSerializable(QUESTIONS_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.questions_review_tab, container, false);

            loadFinalQuestions(view);

        return view;
    }

        private void loadFinalQuestions(View view) {

        if (!finalQuestionsLoaded) {
            Set<String> questionsSet = questionResults.getQuestions().keySet();

            questionsAdapter = new QuestionsReviewAdapter(getActivity(), new Vector(questionsSet), questionResults.getQuestions());
            setListAdapter(questionsAdapter);
            finalQuestionsLoaded = true;
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
