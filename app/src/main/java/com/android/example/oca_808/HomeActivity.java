package com.android.example.oca_808;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
    }

    private void addQs() {
        ArrayList<QuestionEntity> mQuestions = new ArrayList<>();
        mQuestions.add(new QuestionEntity(1, "What is Java?", "A good cup of jo", "small mammal", "large lizard", "programming language", "no idea",
                "a fish","e","Java Basics", 0, false));
        mQuestions.add(new QuestionEntity(0, "What else is Java?", "A programming language", "small mammal", "large lizard", "programming language", "no idea",
                "a fish","a,e","Java Basics", 0, false));
        AppDatabase db = AppDatabase.getDb(this);
        long[] x = db.questionsDao().insertQuestions(mQuestions);
        Log.w("Home", "insert count: " + x.length);
    }

    public void startTest(View view) {
        new QuestionsViewModel();
        startActivity(new Intent(this, QuestionsActivity.class));
        Toast.makeText(this, "does this run?", Toast.LENGTH_SHORT).show();

    }
}
