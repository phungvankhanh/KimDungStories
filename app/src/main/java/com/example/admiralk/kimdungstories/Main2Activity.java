package com.example.admiralk.kimdungstories;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.admiralk.kimdungstories.Rule.checkValid;

public class Main2Activity extends AppCompatActivity {
    //TODO : Activity for chapter name
    List<ChapterOfStory> mChapters;
    List<ChapterOfStory> temp;
    DataBaseHelper dataBaseHelper;
    RecyclerView mRecyclerView;
    ChapterAdapter chapterAdapter;
    private RelativeLayout mRelativeLayout;
    private Handler mHandler;
    private Handler mHandler2;
    private Handler mHandler3;
    private Handler mHandler4;
    private Thread mThread2;
    private Typeface face;
    private Dialog openDialog;
    private TextView dialogTextContent;
    private CheckTuVung mCheckTuVung;
    private MenuItem menuCheck;
    private CheckTuVung checkTuVung;
    private volatile boolean flag = false;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_progress);
        mRelativeLayout.setVisibility(View.GONE);
        Bundle extrasData = getIntent().getExtras();
        String id2 = extrasData.getString("ID", "guest");
        System.out.println(id2);

        String nameStory = extrasData.getString("NAME", "guest");
        setTitle(nameStory);
        checkTuVung = new CheckTuVung();
        dataBaseHelper = null;
        try {
            dataBaseHelper = new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        id = Integer.parseInt(id2);
        mChapters = new ArrayList<>();
        temp = new ArrayList<>();
        mChapters = dataBaseHelper.getAllChapterOfStory(id);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_chapter);
        chapterAdapter = new ChapterAdapter(mChapters, this);
        mRecyclerView.setAdapter(chapterAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        for (int i = 0; i < mChapters.size(); i++) {
            temp.add(mChapters.get(i));
        }
        openDialog = new Dialog(this);
        openDialog.setContentView(R.layout.dialog_layout);
        openDialog.setTitle("Soát lỗi chính tả tiếng Việt");
        openDialog.setCanceledOnTouchOutside(false);

        dialogTextContent = (TextView) openDialog.findViewById(R.id.text_dialog);
        TextView dismissText = (TextView) openDialog.findViewById(R.id.text_cancel_dialog);
        final ProgressBar progressBar = (ProgressBar) openDialog.findViewById(R.id.progressbar_view);
        final RelativeLayout relativeLayout = (RelativeLayout) openDialog.findViewById(R.id.relative_view_dialog);
        relativeLayout.setVisibility(View.GONE);
        face = Typeface.createFromAsset(getAssets(), "fonts/SEGOEUI.TTF");
        dialogTextContent.setTypeface(face);
        dismissText.setTypeface(face);
        dismissText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog.dismiss();
            }
        });
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressBar.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.GONE);
                String res = "Số lỗi tìm được: " + Integer.toString(msg.arg1);
                dialogTextContent.setText(res);


                openDialog.show();

            }
        };

        mHandler2 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressBar.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
                openDialog.show();
            }
        };

        mHandler3 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mRelativeLayout.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                chapterAdapter.notifyDataSetChanged();
            }
        };

        mHandler4 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mRelativeLayout.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                chapterAdapter.notifyDataSetChanged();
            }
        };

        mThread2 = new Thread(new Runnable() {
            @Override
            public void run() {

                Message msg = mHandler2.obtainMessage();
                mHandler2.sendMessage(msg);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        MenuItem menuCheck = menu.findItem(R.id.icon_search_check);
        SearchView searchView = (SearchView) menuItem.getActionView();
        //menuCheck.setVisible(false);
        final ArrayList<ChapterOfStory> chapterOfStoriesSearch = new ArrayList<>();
        for (int i = 0; i < mChapters.size(); i++) {
            chapterOfStoriesSearch.add(mChapters.get(i));
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                final Thread thread = new Thread(new Runnable() {


                    @Override
                    public void run() {
                        SystemClock.sleep(1);
                        Message msg = mHandler3.obtainMessage();
                        mChapters.clear();
                        mHandler3.sendMessage(msg);
                        for (int i = 0; i < temp.size(); i++) {
                            if (dataBaseHelper.searchInDecontent(query, temp.get(i).getmDeId())) {
                                mChapters.add(temp.get(i));
                            }
                        }
                        SystemClock.sleep(1);
                        msg = mHandler4.obtainMessage();
                        mHandler4.sendMessage(msg);


                    }
                });
                thread.start();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public class SearchThread extends Thread {
        @Override
        public void run() {
            super.run();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_search_check:
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int countError = 0;

                        Arrays.sort(Rule.nguyenAm);
                        Arrays.sort(Rule.phuAmCuoi);
                        Arrays.sort(Rule.notPhuAmCuoi);
                        Arrays.sort(Rule.nguyenAmDacBiet);
                        ChapterOfStory chapter = new ChapterOfStory();
                        for (int j = 0; j < mChapters.size(); j++) {
                            chapter = dataBaseHelper.getDecontent(mChapters.get(j).getmDeId());
                            String temp = chapter.getmDecontent().replaceAll("<br/>", " ");
                            String paragraph2 = temp.replaceAll("</td>", " ");
                            String paragraph = paragraph2.replaceAll("-", " ");


                            String[] mang;
                            mang = paragraph.split(" ");
                            for (int i = 0; i < mang.length; i++) {
                                if (mang[i].compareTo("") != 0) {
                                    String tmp = mang[i].trim();
                                    if (tmp.length() != 0) {
                                        Xau key = new Xau(tmp);
                                        if (checkValid(key) == false) {
                                            countError++;
                                            System.out.println(mang[i]);

                                        }
                                    }
                                }

                            }

                            SystemClock.sleep(1);
                            Message msg = mHandler.obtainMessage();
                            msg.arg1 = countError;
                            mHandler.sendMessage(msg);

                        }

                        Message msg2 = mHandler2.obtainMessage();
                        mHandler2.sendMessage(msg2);

                    }
                });
                thread.start();
                break;
            case R.id.menu_search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
