package com.android.example.oca_808.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by charlotte on 11/21/17.
 */

@Entity
//        (indices = {@Index(value = {"q_map_id"},
//        unique = true)})
public class QuestionEntity {

    @PrimaryKey (autoGenerate = true)
    public int _id;
    public int q_map_id;
    public int type; // single or multiple answer
    public String question;
    public String question_code;
    public String opt_a;
    public String opt_b;
    public String opt_c;
    public String opt_d;
    public String opt_e;
    public String opt_f;
    public String answer;
    public int status;
    public boolean saved;
    public String explanation;
    public int difficulty;

    public QuestionEntity(int qid, int type, String question, String question_code, String a, String b, String c, String d, String e, String f,
                          String answer, String explanation, int difficulty){
        q_map_id = qid;
        this.type = type;
        this.question = question;
        this.question_code = question_code;
        opt_a = a;
        opt_b = b;
        opt_c = c;
        opt_d = d;
        opt_e = e;
        opt_f = f;
        this.answer = answer;
        status = 0;
        saved = false;
        this.explanation = explanation;
        this.difficulty = difficulty;
    }


    public QuestionEntity(){}

    public int getType(){
        return type;
    }

    public void setType(int type){
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getA() {
        return opt_a;
    }

    public void setA(String a) {
        this.opt_a = a;
    }

    public String getB() {
        return opt_b;
    }

    public void setB(String b) {
        this.opt_b = b;
    }

    public String getC() {
        return opt_c;
    }

    public void setC(String c) {
        this.opt_c = c;
    }

    public String getD() {
        return opt_d;
    }

    public void setD(String d) {
        this.opt_d = d;
    }

    public String getE() {
        return opt_e;
    }

    public void setE(String e) {
        this.opt_e = e;
    }

    public String getF() {
        return opt_f;
    }

    public void setF(String f) {
        this.opt_f = f;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public int getDifficulty() {
        return difficulty;
    }

}
