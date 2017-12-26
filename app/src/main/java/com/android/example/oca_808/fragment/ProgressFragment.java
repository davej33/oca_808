package com.android.example.oca_808.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.example.oca_808.R;
import com.android.example.oca_808.view_model.QuestionViewModelFactory;
import com.android.example.oca_808.view_model.QuestionsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProgressFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProgressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgressFragment extends Fragment {

    private QuestionsViewModel mViewModel;
    private TextView mProgressQuestionNumberDisplay;
//    private static TextView mTimeRemainingTV;
    private ToggleButton mMarkButton;
    private static String mTimeRemaining;




    private OnFragmentInteractionListener mListener;

    public ProgressFragment() {
        // Required empty public constructor
    }


    public static ProgressFragment newInstance() {
        return  new ProgressFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        if (mViewModel == null) {
            mViewModel = QuestionsViewModel.getQVM();
//            mViewModel = ViewModelProviders.of(this, new QuestionViewModelFactory(getActivity().getApplication())).get(QuestionsViewModel.class);
        }

        int mQuestionNumber = mViewModel.getmWhereWeAt();
        int mQuestionCount = mViewModel.getQuestionCount() - 1;

        mProgressQuestionNumberDisplay = view.findViewById(R.id.question_number_prog);
        TextView mQuestionCountDisplay = view.findViewById(R.id.question_count_prog);
        mProgressQuestionNumberDisplay.setText(String.valueOf(mQuestionNumber));
        mQuestionCountDisplay.setText(String.valueOf(mQuestionCount));

        subscribe();

        return view;
    }

    private void subscribe() {

        final Observer<Integer> questionObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer qNum) {
                mProgressQuestionNumberDisplay.setText(String.valueOf(qNum));
            }
        };
        mViewModel.newQuestion().observe(this, questionObserver);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void loadPreviousQuestion();
    }



}
