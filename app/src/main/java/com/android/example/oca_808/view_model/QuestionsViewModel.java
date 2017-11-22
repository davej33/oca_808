package com.android.example.oca_808.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.android.example.oca_808.db.AppDatabase;
import com.android.example.oca_808.db.entity.QuestionEntity;

import java.util.ArrayList;

/**
 * Created by charlotte on 11/21/17.
 */

public class QuestionsViewModel extends ViewModel {

    private static final String LOG_TAG = QuestionsViewModel.class.getSimpleName();

    private static AppDatabase mDb;
    private static ArrayList<QuestionEntity> mQuestionsList;
    private static MutableLiveData<Integer> mQuestionNumber;
    private QuestionEntity mCurrentQuestion;


    private static final int ONE_SECOND = 1;
    private long mInitialTime;
    private MutableLiveData<Long> mElapsedTime = new MutableLiveData<>();

    // constructor
    public QuestionsViewModel(Context context){

        // instantiate db if null
        if(mDb == null) {
            mDb = AppDatabase.getDb(context);
            Log.w(LOG_TAG, "instantiated Db");
        }

        // set question list
        setQuestionsList();

        // set question number value to 1
        if(mQuestionNumber == null) {
            mQuestionNumber = new MutableLiveData<>();
            mQuestionNumber.setValue(1); //TODO: update to accommodate resumed test
            Log.w(LOG_TAG, "set Question number to 1");
        }
        mCurrentQuestion = mQuestionsList.get(mQuestionNumber.getValue() - 1);



//        startTimer();
    }




    public void setQuestionsList() {
        mQuestionsList = (ArrayList<QuestionEntity>) mDb.questionsDao().getQuestions();
    }

    public LiveData<Integer> newQuestion(){
        return mQuestionNumber;
    }

//    public String getQuestion(int i){
//        QuestionEntity questionEntity = mQuestionsList.get(i);
//        return questionEntity.getQuestion();
//    }

    public QuestionEntity getCurrentQuestion() {
        return mCurrentQuestion;
    }

    // TODO: efficient?
    private void startTimer() {
//        mInitialTime = SystemClock.elapsedRealtime();
//        Timer timer = new Timer();
//
//        // Update the elapsed time every second.
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                final long newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000;
//                // setValue() cannot be called from a background thread so post to main thread.
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mElapsedTime.setValue(newValue);
//
//                    }
//                });
//            }
//        }, ONE_SECOND, ONE_SECOND);
    }
}
