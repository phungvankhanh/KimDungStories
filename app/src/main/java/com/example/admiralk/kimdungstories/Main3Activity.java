package com.example.admiralk.kimdungstories;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

public class Main3Activity extends AppCompatActivity {
    // TODO: Activity for chapter description
    DataBaseHelper dataBaseHelper;
    private TextView mTextView;
    Typeface face;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        dataBaseHelper = null;
        try {
            dataBaseHelper = new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bundle extrasData = getIntent().getExtras();
        String strDeId = extrasData.getString("DEID","guest");
        int iDeId = Integer.parseInt(strDeId);
        ChapterOfStory chapter = new ChapterOfStory();
        chapter = dataBaseHelper.getDecontent(iDeId);

        mTextView = (TextView) findViewById(R.id.text_view_decontent);
        face = Typeface.createFromAsset(getAssets(),"fonts/SEGOEUI.TTF");
        mTextView.setTypeface(face);
        Spanned spanned = Html.fromHtml(chapter.getmDecontent());
        mTextView.setText((String.valueOf(spanned)));


    }

}
