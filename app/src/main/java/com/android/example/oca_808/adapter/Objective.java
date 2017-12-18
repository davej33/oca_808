package com.android.example.oca_808.adapter;

/**
 * Created by charlotte on 12/18/17.
 */

public class Objective {

    private int mObjectiveID;
    private boolean mSelected = true;
    private String mObjTitle;

    public Objective(int id, String title) {
        mObjectiveID = id;
        mObjTitle = title;
    }

    public void setSelected(boolean b) {
        mSelected = b;
    }

    public int getmObjectiveID() {
        return mObjectiveID;
    }

    public boolean ismSelected() {
        return mSelected;
    }

    public String getmObjTitle() {
        return mObjTitle;
    }
}
