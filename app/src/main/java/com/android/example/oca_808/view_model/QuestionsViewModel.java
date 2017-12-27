package com.android.example.oca_808.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.android.example.oca_808.db.AppDatabase;
import com.android.example.oca_808.db.entity.QuestionEntity;
import com.android.example.oca_808.db.entity.TestEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private static TestEntity mCurrentTest;
    private static List<String> mUserAnswerArray;
    private static StringBuilder mUserAnswer;
    private static List<String> mMarkedQuestions;

    private static boolean mInitiated;
    // Timer vars
    private static final int ONE_MINUTE = 60000;
    private static final int TEST_START_DURATION = 9000000;
    private static MutableLiveData<String> mTimeRemaining = new MutableLiveData<>();
    private static TestCountdownTimer mTimer;
    private static int mMin = 30;   // TODO add to test object
    private static int mHour = 2;
    private static int mLoadTestId;
    private static QuestionsViewModel mQuestionViewModel;

    // constructor
    public QuestionsViewModel(Application mApplication) {

        // instantiate db if null
        if (mDb == null) {
            mDb = AppDatabase.getDb(mApplication);
        }
    }


    public LiveData<Integer> newQuestion() {
        return mQuestionNumber;
    }

    public QuestionEntity getCurrentQuestion() {
        return mCurrentQuestion;
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

    public void startTimer() {
        if (mTimer == null) mTimer = new TestCountdownTimer(TEST_START_DURATION, ONE_MINUTE);
        mTimer.start();
    }

    public void stopTimer() {

    }

    public void nextQuestion() {
        mQuestionNumber.setValue(++mWhereWeAt);
        mCurrentQuestion = mQuestionsList.get(mWhereWeAt);

        // set user answer
        mUserAnswer.delete(0, mUserAnswer.length());
        String s = mUserAnswerArray.get(mWhereWeAt);
        if (!s.equals("")) {
            for (char c : s.toCharArray()) {
                mUserAnswer.append(c);
            }
        }
    }

    public void loadPreviousQuestion() {
        mQuestionNumber.setValue(--mWhereWeAt);
        mCurrentQuestion = mQuestionsList.get(mWhereWeAt);
        // set user answer
        mUserAnswer.delete(0, mUserAnswer.length());
        String s = mUserAnswerArray.get(mWhereWeAt);
        if (!s.equals("")) {
            for (char c : s.toCharArray()) {
                mUserAnswer.append(c);
            }
        }
    }

    public int getmWhereWeAt() {
        return mWhereWeAt;
    }

    public int getQuestionCount() {
        return mQuestionsList.size();
    }

    public String getUserAnswer() {

        if (mUserAnswerArray.size() > mWhereWeAt) { // if loading a question that's already been answered
//            Log.w(LOG_TAG, "Question answered. getUserAnswer return = " + mUserAnswerArray.get(mWhereWeAt));
            return mUserAnswerArray.get(mWhereWeAt); // return the answer
        } else {
//            Log.w(LOG_TAG, "getUserAnswer = Unanswered");
            mUserAnswer.delete(0, mUserAnswer.length());
            return ""; // otherwise, return the current user answer
        }
    }

    public void collectUserAnswer(char userAnswer, boolean checkState) {
//        Log.i(LOG_TAG, "1. VM uAnswersArray: " + mUserAnswerArray.toString());
        if (mCurrentQuestion.type == 1) {
            if (mUserAnswer.length() == 1) mUserAnswer.deleteCharAt(0);
            mUserAnswer.append(userAnswer);
        } else if (mCurrentQuestion.type == 0 && checkState) {
            if (!mUserAnswer.toString().contains(String.valueOf(userAnswer)))
                mUserAnswer.append(userAnswer);
//            Log.i(LOG_TAG, "added to mUserAnswer: " + mUserAnswer);
        } else if (mCurrentQuestion.type == 0) {
            mUserAnswer.deleteCharAt(mUserAnswer.indexOf(String.valueOf(userAnswer)));
//            Log.i(LOG_TAG, "removed from mUserAnswer: " + mUserAnswer);
        }
        mUserAnswerArray.set(mWhereWeAt, mUserAnswer.toString());
        Log.i(LOG_TAG, "Test ID: " + mCurrentTest._id + ". uAnswersArray: " + mUserAnswerArray.toString());
    }

    public void setmWhereWeAt(int i) {
        mWhereWeAt = i;
        mCurrentQuestion = mQuestionsList.get(mWhereWeAt);
    }

    // ---------------------------------- get and set test --------------------
    public void getTest(int testId) {

        clearVars();

        // get TestEntity
        mCurrentTest = mDb.testsDao().fetchTest(testId);
        Log.i(LOG_TAG, "current test title: " + mCurrentTest.title);

        setTestAttributes();
    }

    private void setTestAttributes() {
        //TODO: add timer fetch and set
        // get questions
        // set the questions list
        mQuestionsList = setQuestionsList();

        // set user answer to ""
        mUserAnswer = new StringBuilder();

        // set starting point
        if (mQuestionNumber == null) {
            mWhereWeAt = mCurrentTest.resumeQuestionNum;
            Log.i(LOG_TAG, "WWA: " + mWhereWeAt);
            mQuestionNumber = new MutableLiveData<>();
            mQuestionNumber.setValue(mWhereWeAt);
        }
        if (mCurrentQuestion == null) {
            mCurrentQuestion = mQuestionsList.get(mWhereWeAt);
        }

        // TODO: should only do this if resuming test
        // convert answer list to StringBuilder to remove brackets then to List


        // store answers in stringbuilder
        StringBuilder sb = new StringBuilder(mCurrentTest.answerSet);
        Log.i(LOG_TAG, "*** sb = " + sb);

        // remove brackets
        sb.deleteCharAt(sb.length() - 1).deleteCharAt(0);
        Log.i(LOG_TAG, "*** sb = " + sb);

        if (sb.length() > 0) { // if userAnswerList has been initialized
            // convert string to list
            mUserAnswerArray = new ArrayList<>(Arrays.asList((sb.toString()).split(", ")));
            Log.w(LOG_TAG, "user answer array init: " + mUserAnswerArray.toString());

            // if the users Answer list is smaller than the number of test questions [plus 1 to account for the added null at index 0]
            if (mUserAnswerArray.size() < mCurrentTest.questionCount + 1) {

                // add an empty string until the answer list size equals the number questions
                for (int i = mUserAnswerArray.size(); i < mCurrentTest.questionCount + 1; i++) {
                    mUserAnswerArray.add("");
                }

                Log.w(LOG_TAG, "user answer array init final: " + mUserAnswerArray.toString());
            }
        } else {
            mUserAnswerArray = new ArrayList<>();

            // add an empty string until the answer list size equals the number questions
            for (int i = 0; i < mCurrentTest.questionCount; i++) {
                mUserAnswerArray.add("");
            }
            // add null at index 0 so question/index numbers align
//            if (!mUserAnswerArray.get(0).equals("null"))
            mUserAnswerArray.add(0, null);
            Log.w(LOG_TAG, "user answer array init: " + mUserAnswerArray.toString());
        }


        // convert answer list to StringBuilder to remove brackets then to List
        StringBuilder sb2 = new StringBuilder(mCurrentTest.mMarkedQuestionSet);
        sb2.deleteCharAt(sb2.length() - 1).deleteCharAt(0);
        if (sb2.length() < 1) {
            mMarkedQuestions = new ArrayList<>();
            for (int i = 0; i < mCurrentTest.questionCount; i++) {
                mMarkedQuestions.add("");
            }
        } else {
            mMarkedQuestions = new ArrayList<>(Arrays.asList((sb2.toString()).split(", ")));
        }
//        Log.i(LOG_TAG, "marked q array length: " + mMarkedQuestions.size());

        // add null at index 0 so question/index numbers align
        if (!mMarkedQuestions.get(0).equals("null")) mMarkedQuestions.add(0, null);
//        Log.w(LOG_TAG, "mUserAnswer array init: " + mMarkedQuestions.toString());
    }

    public ArrayList<QuestionEntity> setQuestionsList() {

        // get question list from the TestEntity, remove brackets, then convert to List
        StringBuilder questionsStringBuilder = new StringBuilder(mCurrentTest.questionSet);
        Log.i(LOG_TAG, "^^^Current test questionList: " + mCurrentTest.questionSet);


        questionsStringBuilder.deleteCharAt(questionsStringBuilder.length() - 1).deleteCharAt(0);
        String questionsString = questionsStringBuilder.toString();
        List<String> qIdListAsStrings = Arrays.asList(questionsString.split(", "));

        // fetch questions from db and store
        mQuestionsList = (ArrayList<QuestionEntity>) mDb.questionsDao().getQuestions(qIdListAsStrings);
        Log.i(LOG_TAG, "questionList count: " + mQuestionsList.size());

        // add null at index 0 so question number matches indexed position
        mQuestionsList.add(0, null);
        Log.i(LOG_TAG, "questionList count with null added: " + mQuestionsList.size());
        return mQuestionsList;
    }


    private int calculateProgress() {
        if (mUserAnswerArray.size() > 0) {
            int questionsAnswered = 0;
            for (String s : mUserAnswerArray) {
                if (s != null && (!(s.equals("") || s.equals("null")))) {
                    questionsAnswered++;
                }
            }
            int testLength = mQuestionsList.size() - 1;
            int progress = (100 * questionsAnswered) / testLength;

//            Log.w(LOG_TAG, "Progress - answered" + questionsAnswered + " of " + (mQuestionsList.size() - 1) + " equaling " + progress + "% complete");

            return progress;
        } else {
            return 0;
        }
    }

    public void setQVM(QuestionsViewModel QVM) {
        mQuestionViewModel = QVM;
    }

    public static QuestionsViewModel getQVM() {
        return mQuestionViewModel;
    }


    public AppDatabase getDb() {
        return mDb;
    }

    public String getTestTitle() {
        return mCurrentTest.title;
    }

    // get and set marked questions
    public void setmMarkedQuestion(boolean b) {
        if (b) {
            mMarkedQuestions.set(mWhereWeAt, "1");
        } else {
            mMarkedQuestions.set(mWhereWeAt, "0");
        }
        Log.i(LOG_TAG, "marked q array length: " + mMarkedQuestions.toString());
        Log.i(LOG_TAG, "marked question state at " + mWhereWeAt + " is " + mMarkedQuestions.get(mWhereWeAt));
    }

    public String getMarkedState(int i) {
        return mMarkedQuestions.get(i);
    }

    public String getAnswerSubmitted(int i) {
        return mUserAnswerArray.get(i);
    }

    public int getTestScore() {
        int score = 0;

        // iterate through each question
        for (int i = 1; i < mQuestionsList.size(); i++) {
            String userAnswer = "";

            if (mUserAnswerArray.get(i) != null && mUserAnswerArray.get(i).length() > 0) {
                userAnswer = getSortedString(mUserAnswerArray.get(i).toCharArray());
            }
            String sol = getSortedString(mQuestionsList.get(i).answer.toCharArray());

            Log.i(LOG_TAG, "user / sol ---- " + userAnswer + " / " + sol);
            if (userAnswer.equals(sol)) {
                score++;
                Log.i(LOG_TAG, "score: " + score);
            }
        }
        Log.i(LOG_TAG, "score2: " + score);
        return score;
    }

    private String getSortedString(char[] chars) {
        StringBuilder sb = new StringBuilder();

        // get sorted array for user answer
        Arrays.sort(chars);
        for (char c : chars) {
            sb.append(c);
        }
        return sb.toString();
    }

    public LiveData<String> getTimeRemaining() {
        return mTimeRemaining;
    }

    public static class TestCountdownTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         * to {@link #start()} until the countdown is done and {@link #onFinish()}
         * is called.
         * @param countDownInterval The interval along the way to receive
         * {@link #onTick(long)} callbacks.
         */


        String timeRemaining;

        public TestCountdownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            timeRemaining = "" + mHour + ":" + mMin;
            mTimeRemaining.setValue(timeRemaining);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            if (mMin == 0 && mHour == 2) {
                mMin = 59;
                mHour = 1;
            } else if (mMin == 0 && mHour == 1) {
                mMin = 59;
                mHour = 0;
            } else {
                --mMin;
            }

            if (mMin < 10) {
                timeRemaining = "" + mHour + ":" + "0" + mMin;
            } else {
                timeRemaining = "" + mHour + ":" + mMin;
            }


            mTimeRemaining.setValue(timeRemaining);
//            Log.i(LOG_TAG, "LiveData value: " + mTimeRemaining.getValue());
        }

        @Override
        public void onFinish() {

        }
    }

    public void clearVars() {
        if (mCurrentTest != null) {
            mCurrentTest = null;
            mQuestionsList = null;
            mCurrentQuestion = null;
            mUserAnswerArray = null;
            mUserAnswer = null;
        }
    }

    public void saveDataToDb() {
        mCurrentTest.setAnswerSet(mUserAnswerArray.toString());
        mCurrentTest.setResumeQuestionNum(mWhereWeAt);
        mCurrentTest.setProgress(calculateProgress());

        int updateCheck = mDb.testsDao().updateTestResults(mCurrentTest);
//        Log.w(LOG_TAG, "^^^^^^^^^ update check: " + updateCheck);
    }

}

