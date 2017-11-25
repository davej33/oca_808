package com.android.example.oca_808;

import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.example.oca_808.fragment.AnswerFragment;
import com.android.example.oca_808.fragment.ProgressFragment;
import com.android.example.oca_808.fragment.QuestionFragment;
import com.android.example.oca_808.view_model.QuestionsViewModel;


/**
 * Created by charlotte on 11/21/17.
 */

public class QuestionsActivity extends AppCompatActivity implements QuestionFragment.OnFragmentInteractionListener,
        ProgressFragment.OnFragmentInteractionListener, AnswerFragment.OnFragmentInteractionListener {

    private static final String LOG_TAG = QuestionsActivity.class.getSimpleName();
    private Integer questionNum = 0;
    private static QuestionsViewModel mViewModel;
    private FloatingActionButton mFAB;


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        mViewModel = new QuestionsViewModel(getApplicationContext());
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark, null)));

        if (mFAB == null) {
            Log.w(LOG_TAG, "new FAB");
            mFAB = findViewById(R.id.floatingActionButton);
            mFAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userAnswer = AnswerFragment.getUserAnswer();
                    if (userAnswer.length() == 0) {
                        userAnswer = "skipped";
                        mViewModel.checkAnswer(userAnswer);
                        Toast.makeText(QuestionsActivity.this, "Question Skipped", Toast.LENGTH_SHORT).show();
                    } else {
                        String solution = mViewModel.getCurrentQuestion().answer;
                        boolean correctAnswer = mViewModel.checkAnswer(userAnswer);
                        if (correctAnswer) {
                            Toast.makeText(QuestionsActivity.this, "Correct! \nuser answer: " + userAnswer + "\n solution: " + solution, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(QuestionsActivity.this, "Not so much. \nuser answer: " + userAnswer + "\nsolution: " + solution, Toast.LENGTH_LONG).show();
                        }

                        // TODO store answer in Test object, create test object
                    }
                    mViewModel.nextQuestion();
                }
            });
        }
        displayContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.progress_container, ProgressFragment.newInstance(null, null)).commit();
        subscribe();
    }

    private void displayContent() {
        getSupportFragmentManager().beginTransaction().replace(R.id.question_container, QuestionFragment.newInstance(questionNum, null)).commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.progress_container, ProgressFragment.newInstance(null, null)).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.answer_container, AnswerFragment.newInstance(null, null)).commit();
        mFAB.setImageResource(android.R.drawable.ic_media_next);
    }


    private void subscribe() {

        final Observer<Integer> questionObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer qNum) {
                questionNum = qNum;
                displayContent();
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
