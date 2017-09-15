package com.example.admiralk.kimdungstories;

import android.app.Notification;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // TODO: Activity for story name
    private TextView mTextView;
    private Story mStory;
    private StoryAdapter mStoryAdapter;
    private List<Story> mStories;
    private RecyclerView mRecyclerView;
    public DataBaseHelper dataBaseHelper;
    private Button mButton;
    private Handler mHandler;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        //linearLayout = findViewById(R.id.l);
//        mHandler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//
//            }
//        };
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//        thread.start();
        dataBaseHelper = null;
        try {
            dataBaseHelper = new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //setTitleColor(4456);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_story);
        mStories = new ArrayList<>();

        mStories = dataBaseHelper.getAllStory();

        mStoryAdapter = new StoryAdapter(this, mStories);
        mRecyclerView.setAdapter(mStoryAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);



    }




//    @Override
//    public void mClick(View v, int position) {
//        Intent intent = new Intent(this,Main2Activity.class);
//        startActivity(intent);


}
