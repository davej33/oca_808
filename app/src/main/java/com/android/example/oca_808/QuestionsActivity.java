package com.android.example.oca_808;

import android.arch.lifecycle.Observer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.example.oca_808.fragment.AnswerFragment;
import com.android.example.oca_808.fragment.ProgressFragment;
import com.android.example.oca_808.fragment.QuestionFragment;
import com.android.example.oca_808.view_model.QuestionsViewModel;



/**
 * Created by charlotte on 11/21/17.
 */

public class QuestionsActivity extends AppCompatActivity implements QuestionFragment.OnFragmentInteractionListener,
        ProgressFragment.OnFragmentInteractionListener, AnswerFragment.OnFragmentInteractionListener{

    private Integer questionNum = 0;
    private static QuestionsViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        displayContent();


//        subscribe();
    }

    private void displayContent() {
        getSupportFragmentManager().beginTransaction().add(R.id.question_container, QuestionFragment.newInstance(questionNum,null)).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.progress_container, ProgressFragment.newInstance(null,null)).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.answer_container, AnswerFragment.newInstance(null,null)).commit();
    }


    private void subscribe() {
        mViewModel = new QuestionsViewModel(getApplicationContext());
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
