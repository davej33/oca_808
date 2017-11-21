package com.android.example.oca_808.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.android.example.oca_808.db.entity.QuestionEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charlotte on 11/21/17.
 */

@Dao
public interface QuestionsDao {

    @Insert
    public long[] insertQuestions(ArrayList<QuestionEntity> questionEntityArrayList);

    @Query("SELECT * FROM QuestionEntity")
    public List<QuestionEntity> getQuestions();
}
