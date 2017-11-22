package com.android.example.oca_808;

import android.arch.lifecycle.Observer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.example.oca_808.fragment.QuestionFragment;
import com.android.example.oca_808.view_model.QuestionsViewModel;



/**
 * Created by charlotte on 11/21/17.
 */

public class QuestionsActivity extends AppCompatActivity implements QuestionFragment.OnFragmentInteractionListener{

    private Integer questionNum = 0;
    private static QuestionsViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Log.w("QuestionActivity", "onCreate run");
        int i = getSupportFragmentManager().beginTransaction().add(R.id.question_container, QuestionFragment.newInstance(questionNum,null)).commit();
        Log.w("QuestionActivity", "int i: " + i);
//        subscribe();
    }



    private void subscribe() {
        mViewModel = new QuestionsViewModel(getApplicationContext());
        final Observer<Integer> questionObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer qNum) {
                questionNum = qNum;
                getSupportFragmentManager().beginTransaction().add(R.id.question_container, QuestionFragment.newInstance(questionNum,null)).commit();
            }
        };
       mViewModel.newQuestion().observe(this,questionObserver);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
