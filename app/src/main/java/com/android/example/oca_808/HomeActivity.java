package com.android.example.oca_808;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.example.oca_808.db.AppDatabase;
import com.android.example.oca_808.db.entity.QuestionEntity;
import com.android.example.oca_808.view_model.QuestionsViewModel;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ArrayList<QuestionEntity> mQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addQs();
        ImageView i = findViewById(R.id.test_button);
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new QuestionsViewModel(getApplicationContext());
                startActivity(new Intent(getApplicationContext(), QuestionsActivity.class));
            }
        });
    }

    private void addQs() {
        if (mQuestions == null) {
            mQuestions = new ArrayList<>();
            mQuestions.add(new QuestionEntity(1101, 11, 1, "1: What is \n2: Java? 1101", "A good cup of jo", "small mammal", "large lizard", "programming language", "no idea", "a fish", "d", "Just cuz"));
            mQuestions.add(new QuestionEntity(4101, 41, 0, "Given the following array, which statements evaluate to &?\n\nchar[] foo = {‘X’,’1’,’Y’,’2’,’Z,’&’};", "foo[6];", "foo[5];",
                    "foo[foo.length()];", "foo[foo.length()-1];", "Does not compile", "None of the above", "bd", "Key Points:\n - Array indices begin at 0\n - Array length begins at 1\n\n index =   0   1   2   3   4  5\n " +
                    "char[] foo = {‘X’,’A’,’Y’,’B’,’Z,’&’};\nlength =   1   2   3   4   5  6\n\nfoo[6] tries to access index 6 which doesn’t exist so it will throw an ArrayIndexOutOfBoundsException\n " +
                    "foo[5] is correct\nfoo[foo.length()]  tries to access index 6 which doesn’t exist so it will throw an ArrayIndexOutOfBoundsException.\nfoo[foo.length() - 1] is correct\nDoes not compile is incorrect\n" +
                    "'None' is incorrect because two answers evaluate to &"));
            mQuestions.add(new QuestionEntity(1102, 11, 1, "1: What is \n2: Java?", "A good cup of jo", "small mammal", "large lizard", "programming language", "no idea", "a fish", "d", "Just cuz"));


            AppDatabase db = AppDatabase.getDb(this);

            long[] x = db.questionsDao().insertQuestions(mQuestions);
            Log.w("Home", "insert count: " + x.length);

        }
    }


}
