package com.android.example.oca_808.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.example.oca_808.QuestionsActivity;
import com.android.example.oca_808.R;
import com.android.example.oca_808.view_model.QuestionsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionButtonsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionButtonsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionButtonsFragment extends Fragment implements View.OnClickListener {


    private static final String LOG_TAG = QuestionButtonsFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private TextView mPreviousQuestion, mNextQuestion;
    private QuestionsViewModel mViewModel;
    private static ToggleButton mShowAnswerButton, mMarkButton;

    public QuestionButtonsFragment() {
        // Required empty public constructor
    }

    public static QuestionButtonsFragment newInstance() {
        return new QuestionButtonsFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question_buttons, container, false);

        if (mViewModel == null) mViewModel = QuestionsViewModel.getQVM();
        mPreviousQuestion = view.findViewById(R.id.previous_question_view);
        mNextQuestion = view.findViewById(R.id.next_question_view);
        mPreviousQuestion.setOnClickListener(this);
        mNextQuestion.setOnClickListener(this);

        // get marked state of question
        mMarkButton = view.findViewById(R.id.mark_button);
        mMarkButton.setOnClickListener(this);
        String s = mViewModel.getMarkedState(mViewModel.getmWhereWeAt());
        if (s.equals("1")) {
            mMarkButton.setChecked(true);
        } else {
            mMarkButton.setChecked(false);
        }

        // get show answer state
        mShowAnswerButton = view.findViewById(R.id.show_answer);
        mShowAnswerButton.setOnClickListener(this);
        if (QuestionsActivity.showAnswer()) {
            mShowAnswerButton.setChecked(true);
        } else {
            mShowAnswerButton.setChecked(false);
        }

        // make prev button unclickable if on question 1
        if (mViewModel.getmWhereWeAt() == 1) mPreviousQuestion.setClickable(false);

        // go to test results if on last question


        return view;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previous_question_view:
                if (mListener != null) {
                    mListener.loadPreviousQuestion();
                }
                break;
            case R.id.next_question_view:
                mListener.loadNextQuestion();
                break;
            case R.id.mark_button:
                mListener.markButtonPressed(mMarkButton.isChecked());
                break;
            case R.id.show_answer:
                mListener.showAnswerButtonPressed(mShowAnswerButton.isChecked());
                break;
            default:
                Log.e(LOG_TAG, "OnClick no match");

        }
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

        void loadNextQuestion();

        void markButtonPressed(boolean b);

        void showAnswerButtonPressed(boolean b);
    }
}
