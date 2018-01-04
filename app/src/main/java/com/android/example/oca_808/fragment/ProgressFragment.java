package com.android.example.oca_808.fragment;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
public class ProgressFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String LOG_TAG = ProgressFragment.class.getSimpleName();
    private QuestionsViewModel mViewModel;
    private TextView mProgressQuestionNumberDisplay;

    private ImageView mDifficultyImg;
    private TextView mQuestionScore;
    private static final int TEST_SIM = 1;
    private static final int PRACTICE_TEST = 0;
    private static final String DIFF_KEY = "diff_key";
    private static final String Q_SCORE_KEY = "q_score_key";
    private SharedPreferences mPreferences;


    private OnFragmentInteractionListener mListener;


    public ProgressFragment() {
        // Required empty public constructor
    }


    public static ProgressFragment newInstance() {
        return new ProgressFragment();

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
        int mQuestionCount = mViewModel.getQuestionCount();

        mProgressQuestionNumberDisplay = view.findViewById(R.id.question_number_prog);
        TextView mQuestionCountDisplay = view.findViewById(R.id.question_count_prog);
        mProgressQuestionNumberDisplay.setText(String.valueOf(mQuestionNumber));
        mQuestionCountDisplay.setText(String.valueOf(mQuestionCount));

        mDifficultyImg = view.findViewById(R.id.difficult_icon);
        mQuestionScore = view.findViewById(R.id.q_score_status);

        if (mViewModel.getmCurrentTest().type == TEST_SIM) {
            mDifficultyImg.setVisibility(View.GONE);
            mQuestionScore.setVisibility(View.GONE);
        } else {
            SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(getContext());

            if (shPref.getBoolean(DIFF_KEY, true)) {
                mDifficultyImg.setVisibility(View.VISIBLE);
            } else {
                mDifficultyImg.setVisibility(View.INVISIBLE);
            }

            if (shPref.getBoolean(Q_SCORE_KEY, true)) {
                mQuestionScore.setVisibility(View.VISIBLE);
            } else {
                mQuestionScore.setVisibility(View.INVISIBLE);
            }
        }
        subscribe();

        return view;
    }

    private void subscribe() {

        final Observer<Integer> questionObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer qNum) {
                mProgressQuestionNumberDisplay.setText(String.valueOf(qNum));
                setDiffAndScore();
            }
        };
        mViewModel.newQuestion().observe(this, questionObserver);
    }

    private void setDiffAndScore() {

        switch (mViewModel.getCurrentQuestion().difficulty) {
            case 1:
                mDifficultyImg.setImageDrawable(getContext().getResources().getDrawable(R.drawable.easy_q_icon));
                break;
            case 2:
                mDifficultyImg.setImageDrawable(getContext().getResources().getDrawable(R.drawable.med_q_icon));
                break;
            case 3:
                mDifficultyImg.setImageDrawable(getContext().getResources().getDrawable(R.drawable.hard_q_icon));
                break;
            default:
                Log.e("ProgressFrag", "Error matching difficulty");
        }

        switch (mViewModel.getCurrentQuestion().getStatus()) {
            case -1:
                mQuestionScore.setText("-");
                mQuestionScore.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
                break;
            case 0:
                mQuestionScore.setText("0");
                mQuestionScore.setTextColor(getContext().getResources().getColor(R.color.colorGray));
                break;
            case 1:
                mQuestionScore.setText("+");
                mQuestionScore.setTextColor(getContext().getResources().getColor(R.color.colorGreen));
                break;
            case 2:
                mQuestionScore.setText("++");
                mQuestionScore.setTextColor(getContext().getResources().getColor(R.color.colorGreen));
                break;
            default:
                Log.e("ProgressFrag", "Error matching question score");
        }
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
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i(LOG_TAG, "SharedPrefChange run");
        if (mViewModel.getmCurrentTest().type == 0) {
            switch (key) {
                case DIFF_KEY:
                    Log.i(LOG_TAG, "SharedPrefChange diff run");
                    if (sharedPreferences.getBoolean(key, true)) {
                        mDifficultyImg.setVisibility(View.VISIBLE);

                    } else {
                        mDifficultyImg.setVisibility(View.INVISIBLE);
                    }
                    break;
                case Q_SCORE_KEY:
                    Log.i(LOG_TAG, "SharedPrefChange q_score run");
                    if(sharedPreferences.getBoolean(key, true)){
                        mQuestionScore.setVisibility(View.VISIBLE);
                    } else {
                        mQuestionScore.setVisibility(View.INVISIBLE);
                    }
            }
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

        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPreferences.registerOnSharedPreferenceChangeListener(this);
    }
}
