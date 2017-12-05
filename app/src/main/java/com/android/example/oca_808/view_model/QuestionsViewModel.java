package com.android.example.oca_808.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
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
    private static StringBuilder mUserAnswer;


    // Timer vars
//    private static final int ONE_SECOND = 1;
//    private long mInitialTime;
//    private MutableLiveData<Long> mElapsedTime = new MutableLiveData<>();


    // constructor
    public QuestionsViewModel(Context context) {

        // instantiate db if null
        if (mDb == null) {
            mDb = AppDatabase.getDb(context);
        }

        // set question list
        setQuestionsList();

        // set question number value to 1
        if (mQuestionNumber == null) {
            mWhereWeAt = 1;
            mQuestionNumber = new MutableLiveData<>();
            mQuestionNumber.setValue(mWhereWeAt); //TODO: update to accommodate resumed test
        }

        mCurrentQuestion = mQuestionsList.get(mWhereWeAt);
        mUserAnswer = new StringBuilder();

        if (mUserAnswerArray == null) {
            mUserAnswerArray = new ArrayList<>();
            mUserAnswerArray.add(null);
        }
//        startTimer();
//        int index = mQuestionsList.indexOf(mCurrentQuestion);
    }


    public void setQuestionsList() {
        mQuestionsList = (ArrayList<QuestionEntity>) mDb.questionsDao().getQuestions();
        mQuestionsList.add(0, null);
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

    // called from QuestionActivity on FAB click
    // compares user answer and returns any wrong answers in ArrayList
    public ArrayList<String> checkAnswer() {

        ArrayList<String> wrongAnswers = new ArrayList<>(); // create arraylist to hold wrong answers

        if (mCurrentQuestion.getType() == 1) { // 1 == single answer. if question type is Radio
            if (!mUserAnswer.toString().equals(mCurrentQuestion.answer)) { // if userAnswer [is wrong] doesn't equal correct answer
                wrongAnswers.add(mUserAnswer.toString()); // add the user's answer to the wrongAnswer arraylist
            }
        } else { // if question type is checkbox
            String correctAnswer = mCurrentQuestion.getAnswer(); // store correct answer in String
            for (int i = 0; i < mUserAnswer.length(); i++) { // iterate through each letter in user's answer
                String check = String.valueOf(mUserAnswer.charAt(i)); // store each user answer in String
                if (!correctAnswer.contains(check)) { // if user answer not in answer
                    wrongAnswers.add(check); // add to wrongAnswer Arraylist
                }
            }
        }
        return wrongAnswers;
    }

    // TODO: Implement
    public void setUserAnswer() {
        // add to arrayList if unanswered, if changing previous answer then set corresponding element
        if (mUserAnswerArray.size() <= mWhereWeAt) {
            mUserAnswerArray.add(mUserAnswer.toString());
            Log.w(LOG_TAG, "add " + mUserAnswer.toString() + " at index " + mWhereWeAt);
        } else {
            mUserAnswerArray.set(mWhereWeAt, mUserAnswer.toString());
            Log.w(LOG_TAG, "set " + mUserAnswer.toString() + " at index " + mWhereWeAt);
        }
    }

    public void nextQuestion() {
        mQuestionNumber.setValue(++mWhereWeAt);
        mCurrentQuestion = mQuestionsList.get(mWhereWeAt);
    }

    //  TODO: implement
    public void loadPreviousQuestion() {
        mQuestionNumber.setValue(--mWhereWeAt);
        mCurrentQuestion = mQuestionsList.get(mWhereWeAt);
    }

    public int getmWhereWeAt() {
        return mWhereWeAt;
    }

    public int getQuestionCount() {
        return mQuestionsList.size();
    }

    public String getUserAnswer() {
//        Log.w(LOG_TAG, "@@@@@@@@ user array size = " + mUserAnswerArray.size() + " WWA = " + mWhereWeAt);
        if (mUserAnswerArray.size() > mWhereWeAt) { // if loading a question that's already been answered
//            Log.w(LOG_TAG, "@@@@@@@@ existing user answer = " + mUserAnswerArray.get(mWhereWeAt));
            return mUserAnswerArray.get(mWhereWeAt); // return the answer
        } else {
//            Log.w(LOG_TAG, "@@@@@@@@ new user answer = " + mUserAnswer.toString());
            return mUserAnswer.toString(); // otherwise, return the current user answer
        }
    }

    public void collectUserAnswer(char userAnswer, boolean checkState) {
        if (mCurrentQuestion.type == 1) {
            if (mUserAnswer.length() == 1) mUserAnswer.deleteCharAt(0);
            mUserAnswer.append(userAnswer);
        } else if (mCurrentQuestion.type == 0 && checkState) {
            if (!mUserAnswer.toString().contains(String.valueOf(userAnswer)))
                mUserAnswer.append(userAnswer);
        } else if (mCurrentQuestion.type == 0) {
            mUserAnswer.deleteCharAt(mUserAnswer.indexOf(String.valueOf(userAnswer)));
        }
    }
}