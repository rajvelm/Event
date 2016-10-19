package com.example.lenovo.eventapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lenovo on 8/26/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "EventList.db";
    public static final String TABLE_NAME = "tblEvents";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "EVENT_NAME";
    public static final String COL_3 = "CREATED_BY";
    public static final String COL_4 = "RATING";
    public static final String COL_5 = "EVENT_URL";
    public static final String COL_6 = "EVENT_DESC";
    public static final String COL_7 = "EVENT_DATE";
    public static final String COL_8 = "VIDEO_ID";
    public static final String COL_9 = "EVENT_TYPE";


    public static final String TAG = "Result in DB";
    public static SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //db.execSQL("drop table tblEventList");
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, EVENT_NAME TEXT, CREATED_BY TEXT, RATING REAL, EVENT_URL TEXT, EVENT_DESC TEXT, EVENT_DATE DATETIME, VIDEO_ID TEXT, EVENT_TYPE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String event_name,String created_by, String event_url, String event_desc,String event_date,
                              String video_id, String event_type)
    {
        db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        String event_rating = "0" ;
        cv.put(COL_2, event_name);
        cv.put(COL_3, created_by);
        cv.put(COL_4,event_rating);
        cv.put(COL_5, event_url);
        cv.put(COL_6, event_desc);
        cv.put(COL_7, event_date);
        cv.put(COL_8, video_id);
        cv.put(COL_9, event_type);
        long result = db.insert(TABLE_NAME, null, cv);
        Log.i(TAG, "insertData: "+result);
        if(result == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllData()
    {
        db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+ TABLE_NAME,null);
        Log.d("DB Helper", "getAllData: "+res);
        return res;
    }

    public boolean updateData(String id,String event_name, String event_rating, String event_url)
    {
        db=this.getWritableDatabase();
        ContentValues updateContentValues =new ContentValues();
        updateContentValues.put(COL_1, id);
        updateContentValues.put(COL_2, event_name);
        updateContentValues.put(COL_4, event_rating);
        updateContentValues.put(COL_5, event_url);
        db.update(TABLE_NAME, updateContentValues, "ID =?", new String[] {id});
        return true;
    }

    public void close()
    {
        db.close();
    }
}
