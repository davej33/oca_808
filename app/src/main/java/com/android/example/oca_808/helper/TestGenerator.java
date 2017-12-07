package com.android.example.oca_808.helper;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.example.oca_808.db.AppDatabase;
import com.android.example.oca_808.db.entity.QuestionEntity;
import com.android.example.oca_808.db.entity.TestEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by charlotte on 11/21/17.
 */

public final class TestGenerator {

    private static final String LOG_TAG = TestGenerator.class.getSimpleName();
    private static long mStartTime;
    private static AppDatabase mDb;
    private static List<QuestionEntity> mQuestions;

    public static void createTestSim() {
        // get questions

        List<Integer> questionList = mDb.questionsDao().getQuestionIds();
        String questionListString = questionList.toString();

        // create list for answers
        List<String> answerArrayList = new ArrayList<>(questionList.size());
        answerArrayList.add("b");
        answerArrayList.add("def");
        String answerListString = answerArrayList.toString();

        // create list for storing time elapsed on each question
        List<String> elapsedQuestionTimeList = new ArrayList<>(questionList.size());
        String elapsedQuestionTimeString = elapsedQuestionTimeList.toString();

        // get local time in milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime startTime = LocalDateTime.now();
            ZoneId zoneId = ZoneId.systemDefault();
            mStartTime = startTime.atZone(zoneId).toEpochSecond();
        } else {
            mStartTime = System.currentTimeMillis();
        }

        // create new test
        TestEntity newTest = new TestEntity(1, questionListString, answerListString, elapsedQuestionTimeString, false, mStartTime, 0, 0, 0, 1, questionList.size(), 1);
        long testInsertCheck = mDb.testsDao().insertNewTest(newTest);
        Log.i(LOG_TAG, "test to string: " + newTest.toString());
        Log.i(LOG_TAG, "test insert check: " + testInsertCheck);
    }

    public static void addQs(Context context) {
        mDb = AppDatabase.getDb(context);
        if (mQuestions == null) {
            mQuestions = new ArrayList<>();
            mQuestions.add(new QuestionEntity(1101, 11, 1, "1: What is \n2: Java? 1101", "A good cup of jo", "small mammal", "large lizard", "programming language", "no idea", "a fish", "d", "Just cuz", 2));
            mQuestions.add(new QuestionEntity(4101, 41, 0, "Given the following array, which statements evaluate to &?\n\nchar[] foo = {‘X’,’1’,’Y’,’2’,’Z,’&’};", "foo[6];", "foo[5];",
                    "foo[foo.length()];", "foo[foo.length()-1];", "Does not compile", "None of the above", "bd", "Key Points:\n - Array indices begin at 0\n - Array length begins at 1\n\n     index =   0   1   2   3   4  5\n " +
                    "char[] foo = {‘X’,’A’,’Y’,’B’,’Z,’&’};\n      length =   1   2   3   4   5  6\n\nfoo[6] tries to access index 6 which doesn’t exist so it will throw an ArrayIndexOutOfBoundsException\n " +
                    "foo[5] is correct\nfoo[foo.length()]  tries to access index 6 which doesn’t exist so it will throw an ArrayIndexOutOfBoundsException.\nfoo[foo.length() - 1] is correct\nDoes not compile is incorrect\n" +
                    "'None' is incorrect because two answers evaluate to &", 3));
            mQuestions.add(new QuestionEntity(1102, 11, 1, "1: What is \n2: Java?", "A good cup of jo", "small mammal", "large lizard", "programming language", "no idea", "a fish", "d", "Just cuz", 1));
            mQuestions.add(new QuestionEntity(4102, 41, 0, "Given the following array, which statements evaluate to &?\n\nchar[] foo = {‘X’,’1’,’Y’,’2’,’Z,’&’};", "foo[6];", "foo[5];",
                    "foo[foo.length()];", "foo[foo.length()-1];", "Does not compile", "None of the above", "bd", "Key Points:\n - Array indices begin at 0\n - Array length begins at 1\n\n     index =   0   1   2   3   4  5\n " +
                    "char[] foo = {‘X’,’A’,’Y’,’B’,’Z,’&’};\n      length =   1   2   3   4   5  6\n\nfoo[6] tries to access index 6 which doesn’t exist so it will throw an ArrayIndexOutOfBoundsException\n " +
                    "foo[5] is correct\nfoo[foo.length()]  tries to access index 6 which doesn’t exist so it will throw an ArrayIndexOutOfBoundsException.\nfoo[foo.length() - 1] is correct\nDoes not compile is incorrect\n" +
                    "'None' is incorrect because two answers evaluate to &", 3));

            long[] x = TestGenerator.mDb.questionsDao().insertQuestions(mQuestions);
            Log.w(LOG_TAG, "insert count: " + x.length);

        }
    }
}
