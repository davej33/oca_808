package com.android.example.oca_808.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by charlotte on 11/21/17.
 */

@Entity
public class TestEntity {

    @PrimaryKey(autoGenerate = true)
    int id;
    public int type; // 0 = practice, 1 = test
    public boolean complete; // 0 = in-progress, 1 = complete
    public long date; // date last worked on
    public long elapsed_time_test;
    public String elapsed_time_question;
    public int paused_question;

    public TestEntity(int type, boolean complete, long date, long elapsed_time_test, String elapsed_time_question, int paused_question){
        this.type = type;
        this.complete = complete;
        this.date = date;
        this.elapsed_time_test = elapsed_time_test;
        this.elapsed_time_question = elapsed_time_question;
        this.paused_question = paused_question;
    }
}
