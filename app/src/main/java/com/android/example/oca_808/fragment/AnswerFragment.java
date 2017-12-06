package com.android.example.oca_808.fragment;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.example.oca_808.R;
import com.android.example.oca_808.view_model.QuestionsViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnswerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnswerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnswerFragment extends Fragment implements View.OnClickListener {

    private static final String LOG_TAG = AnswerFragment.class.getSimpleName();
    private RadioButton radio_a, radio_b, radio_c, radio_d, radio_e, radio_f;
    private RadioGroup radioGroup;
    private CheckBox checkbox_a, checkbox_b, checkbox_c, checkbox_d, checkbox_e, checkbox_f;
    private static QuestionsViewModel mViewModel;
    private static String mCorrectAnswers;
    private static int mQuestionType;
    private int i = 0;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String WRONG_ANSWERS = "display";
    private static final String USER_ANSWER = "user_answer";

    private ArrayList<String> mWrongAnswers;
    private String mUserAnswer;

    private OnFragmentInteractionListener mListener;


    public AnswerFragment() {
        // Required empty public constructor
    }


    public static AnswerFragment newInstance(ArrayList<String> wrongAnswers, String userAnswer) {
        AnswerFragment fragment = new AnswerFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(WRONG_ANSWERS, wrongAnswers);
        args.putString(USER_ANSWER, userAnswer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mViewModel == null) {
            mViewModel = new QuestionsViewModel(getContext());
        }

        if (getArguments() != null) {
            mWrongAnswers = getArguments().getStringArrayList(WRONG_ANSWERS);
            mUserAnswer = getArguments().getString(USER_ANSWER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_answer, container, false);
        mQuestionType = mViewModel.getCurrentQuestion().getType();
        mCorrectAnswers = mViewModel.getCurrentQuestion().answer;
        mUserAnswer = mViewModel.getUserAnswer();

        getViews(view);

        // set answers based on type of question
        if (mQuestionType == 1) {
            setRadioViewValues();
        } else {
            setCheckboxViews();
        }

        if (mUserAnswer.length() > 0) {
            displayUserAnswer();
        }

        // determines whether to show answers or not. If wrong answers is not null, then show
        if (mWrongAnswers != null) {
            showAnswers();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        displayUserAnswer();
    }

    private void showAnswers() {
        disableRadioButtons();
        disableCheckboxes();
        final int GREEN = getResources().getColor(R.color.colorGreen);
        final int RED = getResources().getColor(R.color.colorAccent);
        if (mQuestionType == 1) {
            switch (mCorrectAnswers.charAt(0)) {
                case 'a':
                    radio_a.setTextColor(GREEN);
                    break;
                case 'b':
                    radio_b.setTextColor(GREEN);
                    break;
                case 'c':
                    radio_c.setTextColor(GREEN);
                    break;
                case 'd':
                    radio_d.setTextColor(GREEN);
                    break;
                case 'e':
                    radio_e.setTextColor(GREEN);
                    break;
                case 'f':
                    radio_f.setTextColor(GREEN);
                    break;
                default:
                    Log.e(LOG_TAG, "Radio question answer-match error");
            }
            if (mWrongAnswers.size() == 1) {
                switch (mWrongAnswers.get(0)) {
                    case "a":
                        radio_a.setTextColor(RED);
                        break;
                    case "b":
                        radio_b.setTextColor(RED);
                        break;
                    case "c":
                        radio_c.setTextColor(RED);
                        break;
                    case "d":
                        radio_d.setTextColor(RED);
                        break;
                    case "e":
                        radio_e.setTextColor(RED);
                        break;
                    case "f":
                        radio_f.setTextColor(RED);
                        break;
                    default:
                        Log.e(LOG_TAG, "Radio question wrong answer-match error");

                }
            }

            displayUserAnswer();


        } else {
            for (int i = 0; i < mCorrectAnswers.length(); i++) {
                switch (mCorrectAnswers.charAt(i)) {
                    case 'a':
                        checkbox_a.setTextColor(GREEN);
                        break;
                    case 'b':
                        checkbox_b.setTextColor(GREEN);
                        break;
                    case 'c':
                        checkbox_c.setTextColor(GREEN);
                        break;
                    case 'd':
                        checkbox_d.setTextColor(GREEN);
                        break;
                    case 'e':
                        checkbox_e.setTextColor(GREEN);
                        break;
                    case 'f':
                        checkbox_f.setTextColor(GREEN);
                        break;
                    default:
                        Log.e(LOG_TAG, "Checkbox question correct-answer-match error");
                }
            }
            if (mWrongAnswers.size() != 0) {
                for (int i = 0; i < mWrongAnswers.size(); i++) {
                    switch (mWrongAnswers.get(i)) {
                        case "a":
                            checkbox_a.setTextColor(RED);
                            break;
                        case "b":
                            checkbox_b.setTextColor(RED);
                            break;
                        case "c":
                            checkbox_c.setTextColor(RED);
                            break;
                        case "d":
                            checkbox_d.setTextColor(RED);
                            break;
                        case "e":
                            checkbox_e.setTextColor(RED);
                            break;
                        case "f":
                            checkbox_f.setTextColor(RED);
                            break;
                        default:
                            Log.e(LOG_TAG, "Checkbox question wrong-answer-match error");
                    }
                }
            }
            displayUserAnswer();
        }
    }

    private void displayUserAnswer() {
        Log.i(LOG_TAG, "******** display user answer");
        if (mQuestionType == 1) {
            Log.i(LOG_TAG, "******** display user answer RADIO");
            if (mUserAnswer.length() != 0) {
                switch (mUserAnswer.charAt(0)) {
                    case 'a':
                        radio_a.setChecked(true);
                        break;
                    case 'b':
                        radio_b.setChecked(true);
                        break;
                    case 'c':
                        radio_c.setChecked(true);
                        break;
                    case 'd':
                        radio_d.setChecked(true);
                        break;
                    case 'e':
                        radio_e.setChecked(true);
                        break;
                    case 'f':
                        radio_f.setChecked(true);
                        break;
                    default:
                        Log.e(LOG_TAG, "Radio question wrong user-answer-match error");

                }

            }
        } else {
            Log.i(LOG_TAG, " ********* display user answer checkbox");
            for (int i = 0; i < mUserAnswer.length(); i++) {
                if (mUserAnswer.length() != 0) {
                    switch (mUserAnswer.charAt(i)) {
                        case 'a':
                            checkbox_a.setChecked(true);
                            break;
                        case 'b':
                            checkbox_b.setChecked(true);
                            break;
                        case 'c':
                            checkbox_c.setChecked(true);
                            break;
                        case 'd':
                            checkbox_d.setChecked(true);
                            break;
                        case 'e':
                            checkbox_e.setChecked(true);
                            break;
                        case 'f':
                            checkbox_f.setChecked(true);
                            break;
                        default:
                            Log.e(LOG_TAG, "Checkbox question user-answer-match error");
                    }
                }
            }
        }
    }

    private void disableCheckboxes() {
        checkbox_a.setEnabled(false);
        checkbox_b.setEnabled(false);
        checkbox_c.setEnabled(false);
        checkbox_d.setEnabled(false);
        checkbox_e.setEnabled(false);
        checkbox_f.setEnabled(false);
    }

    private void disableRadioButtons() {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(false);
        }
    }

    private void getViews(View view) {
        checkbox_a = view.findViewById(R.id.checkboxButton_a);
        checkbox_b = view.findViewById(R.id.checkboxButton_b);
        checkbox_c = view.findViewById(R.id.checkboxButton_c);
        checkbox_d = view.findViewById(R.id.checkboxButton_d);
        checkbox_e = view.findViewById(R.id.checkboxButton_e);
        checkbox_f = view.findViewById(R.id.checkboxButton_f);
        checkbox_a.setOnClickListener(this);
        checkbox_b.setOnClickListener(this);
        checkbox_c.setOnClickListener(this);
        checkbox_d.setOnClickListener(this);
        checkbox_e.setOnClickListener(this);
        checkbox_f.setOnClickListener(this);
        radio_a = view.findViewById(R.id.radioButton_a);
        radio_b = view.findViewById(R.id.radioButton_b);
        radio_c = view.findViewById(R.id.radioButton_c);
        radio_d = view.findViewById(R.id.radioButton_d);
        radio_e = view.findViewById(R.id.radioButton_e);
        radio_f = view.findViewById(R.id.radioButton_f);
        radioGroup = view.findViewById(R.id.radio_group);

    }

    private void setCheckboxViews() {

        radioGroup.setVisibility(View.INVISIBLE);

        checkbox_a.setText(mViewModel.getCurrentQuestion().getA());
        checkbox_b.setText(mViewModel.getCurrentQuestion().getB());
        checkbox_c.setText(mViewModel.getCurrentQuestion().getC());
        checkbox_d.setText(mViewModel.getCurrentQuestion().getD());
        checkbox_e.setText(mViewModel.getCurrentQuestion().getE());
        checkbox_f.setText(mViewModel.getCurrentQuestion().getF());
        checkbox_a.setChecked(false);
        checkbox_b.setChecked(false);
        checkbox_c.setChecked(false);
        checkbox_d.setChecked(false);
        checkbox_e.setChecked(false);
        checkbox_f.setChecked(false);
        checkbox_a.setTextColor(getResources().getColor(R.color.colorBlack));
        checkbox_b.setTextColor(getResources().getColor(R.color.colorBlack));
        checkbox_c.setTextColor(getResources().getColor(R.color.colorBlack));
        checkbox_d.setTextColor(getResources().getColor(R.color.colorBlack));
        checkbox_e.setTextColor(getResources().getColor(R.color.colorBlack));
        checkbox_f.setTextColor(getResources().getColor(R.color.colorBlack));


    }

    private void setRadioViewValues() {
        checkbox_a.setVisibility(View.INVISIBLE);
        checkbox_b.setVisibility(View.INVISIBLE);
        checkbox_c.setVisibility(View.INVISIBLE);
        checkbox_d.setVisibility(View.INVISIBLE);
        checkbox_e.setVisibility(View.INVISIBLE);
        checkbox_f.setVisibility(View.INVISIBLE);

        radio_a.setText(mViewModel.getCurrentQuestion().getA());
        radio_b.setText(mViewModel.getCurrentQuestion().getB());
        radio_c.setText(mViewModel.getCurrentQuestion().getC());
        radio_d.setText(mViewModel.getCurrentQuestion().getD());
        radio_e.setText(mViewModel.getCurrentQuestion().getE());
        radio_f.setText(mViewModel.getCurrentQuestion().getF());

        radio_a.setTextColor(getResources().getColor(R.color.colorBlack));
        radio_b.setTextColor(getResources().getColor(R.color.colorBlack));
        radio_c.setTextColor(getResources().getColor(R.color.colorBlack));
        radio_d.setTextColor(getResources().getColor(R.color.colorBlack));
        radio_e.setTextColor(getResources().getColor(R.color.colorBlack));
        radio_f.setTextColor(getResources().getColor(R.color.colorBlack));


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Character mRadioSelection = null;
                switch (i) {
                    case R.id.radioButton_a:
                        mRadioSelection = 'a';
                        break;
                    case R.id.radioButton_b:
                        mRadioSelection = 'b';
                        break;
                    case R.id.radioButton_c:
                        mRadioSelection = 'c';
                        break;
                    case R.id.radioButton_d:
                        mRadioSelection = 'd';
                        break;
                    case R.id.radioButton_e:
                        mRadioSelection = 'e';
                        break;
                    case R.id.radioButton_f:
                        mRadioSelection = 'f';
                        break;
                    default:
                        Log.e(LOG_TAG, "Radio selection match error");
                }
                mViewModel.collectUserAnswer(mRadioSelection, true);
                mListener.answerSelected(true);
            }
        });


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

        RadioButton radioButtonView;
        CheckBox checkBoxView;
        boolean vIsChecked;
        if (mQuestionType == 1) {
            radioButtonView = (RadioButton) v;
            vIsChecked = radioButtonView.isChecked();
        } else {
            checkBoxView = (CheckBox) v;
            vIsChecked = checkBoxView.isChecked();
        }

        char answer;
        switch (v.getId()) {
            case R.id.checkboxButton_a:
                answer = 'a';
                break;
            case R.id.checkboxButton_b:
                answer = 'b';
                break;
            case R.id.checkboxButton_c:
                answer = 'c';
                break;
            case R.id.checkboxButton_d:
                answer = 'd';
                break;
            case R.id.checkboxButton_e:
                answer = 'e';
                break;
            case R.id.checkboxButton_f:
                answer = 'f';
                break;
            default:
                answer = 'z';
                Log.e(LOG_TAG, "Error matching checkbox");
        }

        if (i == 0) {
            for (int j = 0; j < mUserAnswer.length(); j++) {
                char c = mUserAnswer.charAt(j);
                mViewModel.collectUserAnswer(c, true);
            }
        }
        if(answer != 'z'){
            mViewModel.collectUserAnswer(answer, vIsChecked);
        } else {
            Log.e(LOG_TAG, "ERROR matching checkbox selection");
        }
        mListener.answerSelected((mViewModel.getUserAnswer().length() > 0));

        StringBuilder sb = new StringBuilder(mUserAnswer);
        if(vIsChecked){
            sb.append(answer);
        } else {
            sb.deleteCharAt(sb.indexOf(String.valueOf(answer)));
        }
        mUserAnswer = sb.toString();

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

        void answerSelected(boolean b);
    }
}
