package com.android.example.oca_808;

import android.app.Fragment;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.example.oca_808.fragment.QuestionFragment;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by charlotte on 11/21/17.
 */

public class QuestionsActivity extends AppCompatActivity{

    private Integer questionNum = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_question);

        getSupportFragmentManager().beginTransaction().add(R.id.question_container, QuestionFragment.newInstance(questionNum,null)).commit();

        subscribe();
    }

    private void subscribe() {
        final Observer questionObserver = new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                questionNum = (Integer) o;
                getSupportFragmentManager().beginTransaction().add(R.id.question_container, QuestionFragment.newInstance(questionNum,null)).commit();
            }
        };
    }


}
