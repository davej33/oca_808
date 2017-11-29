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

    // database
    private static AppDatabase mDb;

    // Question vars
    private static ArrayList<QuestionEntity> mQuestionsList;
    private static MutableLiveData<Integer> mQuestionNumber;
    private static QuestionEntity mCurrentQuestion;
    private static int mWhereWeAt;

    // Test vars
    private static ArrayList<String> mUserAnswerArray;
    private String mUserAnswer;

    // Timer vars
    private static final int ONE_SECOND = 1;
    private long mInitialTime;
    private MutableLiveData<Long> mElapsedTime = new MutableLiveData<>();


    // constructor
    public QuestionsViewModel(Context context) {

        // instantiate db if null
        if (mDb == null) {
            mDb = AppDatabase.getDb(context);
            Log.w(LOG_TAG, "instantiated Db");
        }

        // set question list
        setQuestionsList();

        // set question number value to 1
        if (mQuestionNumber == null) {
            mWhereWeAt = 1;
            mQuestionNumber = new MutableLiveData<>();
            mQuestionNumber.setValue(mWhereWeAt); //TODO: update to accommodate resumed test
            Log.w(LOG_TAG, "set Question number to 1");
        }
        mCurrentQuestion = mQuestionsList.get(mWhereWeAt);


        if (mUserAnswerArray == null) {
            Log.w(LOG_TAG, "instantiated userAnswerArray");
            mUserAnswerArray = new ArrayList<>();
            mUserAnswerArray.add(null);
        }
//        startTimer();
        int index = mQuestionsList.indexOf(mCurrentQuestion);
        Log.w(LOG_TAG, "index / wwa: " + index + " / " + mWhereWeAt);
    }

    public void setQuestionsList() {
        mQuestionsList = (ArrayList<QuestionEntity>) mDb.questionsDao().getQuestions();
        Log.w(LOG_TAG, "Question List size: " + mQuestionsList.size());
        mQuestionsList.add(0, null);
        Log.w(LOG_TAG, "2 Question List size: " + mQuestionsList.size());
    }

    public LiveData<Integer> newQuestion() {
        return mQuestionNumber;
    }


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

    public ArrayList<String> checkAnswer(String userAnswer) {
        mUserAnswer = userAnswer;
        ArrayList<String> wrongAnswers = new ArrayList<>();
        if (mCurrentQuestion.getType() == 1) { // 1 == single answer
            if (!userAnswer.equals(mCurrentQuestion.getAnswer())) {
                wrongAnswers.add(userAnswer);
                return wrongAnswers;
            }
        } else {
            String correctAnswer = mCurrentQuestion.getAnswer();
            for (int i = 0; i < userAnswer.length(); i++) {
                String check = String.valueOf(userAnswer.charAt(i));
                if(!correctAnswer.contains(check)){
                    wrongAnswers.add(check);
                }
            }
        }
        return wrongAnswers;
    }
    public int addUserAnswer(String userAnswer) {
        // add to arrayList if unanswered, if changing previous answer then set corresponding element
        if (mUserAnswerArray.size() <= mWhereWeAt) {
            mUserAnswerArray.add(userAnswer);
            Log.w(LOG_TAG, "add ");
        } else {
            mUserAnswerArray.set(mWhereWeAt, userAnswer);
            Log.w(LOG_TAG, "set ");
        }
        return mUserAnswerArray.size();
    }
    public void nextQuestion(){
        mQuestionNumber.setValue(++mWhereWeAt);
        mCurrentQuestion = mQuestionsList.get(mWhereWeAt);
    }
    public void previousQuestion(){
        mQuestionNumber.setValue(--mWhereWeAt);
        mCurrentQuestion = mQuestionsList.get(mWhereWeAt);
    }

    public int getmWhereWeAt(){
        return mWhereWeAt;
    }

    public int getQuestionCount(){
        return mQuestionsList.size();
    }

    public String getmUserAnswer() {
        return mUserAnswer;
    }
}
