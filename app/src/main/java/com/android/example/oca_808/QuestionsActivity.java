package com.android.example.oca_808;

import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.example.oca_808.fragment.AnswerFragment;
import com.android.example.oca_808.fragment.ExplanationFragment;
import com.android.example.oca_808.fragment.ProgressFragment;
import com.android.example.oca_808.fragment.QuestionFragment;
import com.android.example.oca_808.view_model.QuestionsViewModel;

import java.util.ArrayList;


/**
 * Created by charlotte on 11/21/17.
 */

public class QuestionsActivity extends AppCompatActivity implements QuestionFragment.OnFragmentInteractionListener,
        ProgressFragment.OnFragmentInteractionListener, AnswerFragment.OnFragmentInteractionListener, ExplanationFragment.OnFragmentInteractionListener {

    private static final String LOG_TAG = QuestionsActivity.class.getSimpleName();
    private Integer questionNum = 0;
    private static QuestionsViewModel mViewModel;
    private FloatingActionButton mFAB;
    private ToggleButton mShowAnswerButton;
    private FrameLayout mExplanationContainer, mQuestionContainer, mAnswerContainer, mQuestionForSolutionContainer;
    private static final String EXPLANATION_DISPLAY_TYPE = "explanation";
    private ArrayList<String> mWrongAnswers;
    private String mUserAnswer;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);



        mViewModel = new QuestionsViewModel(getApplicationContext());
        mShowAnswerButton = findViewById(R.id.show_answer);
        mExplanationContainer = findViewById(R.id.explanation_container);
        mQuestionContainer = findViewById(R.id.question_container);
        mAnswerContainer = findViewById(R.id.answer_container);
        mQuestionForSolutionContainer = findViewById(R.id.question_solution_container);



        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark, null)));



        if (mFAB == null) {
            Log.w(LOG_TAG, "new FAB");
            mFAB = findViewById(R.id.floatingActionButton);
            mFAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mUserAnswer = AnswerFragment.getUserAnswer();
                    if (mUserAnswer.length() == 0) {
                        mUserAnswer = "skipped";
                        mViewModel.checkAnswer(mUserAnswer);
                        Toast.makeText(QuestionsActivity.this, "Question Skipped", Toast.LENGTH_SHORT).show();
                    } else {
                        String solution = mViewModel.getCurrentQuestion().answer;
                        mWrongAnswers = mViewModel.checkAnswer(mUserAnswer);
                        if (mWrongAnswers.size() == 0) {
                            Toast.makeText(QuestionsActivity.this, "Correct!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(QuestionsActivity.this, "Not so much. \nuser answer: " + mUserAnswer + "\nsolution: " + solution, Toast.LENGTH_LONG).show();
                        }

                        // TODO store answer in Test object, create test object
                    }
                    if (!mShowAnswerButton.isChecked() || (mShowAnswerButton.isChecked() && mExplanationContainer.getVisibility() == View.VISIBLE)) {
                        mViewModel.nextQuestion();
                    } else {
                        displayExplanation();
                    }
                }
            });
        }

        displayQuestion();

        getSupportFragmentManager().beginTransaction().replace(R.id.progress_container, ProgressFragment.newInstance(null, null)).commit();

        subscribe();

    }

    private void displayQuestion() {

        // manage view visibilities
        mExplanationContainer.setVisibility(View.GONE);
        mQuestionForSolutionContainer.setVisibility(View.GONE);
        mQuestionContainer.setVisibility(View.VISIBLE);

        // set new views
        getSupportFragmentManager().beginTransaction().replace(R.id.question_container, QuestionFragment.newInstance(questionNum, null)).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.answer_container, AnswerFragment.newInstance(null, null)).commit();

        mFAB.setImageResource(android.R.drawable.ic_media_next);
    }


    private void displayExplanation() {

        // manage view visibilities
        mQuestionContainer.setVisibility(View.GONE);
        mExplanationContainer.setVisibility(View.VISIBLE);
        mQuestionForSolutionContainer.setVisibility(View.VISIBLE);

        // set new views
        getSupportFragmentManager().beginTransaction().replace(R.id.answer_container, AnswerFragment.newInstance(mWrongAnswers, mUserAnswer)).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.question_solution_container, QuestionFragment.newInstance(questionNum, null)).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.explanation_container, ExplanationFragment.newInstance(null, null)).commit();
    }

    private void subscribe() {

        final Observer<Integer> questionObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer qNum) {
                questionNum = qNum;
                displayQuestion();
            }
        };
        mViewModel.newQuestion().observe(this, questionObserver);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void answerSelected(boolean b) {
        Log.w(LOG_TAG, "answer selected run");
        if (b) {
            mFAB.setImageResource(android.R.drawable.checkbox_on_background);
        } else {
            mFAB.setImageResource(android.R.drawable.ic_media_next);
        }
    }
}
