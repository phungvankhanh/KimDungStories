package com.example.admiralk.kimdungstories;

/**
 * Created by AdmiralK on 5/2/2017.
 */

public class ChapterOfStory {
    private int mStId;
    private int mDeId;
    private String mDecontent;
    private String mDename;

    public ChapterOfStory() {
    }

    public ChapterOfStory(String mDename) {
        this.mDename = mDename;
    }

    public int getmStId() {
        return mStId;
    }

    public void setmStId(int mStId) {
        this.mStId = mStId;
    }

    public int getmDeId() {
        return mDeId;
    }

    public void setmDeId(int mDeId) {
        this.mDeId = mDeId;
    }

    public String getmDecontent() {
        return mDecontent;
    }

    public void setmDecontent(String mDecontent) {
        this.mDecontent = mDecontent;
    }

    public String getmDename() {
        return mDename;
    }

    public void setmDename(String mDename) {
        this.mDename = mDename;
    }


}
