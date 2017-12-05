package com.android.example.oca_808;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.example.oca_808.db.AppDatabase;
import com.android.example.oca_808.db.entity.QuestionEntity;
import com.android.example.oca_808.db.entity.TestEntity;
import com.android.example.oca_808.view_model.QuestionsViewModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String LOG_TAG = HomeActivity.class.getSimpleName();
    ArrayList<QuestionEntity> mQuestions;
    private AppDatabase mDb;
    private long mStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDb = AppDatabase.getDb(this);
        addQs();
        ImageView i = findViewById(R.id.test_button);
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new QuestionsViewModel(getApplicationContext());
                startActivity(new Intent(getApplicationContext(), QuestionsActivity.class));
                createTestSim();

            }
        });
    }

    private void createTestSim() {

        // get questions
        List<Integer> questionList = mDb.questionsDao().getQuestionIds();
        String questionListString = questionList.toString();

        // create list for answers
        List<String> answerArrayList = new ArrayList<>(questionList.size()); // set ArrayList capacity to 70
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

    private void addQs() {
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

            long[] x = mDb.questionsDao().insertQuestions(mQuestions);
            Log.w(LOG_TAG, "insert count: " + x.length);

        }
    }


}
