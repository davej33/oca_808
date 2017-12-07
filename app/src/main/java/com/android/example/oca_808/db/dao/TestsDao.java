package com.android.example.oca_808.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.android.example.oca_808.db.entity.TestEntity;

import java.util.List;

/**
 * Created by charlotte on 12/5/17.
 */

@Dao
public interface TestsDao {

    @Insert
    long insertNewTest(TestEntity newTest);

    @Query("SELECT * FROM TestEntity WHERE _id = :id")
    TestEntity fetchTest(int id);

    @Query("SELECT * FROM TestEntity WHERE type = :type AND complete = 'false'")
    List<TestEntity> fetchIncompleteTests(int type);



    @Update
    int updateTestResults(TestEntity... test);
}
