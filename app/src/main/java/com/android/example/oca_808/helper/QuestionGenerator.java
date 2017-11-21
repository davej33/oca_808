package com.android.example.oca_808.helper;

import com.android.example.oca_808.db.entity.QuestionEntity;

import java.util.ArrayList;

/**
 * Created by charlotte on 11/21/17.
 */

public final class QuestionGenerator {

    private static ArrayList<QuestionEntity> mQuestions;

    public QuestionGenerator(){
        mQuestions = new ArrayList<>();
        mQuestions.add(new QuestionEntity(1, "What is Java?", "A good cup of jo", "small mammal", "large lizard", "programming language", "no idea",
                "a fish","e","Java Basics", 0, false));
        mQuestions.add(new QuestionEntity(0, "What else is Java?", "A programming language", "small mammal", "large lizard", "programming language", "no idea",
                "a fish","a,e","Java Basics", 0, false));


    }
}
