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
        ArrayList<QuestionEntity> mQuestions = new ArrayList<>();
        mQuestions.add(new QuestionEntity(1, "Question 1\n1: What is \n2: Java?", "A good cup of jo", "small mammal", "large lizard", "programming language", "no idea",
                "a fish","d","Java Basics", 0, false));
        mQuestions.add(new QuestionEntity(0, "Question 2\nWhat else is Java?", "A programming language", "jumbo", "bear", "programming language", "fudge",
                "a fish","ad","Java Basics", 0, false));
        mQuestions.add(new QuestionEntity(1, "Question 3\n1: what color is the sky?", "blue", "small mammal", "large lizard", "programming language", "no idea",
                "a fish","a","Java Basics", 0, false));
        mQuestions.add(new QuestionEntity(0, "Question 4\nWhat numbers are between 1 and 5?", "A programming language", "3", "bear", "programming language", "4",
                "a fish","be","Java Basics", 0, false));
        mQuestions.add(new QuestionEntity(1, "Question 5\nCubs World Series Championship Year?", "A good cup of jo", "small mammal", "large lizard", "programming language", "no idea",
                "2016","f","Java Basics", 0, false));
        mQuestions.add(new QuestionEntity(0, "Question 6\nFirst three letters of the alphabet?", "c", "jumbo", "bear", "programming language", "a",
                "b","aef","Java Basics", 0, false));
        AppDatabase db = AppDatabase.getDb(this);

        long[] x = db.questionsDao().insertQuestions(mQuestions);
        Log.w("Home", "insert count: " + x.length);

    }


}
