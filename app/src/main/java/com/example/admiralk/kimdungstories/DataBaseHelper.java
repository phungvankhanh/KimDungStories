package com.example.admiralk.kimdungstories;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by AdmiralK on 5/2/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private Context mycontext;
    //private String DB_PATH = mycontext.getApplicationContext().getPackageName()+"/databases/";
    private static String DB_NAME = "kimdung_ver4.sqlite"; //the extension may be .sqlite or .db
    public SQLiteDatabase myDataBase;
    private String DB_PATH = "/data/data/"
            + "com.example.admiralk.kimdungstories"
            + "/databases/";

    public DataBaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, 1);
        this.mycontext = context;
        boolean dbexist = checkdatabase();
        if (dbexist) {
            System.out.println("Database exists");
            opendatabase();
        } else {
            System.out.println("Database doesn't exist");
            createdatabase();
        }

    }

    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if (dbexist) {
            //System.out.println(" Database exists.");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkdatabase() {
        //SQLiteDatabase checkdb = null;
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            //checkdb = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }

        return checkdb;
    }

    private void copydatabase() throws IOException {

        //Open your local db as the input stream
        InputStream myinput = mycontext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();

    }

    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    public Story getStory(int id) {
        String SELECT_STORY_BY_ID = "SELECT * FROM kimdung WHERE stID = "
                + id;
        Cursor cursor = myDataBase.rawQuery(SELECT_STORY_BY_ID, null);
        if (cursor != null)
            cursor.moveToFirst();
        Story story = new Story();
        story.setmId(Integer.parseInt(cursor.getString(0)));
        story.setmStName(cursor.getString(1));
        story.setmStDescribe(cursor.getString(4));
        return story;
    }

    public List<Story> getAllStory() {
        List<Story> stories = new LinkedList<Story>();

        String query = "SELECT * FROM kimdung";

        Cursor cursor = myDataBase.rawQuery(query, null);
        Story story = null;
        if (cursor.moveToFirst()) {
            do {
                story = new Story();
                story.setmStName(cursor.getString(1));
                story.setmId(Integer.parseInt(cursor.getString(0)));
                story.setmStDescribe(cursor.getString(4));

                stories.add(story);
            } while (cursor.moveToNext());
        }
        Log.d("getAllStory()", stories.toString());
        return stories;
    }

    public List<ChapterOfStory> getAllChapterOfStory(int stId) {
        List<ChapterOfStory> chapters = new LinkedList<>();
        String strStID = Integer.toString(stId);
        String query = "SELECT deID,deName FROM st_kimdung WHERE stID = " + strStID;
        Cursor cursor = myDataBase.rawQuery(query, null);
        ChapterOfStory chapter = null;


        if (cursor.moveToFirst()) {
            do {
                chapter = new ChapterOfStory();
                chapter.setmDeId(Integer.parseInt(cursor.getString(0)));
                chapter.setmDename(cursor.getString(1));
                chapters.add(chapter);

            } while (cursor.moveToNext());
        }
        System.out.println("Chapter cursor: " + cursor.getCount());
        Log.d("getAllChapter()", chapters.toString());
        return chapters;
    }

    public ChapterOfStory getDecontent(int deID) {
        String decontent = new String();
        String strDeId = Integer.toString(deID);
        String query = "SELECT decontent FROM st_kimdung WHERE deID = "
                + strDeId;
        Cursor cursor = myDataBase.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        ChapterOfStory chapter = new ChapterOfStory();
        chapter.setmDecontent(cursor.getString(0));
        return chapter;
    }




    public boolean searchInDecontent(String string, int deId) {
        String[] strings = string.split(" ");
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (String s : strings) {
            if (!s.equals("")) {
                stringArrayList.add(s);
            }
        }

        String query = "SELECT deID FROM st_kimdung WHERE deID = " + deId + " AND decontent" +
                " LIKE '";
        for (int i = 0; i < stringArrayList.size(); i++) {
            query += "%" + stringArrayList.get(i);
        }
        query += "%'";
        System.out.println("query : " + query);
        Cursor cursor;
        cursor = myDataBase.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            System.out.println("cursor: " + cursor.getCount());
            return true;
        }
        //System.out.println("cursor: "+cursor.getCount());
        return false;
    }

}
