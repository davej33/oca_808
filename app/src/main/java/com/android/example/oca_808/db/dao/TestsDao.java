package com.android.example.oca_808.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.android.example.oca_808.db.entity.TestEntity;

/**
 * Created by charlotte on 12/5/17.
 */

@Dao
public interface TestsDao {

    @Insert
    long insertNewTest(TestEntity newTest);

    @Query("SELECT * FROM TestEntity WHERE id = :id")
    TestEntity fetchTest(int id);
}
