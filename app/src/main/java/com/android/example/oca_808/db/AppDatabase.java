package com.android.example.oca_808.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.android.example.oca_808.db.dao.QuestionsDao;
import com.android.example.oca_808.db.entity.QuestionEntity;

/**
 * Created by charlotte on 11/21/17.
 */

@Database(entities = QuestionEntity.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase mDb;

    public abstract QuestionsDao questionsDao();

    public static AppDatabase getDb(Context context){
        if(mDb == null){
            mDb = Room.databaseBuilder(context, AppDatabase.class, "questions-db").allowMainThreadQueries().build(); // TODO: fix main thread method
        }
        return mDb;
    }


}
