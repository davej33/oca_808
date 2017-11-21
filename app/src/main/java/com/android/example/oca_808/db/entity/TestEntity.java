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
    int type; // 0 = practice, 1 = test
    boolean complete; // 0 = in-progress, 1 = complete
    long date; // date last worked on
    long elapsed_time;
    String q_elapsed_time;
    int paused_question;

}
