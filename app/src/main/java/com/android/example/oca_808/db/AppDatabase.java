package com.android.example.oca_808.db;

import android.arch.persistence.room.RoomDatabase;

import com.android.example.oca_808.db.dao.QuestionsDao;

/**
 * Created by charlotte on 11/21/17.
 */

public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase mDb;

    public abstract QuestionsDao questionsDao();


}
