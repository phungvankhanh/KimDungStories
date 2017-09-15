package com.example.admiralk.kimdungstories;

/**
 * Created by AdmiralK on 5/2/2017.
 */

public class Story {
    private int mId;
    private String mStName;
    private String mStDescribe;

    public int getmId() {
        return mId;
    }

    public Story(String mStName) {
        this.mStName = mStName;
    }

    public Story() {

    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmStName() {
        return mStName;
    }

    public void setmStName(String mStName) {
        this.mStName = mStName;
    }

    public String getmStDescribe() {
        return mStDescribe;
    }

    public void setmStDescribe(String mStDescribe) {
        this.mStDescribe = mStDescribe;
    }
}
