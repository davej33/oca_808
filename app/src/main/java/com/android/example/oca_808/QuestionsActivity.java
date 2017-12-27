package com.android.example.oca_808;

import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.example.oca_808.fragment.AnswerFragment;
import com.android.example.oca_808.fragment.ExplanationFragment;
import com.android.example.oca_808.fragment.ProgressFragment;
import com.android.example.oca_808.fragment.QuestionButtonsFragment;
import com.android.example.oca_808.fragment.QuestionFragment;
import com.android.example.oca_808.view_model.QuestionsViewModel;

import java.util.ArrayList;


/**
 * Created by charlotte on 11/21/17.
 */

public class QuestionsActivity extends AppCompatActivity implements QuestionFragment.OnFragmentInteractionListener,
        ProgressFragment.OnFragmentInteractionListener, AnswerFragment.OnFragmentInteractionListener,
        QuestionButtonsFragment.OnFragmentInteractionListener, ExplanationFragment.OnFragmentInteractionListener {

    private static final String LOG_TAG = QuestionsActivity.class.getSimpleName();
    private Integer mQuestionNum = 0;
    private static QuestionsViewModel mViewModel;


    private FrameLayout mExplanationContainer;
    private FrameLayout mQuestionContainer;
    private FrameLayout mQuestionForSolutionContainer;
    private ArrayList<String> mWrongAnswers;
    private TextView mTimer;
    private static boolean mShowAnswer;
    private boolean mQuestionIsMarked;

    @TargetApi(Build.VERSION_CODES.M) // TODO: Fix
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        if (mViewModel == null) {
            mViewModel = QuestionsViewModel.getQVM();
//            mViewModel = ViewModelProviders.of(this, new QuestionViewModelFactory(getActivity().getApplication())).get(QuestionsViewModel.class);
        }

        mExplanationContainer = findViewById(R.id.explanation_container);
        mQuestionContainer = findViewById(R.id.question_container);
        mQuestionForSolutionContainer = findViewById(R.id.question_solution_container);
        mTimer = findViewById(R.id.textClock);


        Toast.makeText(this, mViewModel.getTestTitle(), Toast.LENGTH_SHORT).show();
        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorBlack, null)));

        if (mViewModel.getCurrentQuestion() == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    displayQuestion();

                    mViewModel.startTimer();

                    subscribe();
                }
            }, 1000);
        } else {
            displayQuestion();

            mViewModel.startTimer();

            subscribe();
        }

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
        getSupportFragmentManager().beginTransaction().replace(R.id.buttons_container, QuestionButtonsFragment.newInstance()).commit();
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
                mQuestionNum = qNum;
                displayQuestion();
            }
        };
        mViewModel.newQuestion().observe(this, questionObserver);

        final Observer<String> timerObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                mTimer.setText(s);
            }
        };
        mViewModel.getTimeRemaining().observe(this, timerObserver);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void loadPreviousQuestion() {
        mViewModel.loadPreviousQuestion();
    }

    @Override
    public void loadNextQuestion() {
        // go to test results if on last question
        int wwa = mViewModel.getmWhereWeAt();
        int qCount = mViewModel.getQuestionCount();
        if (qCount == wwa + 1) {
            startActivity(new Intent(this, TestReviewActivity.class));
        } else {
            mViewModel.setmMarkedQuestion(mQuestionIsMarked);
            mWrongAnswers = mViewModel.checkAnswer();
            Log.i(LOG_TAG, "loadNextQuestion");
            // if showAnswer = false or the explanation view is visible then go to next question
            if (!mShowAnswer || (mExplanationContainer.getVisibility() == View.VISIBLE)) {
                mViewModel.nextQuestion();
            } else {
                displayExplanation();
            }
        }
    }

    @Override
    public void markButtonPressed(boolean b) {
        mQuestionIsMarked = b;
    }

    @Override
    public void showAnswerButtonPressed(boolean b) {
        mShowAnswer = b;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test_pref_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.end_session:
                startActivity(new Intent(this, TestReviewActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public static boolean showAnswer() {
        return mShowAnswer;
    }
}
