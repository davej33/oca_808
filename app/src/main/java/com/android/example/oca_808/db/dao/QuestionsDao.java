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
    long[] insertQuestions(ArrayList<QuestionEntity> questionEntityArrayList);

    @Query("SELECT * FROM QuestionEntity WHERE q_map_id IN (:list)")
    List<QuestionEntity> getQuestions(List<String> list);

    @Query("SELECT q_map_id FROM QuestionEntity")
    List<Integer> getQuestionIds();
}
