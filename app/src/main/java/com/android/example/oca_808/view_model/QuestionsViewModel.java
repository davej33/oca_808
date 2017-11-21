package com.android.example.oca_808.view_model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.android.example.oca_808.db.entity.QuestionEntity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by charlotte on 11/21/17.
 */

public class QuestionsViewModel extends ViewModel {

    private ArrayList<QuestionEntity> mQuestionsList;
    private static final int ONE_SECOND = 1;
    private long mInitialTime;
    private MutableLiveData<Long> mElapsedTime;

    public QuestionsViewModel(){
        startTimer();
    }

    // TODO: efficient?
    private void startTimer() {
        mInitialTime = SystemClock.elapsedRealtime();
        Timer timer = new Timer();

        // Update the elapsed time every second.
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final long newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000;
                // setValue() cannot be called from a background thread so post to main thread.
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mElapsedTime.setValue(newValue);

                    }
                });
            }
        }, ONE_SECOND, ONE_SECOND);}
}
