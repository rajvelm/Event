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

    public static final String MEMBER_TABLE = "tblMembers";
    public static final String MCOL_1 = "MEMBER_ID";
    public static final String MCOL_2 = "MEMBER_NAME";
    public static final String MCOL_3 = "MEMBER_EMAIL";
    public static final String MCOL_4 = "PASSWORD";
    public static final String MCOL_5 = "STATUS";
    public static final String MCOL_6 = "ADMIN";
    public static final String MCOL_7 = "SIGNUP_DATE";
    public static final String MCOL_8 = "LAST_LOGIN";

    public static final String TAG = "Result in DB";
    public static SQLiteDatabase db;

    public static final String CREATE_TABLE="create table " + MEMBER_TABLE + "(MEMBER_ID INTEGER PRIMARY KEY AUTOINCREMENT, MEMBER_NAME TEXT, " +
        " MEMBER_EMAIL TEXT, PASSWORD TEXT, STATUS BOOLEAN NOT NULL CHECK (STATUS IN (0,1)), ADMIN BOOLEAN "+
        " NOT NULL CHECK (ADMIN IN (0,1)), SIGNUP_DATE DATETIME, LAST_LOGIN DATETIME)";


    public DatabaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,1);
    }

    /*@Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("helper","create table"+CREATE_TABLE);

        db.execSQL("create table " + MEMBER_TABLE + "(MEMBER_ID INTEGER PRIMARY KEY AUTOINCREMENT, MEMBER_NAME TEXT, " +
                " MEMBER_EMAIL TEXT, PASSWORD TEXT, STATUS BOOLEAN NOT NULL CHECK (STATUS IN (0,1)), ADMIN BOOLEAN "+
                " NOT NULL CHECK (ADMIN IN (0,1)), SIGNUP_DATE DATETIME, LAST_LOGIN DATETIME)");

        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, EVENT_NAME TEXT, CREATED_BY TEXT, RATING REAL, EVENT_URL TEXT, EVENT_DESC TEXT, EVENT_DATE DATETIME, VIDEO_ID TEXT, EVENT_TYPE TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + MEMBER_TABLE);
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }


    public boolean insertMemberData(String member_name,String member_email, String password, int status,int admin,
                              String signup_date, String last_login)
    {
        db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(MCOL_2, member_name);
        cv.put(MCOL_3, member_email);
        cv.put(MCOL_4, password);
        cv.put(MCOL_5, status);
        cv.put(MCOL_6, admin);
        cv.put(MCOL_7, signup_date);
        cv.put(MCOL_8, last_login);

        long result = db.insert(MEMBER_TABLE, null, cv);
        Log.i(TAG, "insert MEMBER Data: "+result);
        if(result == -1)
            return false;
        else
            return true;

    }

    public Cursor getMemberData()
    {
        db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+ MEMBER_TABLE,null);
        Log.d("DB Helper", "get member Data: "+res);
        return res;
    }

    public boolean updateMemberData(String member_id,String last_login)
    {
        db=this.getWritableDatabase();
        ContentValues updateContentValues =new ContentValues();
        updateContentValues.put(MCOL_1, member_id);
        updateContentValues.put(MCOL_8, last_login);
        db.update(MEMBER_TABLE, updateContentValues, "MEMBER_ID =?", new String[] {member_id});
        return true;
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
