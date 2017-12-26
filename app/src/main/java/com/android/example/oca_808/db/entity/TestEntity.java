package com.android.example.oca_808.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by charlotte on 11/21/17.
 */

@Entity
public class TestEntity {

    @PrimaryKey(autoGenerate = true)
    public int _id;
    public String title;
    public int type; // 0 = practice, 1 = test
    public String questionSet;
    public String answerSet;
    public String questionElapsedTimeSet;
    public boolean complete; // 0 = in-progress, 1 = complete
    public int progress;
    public long startDateTime;
    public long endDateTime;
    public long elapsedTestTime;
    public long elapsedQuestionTime;
    public int resumeQuestionNum;
    public int questionCount;
    public int sessionCount;
    public String mMarkedQuestionSet;

    public TestEntity(int type, String title, String questionSet, String answerSet, String questionElapsedTimeSet,
                      boolean complete, int progress, long startDateTime, long endDateTime, long elapsedTestTime,
                      long elapsedQuestionTime, int resumeQuestionNum, int questionCount, int sessionCount) {
        this.type = type;
        this.title = title;
        this.complete = complete;
        this.progress = progress;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.elapsedTestTime = elapsedTestTime;
        this.elapsedQuestionTime = elapsedQuestionTime;
        this.resumeQuestionNum = resumeQuestionNum;
        this.questionSet = questionSet;
        this.answerSet = answerSet;
        this.questionElapsedTimeSet = questionElapsedTimeSet;
        this.questionCount = questionCount;
        this.sessionCount = sessionCount;
        mMarkedQuestionSet = answerSet;
    }

    public String getAnswerSet() {
        return answerSet;
    }

    public void setAnswerSet(String answerSet) {
        this.answerSet = answerSet;
    }

    public void setElapsedQuestionTime(long elapsedQuestionTime) {
        this.elapsedQuestionTime = elapsedQuestionTime;
    }

    public void setElapsedTestTime(long elapsedTestTime) {
        this.elapsedTestTime = elapsedTestTime;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public void setResumeQuestionNum(int resumeQuestionNum) {
        this.resumeQuestionNum = resumeQuestionNum;
    }

    public void setSessionCount(int sessionCount) {
        this.sessionCount = sessionCount;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setMarkedQuestionsSet(String markedQuestionSet){
        this.mMarkedQuestionSet = markedQuestionSet;
    }
}
