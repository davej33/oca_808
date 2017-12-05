package com.android.example.oca_808.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.android.example.oca_808.db.dao.QuestionsDao;
import com.android.example.oca_808.db.dao.TestsDao;
import com.android.example.oca_808.db.entity.QuestionEntity;
import com.android.example.oca_808.db.entity.TestEntity;

import junit.framework.Test;

/**
 * Created by charlotte on 11/21/17.
 */

@Database(entities = {QuestionEntity.class, TestEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase mDb;

    public abstract QuestionsDao questionsDao();

    public abstract TestsDao testsDao();

    public static AppDatabase getDb(Context context){
        if(mDb == null){
            mDb = Room.databaseBuilder(context, AppDatabase.class, "questions-db").allowMainThreadQueries().build(); // TODO: fix main thread method
        }
        return mDb;
    }


}
