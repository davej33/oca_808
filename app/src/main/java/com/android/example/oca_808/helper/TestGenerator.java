package com.android.example.oca_808.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.example.oca_808.R;
import com.android.example.oca_808.db.AppDatabase;
import com.android.example.oca_808.db.entity.QuestionEntity;
import com.android.example.oca_808.db.entity.TestEntity;
import com.android.example.oca_808.view_model.QuestionsViewModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by charlotte on 11/21/17.
 */

public final class TestGenerator {

    private static final String LOG_TAG = TestGenerator.class.getSimpleName();
    private static final int DIFF_EASY = 1;
    private static final int DIFF_MED = 2;
    private static final int DIFF_HARD = 3;
    private static long mStartTime;
    private static AppDatabase mDb;
    private static List<QuestionEntity> mQuestions;
    private static final String TEST_NUM_TEXT = "Test_";
    private static final String PRACTICE_NUM_TEXT = "Pract_";
    private static int mTestNum;
    private static SharedPreferences shPref;

    public static void createTestSim(Context context, int testType) {

        // get db
        mDb = AppDatabase.getDb(context);

        // get questions
        String testTitle = createTestTitle(context, testType); // Test sim = 1, practice test = 0

        // save current test in shared preferences as the most recent test, allowing user to resume
        saveTestIdInSharedPref(context, testType);

        List<Integer> questionList = mDb.questionsDao().getQuestionIds();
        String questionListString = questionList.toString();

        // create list for answers
        String answerArrayList = new ArrayList<>(questionList.size()).toString();


        // create list for storing time elapsed on each question
        String elapsedQuestionTimeList = new ArrayList<>(questionList.size()).toString();

        // get local time in milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime startTime = LocalDateTime.now();
            ZoneId zoneId = ZoneId.systemDefault();
            mStartTime = startTime.atZone(zoneId).toEpochSecond();
        } else {
            mStartTime = System.currentTimeMillis();
        }

        // create new test
        TestEntity newTest = new TestEntity(testType, testTitle, questionListString, answerArrayList,
                elapsedQuestionTimeList, false, 0, mStartTime, 0, 0, 0, 1, questionList.size(), 1);
        long insertTest = mDb.testsDao().insertNewTest(newTest);
        QuestionsViewModel.getQVM().getTest(mTestNum);

    }

    private static void saveTestIdInSharedPref(Context context, int testType) {
        SharedPreferences.Editor editor = shPref.edit();
        if(testType == 1){
            editor.putInt(context.getResources().getString(R.string.sp_resume_test), mTestNum);
        } else {
            editor.putInt(context.getResources().getString(R.string.sp_resume_practice_test), mTestNum);
        }
        editor.apply();
    }

    private static String createTestTitle(Context context, int type) {
        shPref = PreferenceManager.getDefaultSharedPreferences(context);
        mTestNum = shPref.getInt(context.getResources().getString(R.string.sp_test_num_key), -1);
        SharedPreferences.Editor editor = shPref.edit();
        editor.putInt(context.getResources().getString(R.string.sp_test_num_key), ++mTestNum);
        editor.apply();
        String title = "";
        if (type == 1) {
            title = TEST_NUM_TEXT + String.valueOf(mTestNum);
        } else {
            title = PRACTICE_NUM_TEXT + String.valueOf(mTestNum);
        }
        return title;
    }

    public static boolean addQs(Context context) {
        mDb = AppDatabase.getDb(context);
        if (mQuestions == null) {
            mQuestions = new ArrayList<>();

            // ---------------- Java Basics 9900 -----------------------
            mQuestions.add(new QuestionEntity(9901, 0, "Select all valid identifiers.", "$xYz", "3foo", "_bar", "true", "True", "foo3", "acef",
                            "Java identifiers:\nA) Can start with and contain ‘$’ or ‘_’\nB) Can contain numbers, but not at the beginning\nC) Can start with and contain ‘$’ or ‘_’\nD) Can not "
                            + "use a keyword as an identifier\nE) Case matters. ‘True’ is valid, ‘true’ is not\nF) Can contain numbers, not at the beginning\n", DIFF_EASY));


            mQuestions.add(new QuestionEntity(9906, 0, "Select all the valid identifiers.", "public", "foo.bar", "keyword", "Valid!", "pump_it_up", "No valid options", "ce",
                            "A) Can not use a reserved word\nB) Can not contain ‘.’; ‘$’ and ‘_’ only valid special characters\nC) ’keyword’ is valid; not a reserved keyword\nD) Can not " +
                            "contain ‘!’; ‘$’ and ‘_’ only valid special characters\nE) Case matters; ‘Public’ is valid, ‘public’ is not\nF) Two valid identifiers", DIFF_MED));


            // ---------------- Java Basics 1100 -----------------------
            mQuestions.add(new QuestionEntity(1103, 0, "Given the following class, the code will compile if…\n1:  public class Apple{\n2:\n3:       public void green(boolean isSliced){\n4: \n5:" +
                    "           if(isSliced){\n6:\n7:        }\n8:   System.out.print(fruit);\n9:\n10:    }\n11: }", "String fruit = \"eat\"; is added on line 2", "String fruit = \"eat\"; is added on line 4",
                    "String fruit = \"eat\"; is added on line 6", "String fruit = \"eat\"; is added on line 9", "String fruit = \"eat\"; is added on line 10", "None of these options will compile",
                    "ab", "A) Correct: instance variables are in scope for the life of the object.\nB) Correct: fruit is local to the method in which it is called from in line 8.\nC) Incorrect: " +
                    "fruit would only be in scope within the if statement.\nD & E) Incorrect: fruit would be declared after the call on line 8, thus out of scope.\n", DIFF_EASY));


            // ---------------- Data Types 2100 ---------------------

            mQuestions.add(new QuestionEntity(2103, 1, "What will the following code output?\n1:  public class Cup {\n2:  	private String halfFull;\n3:  	private boolean halfEmpty;\n4:  	" +
                    "public static void main(String[] args){\n5:  	    Cup cup = new Cup();\n6:        System.out.print(\"Half empty = \" + cup.halfEmpty);\n7:        System.out.print(\", Half full = \" + " +
                    "cup.halfFull);\n8:  } }", "Half-empty = false, Half full = null", "Half empty = false, Half full =  ", "Half empty = null, Half full = null", "Compile error - line 6", "Compile error - line 7",
                    "None of the above", "c", "An instance variable defaults to a predetermined value base on its data type when called before being initialized. String defaults to null and boolean defaults to false.", 1));

            mQuestions.add(new QuestionEntity(2104, 0, "Select all lines that generate a compile error.\n4:  String apples = \"Delicious\";\n5:  short numApples = 9;\n6:  int weight = 12.4;\n7:  numApples.length();\n8:  " +
                    "weight.length();\n9:  apples.length();", "Line 4", "Line 5", "Line 6", "Line 7", "Line 8", "Line 9", "cde", "C) Error: can not cast a floating-point data type into an int data type.\nD & E) Error: " +
                    "primitive types can not have methods called on them.", DIFF_EASY));


            // ---------------- Arrays 4100 -----------------------
            mQuestions.add(new QuestionEntity(4106, 0, "Given the following array, which statements evaluate to '&'?\n\nchar[] foo = {‘X’,’1’,’Y’,’2’,’Z,’&’};", "foo[6];", "foo[5];",
                    "foo[foo.length()];", "foo[foo.length()-1];", "Does not compile", "None of the above", "bd", "Key Points:\n - Array indices begin at 0\n - Array length begins at 1\n\n       index =   0   1   2   3   4  5\n " +
                    "char[] foo = {‘X’,’A’,’Y’,’B’,’Z,’&’};\n       length =   1   2   3   4   5  6\n\nfoo[6] tries to access index 6 which doesn’t exist so it will throw an ArrayIndexOutOfBoundsException\n " +
                    "foo[5] is correct\nfoo[foo.length()]  tries to access index 6 which doesn’t exist so it will throw an ArrayIndexOutOfBoundsException.\nfoo[foo.length() - 1] is correct\nDoes not compile is incorrect\n" +
                    "'None' is incorrect because two answers evaluate to &", DIFF_MED));


            mQuestions.add(new QuestionEntity(4107, 0, "Given the following array, which statements evaluate to '&'?\n\nchar[] foo = {‘X’,’1’,’Y’,’2’,’Z,’&’};", "foo[6];", "foo[5];",
                    "foo[foo.length()];", "foo[foo.length()-1];", "Does not compile", "None of the above", "bd", "Key Points:\n - Array indices begin at 0\n - Array length begins at 1\n\n       index =   0   1   2   3   4  5\n " +
                    "char[] foo = {‘X’,’A’,’Y’,’B’,’Z,’&’};\n       length =   1   2   3   4   5  6\n\nfoo[6] tries to access index 6 which doesn’t exist so it will throw an ArrayIndexOutOfBoundsException\n " +
                    "foo[5] is correct\nfoo[foo.length()]  tries to access index 6 which doesn’t exist so it will throw an ArrayIndexOutOfBoundsException.\nfoo[foo.length() - 1] is correct\nDoes not compile is incorrect\n" +
                    "'None' is incorrect because two answers evaluate to &", DIFF_MED));


            long[] x = TestGenerator.mDb.questionsDao().insertQuestions(mQuestions);
            Log.w(LOG_TAG, "insert count: " + x.length);


        }
        return true;
    }
}
