package com.android.example.oca_808.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by charlotte on 11/21/17.
 */

@Entity
public class QuestionEntity {

    @PrimaryKey(autoGenerate = true)
    int id;

    private int type; // single or multiple answer
    private String question;
    private String opt_a;
    private String opt_b;
    private String opt_c;
    private String opt_d;
    private String opt_e;
    private String opt_f;
    private String answer;
    private String objectives;
    private int status;
    private boolean saved;

    public QuestionEntity(int type, String question, String a, String b, String c, String d, String e, String f,
                          String answer, String objectives, int status, boolean saved){
        this.type =  type;
        this.question = question;
        this.opt_a = a;
        this.opt_b = b;
        this.opt_c = c;
        this.opt_d = d;
        this.opt_e = e;
        this.opt_f = f;
        this.answer = answer;
        this.objectives = objectives;
        this.status = status;
        this.saved = saved;
    }

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

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
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
}
