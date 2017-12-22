package com.android.example.oca_808;

import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.example.oca_808.fragment.AnswerFragment;
import com.android.example.oca_808.fragment.ExplanationFragment;
import com.android.example.oca_808.fragment.ProgressFragment;
import com.android.example.oca_808.fragment.QuestionFragment;
import com.android.example.oca_808.view_model.QuestionViewModelFactory;
import com.android.example.oca_808.view_model.QuestionsViewModel;

import java.util.ArrayList;


/**
 * Created by charlotte on 11/21/17.
 */

public class QuestionsActivity extends AppCompatActivity implements QuestionFragment.OnFragmentInteractionListener,
        ProgressFragment.OnFragmentInteractionListener, AnswerFragment.OnFragmentInteractionListener, ExplanationFragment.OnFragmentInteractionListener {

    private static final String LOG_TAG = QuestionsActivity.class.getSimpleName();
    private Integer mQuestionNum = 0;
    private static QuestionsViewModel mViewModel;
    private FloatingActionButton mFAB;
    private ToggleButton mShowAnswerButton, mMarkButton;
    private FrameLayout mExplanationContainer, mQuestionContainer, mAnswerContainer, mQuestionForSolutionContainer;
    private static final String EXPLANATION_DISPLAY_TYPE = "explanation";
    private ArrayList<String> mWrongAnswers;
    private String mUserAnswer;

    @TargetApi(Build.VERSION_CODES.M) // TODO: Fix
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);


        if (mViewModel == null) {
            mViewModel = QuestionsViewModel.getQVM();
//            mViewModel = ViewModelProviders.of(this, new QuestionViewModelFactory(getActivity().getApplication())).get(QuestionsViewModel.class);
        }
        mShowAnswerButton = findViewById(R.id.show_answer);
        mExplanationContainer = findViewById(R.id.explanation_container);
        mQuestionContainer = findViewById(R.id.question_container);
        mAnswerContainer = findViewById(R.id.answer_container);
        mQuestionForSolutionContainer = findViewById(R.id.question_solution_container);

        mMarkButton = findViewById(R.id.mark_button);
        String s = mViewModel.getMarkedState(mViewModel.getmWhereWeAt());
        if(s.equals("1")){
            mMarkButton.setChecked(true);
        } else {
            mMarkButton.setChecked(false);
        }
//        mMarkButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mViewModel.setmMarkedQuestion(mMarkButton.isChecked());
//            }
//        });

        Toast.makeText(this, mViewModel.getTestTitle(), Toast.LENGTH_SHORT).show();
        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark, null)));


        if (mFAB == null) {
            mFAB = findViewById(R.id.floatingActionButton);
            mFAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mViewModel.setmMarkedQuestion(mMarkButton.isChecked());
//                    String solution = mViewModel.getCurrentQuestion().answer;
                    mWrongAnswers = mViewModel.checkAnswer();

                    // TODO store answer in Test object, create test object

                    if (!mShowAnswerButton.isChecked() || (mShowAnswerButton.isChecked() && mExplanationContainer.getVisibility() == View.VISIBLE)) {
                        mViewModel.nextQuestion();
                    } else {
                        displayExplanation();
                    }
                }
            });
        }

        displayQuestion();
        mViewModel.startTimer();

        subscribe();

    }

    private void displayQuestion() {

        // manage view visibilities
        mExplanationContainer.setVisibility(View.GONE);
        mQuestionForSolutionContainer.setVisibility(View.GONE);
        mQuestionContainer.setVisibility(View.VISIBLE);

        // set new views
        getSupportFragmentManager().beginTransaction().replace(R.id.question_container, QuestionFragment.newInstance(mQuestionNum, null)).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.answer_container, AnswerFragment.newInstance(null, null)).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.progress_container, ProgressFragment.newInstance()).commit();


        // set fab state based on user answer
        if (mViewModel.getUserAnswer() == null || mViewModel.getUserAnswer().equals("")) {
            mFAB.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGray)));
            mFAB.setImageResource(android.R.drawable.ic_media_next);
        } else {
            mFAB.setImageResource(android.R.drawable.checkbox_on_background);
            mFAB.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGreen)));
        }
    }


    private void displayExplanation() {

        // manage view visibilities
        mQuestionContainer.setVisibility(View.GONE);
        mExplanationContainer.setVisibility(View.VISIBLE);
        mQuestionForSolutionContainer.setVisibility(View.VISIBLE);

        // set new views
        getSupportFragmentManager().beginTransaction().replace(R.id.answer_container, AnswerFragment.newInstance(mWrongAnswers, null)).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.question_solution_container, QuestionFragment.newInstance(mQuestionNum, null)).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.explanation_container, ExplanationFragment.newInstance(null, null)).commit();
    }

    private void subscribe() {

        final Observer<Integer> questionObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer qNum) {
                Log.i(LOG_TAG,"check onQuestionChange");
                mQuestionNum = qNum;
                displayQuestion();
            }
        };
        mViewModel.newQuestion().observe(this, questionObserver);

        final Observer<String> timerObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.i(LOG_TAG,"check onTimerChange");
                ProgressFragment.setTimeRemainingFrag(s);
            }
        };
        mViewModel.getTimeRemaining().observe(this, timerObserver);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void answerSelected(boolean b) {
        if (b) {
            mFAB.setImageResource(android.R.drawable.checkbox_on_background);
        } else {
            mFAB.setImageResource(android.R.drawable.ic_media_next);
        }
    }

    @Override
    public void loadPreviousQuestion() {
        mViewModel.loadPreviousQuestion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test_pref_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.end_session:
                startActivity(new Intent(this, TestReviewActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
