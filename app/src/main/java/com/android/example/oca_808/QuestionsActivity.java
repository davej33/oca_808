package com.android.example.oca_808;

import android.arch.lifecycle.Observer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
        ProgressFragment.OnFragmentInteractionListener, AnswerFragment.OnFragmentInteractionListener{

    private static final String LOG_TAG = QuestionsActivity.class.getSimpleName();
    private Integer questionNum = 0;
    private static QuestionsViewModel mViewModel;
    private FloatingActionButton mFAB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        mViewModel = new QuestionsViewModel(getApplicationContext());
        displayContent();
        if(mFAB == null) {
            Log.w(LOG_TAG, "new FAB");
            mFAB = findViewById(R.id.floatingActionButton);
            mFAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = AnswerFragment.getUserAnswer();
                    Log.w(LOG_TAG, "user answer: " + s);
                    boolean correctAnswer = mViewModel.checkAnswer(s);
                    Log.w(LOG_TAG, "Answer correct: " + correctAnswer);
                    if(correctAnswer){
                        Toast.makeText(QuestionsActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(QuestionsActivity.this, "Not so much", Toast.LENGTH_SHORT).show();
                    }
                    mViewModel.nextQuestion();

                    // store answer in Test object, create test object
                }
            });
        }

        subscribe();
    }

    private void displayContent() {
        getSupportFragmentManager().beginTransaction().replace(R.id.question_container, QuestionFragment.newInstance(questionNum,null)).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.progress_container, ProgressFragment.newInstance(null,null)).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.answer_container, AnswerFragment.newInstance(null,null)).commit();
    }


    private void subscribe() {

        final Observer<Integer> questionObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer qNum) {
                questionNum = qNum;
                displayContent();
            }
        };
       mViewModel.newQuestion().observe(this,questionObserver);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
