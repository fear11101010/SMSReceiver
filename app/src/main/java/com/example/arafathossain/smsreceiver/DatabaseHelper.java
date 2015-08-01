package com.example.arafathossain.smsreceiver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Arafat Hossain on 7/31/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sms";
    private static final String TABLE_NAME = "sms_detail";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FROM = "sender";
    public static final String COLUMN_BODY = "body";
    public static final String COLUMN_TIME = "time";
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUMN_FROM+" TEXT,"+COLUMN_BODY+" TEXT,"+COLUMN_TIME+" TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(CREATE_TABLE_QUERY);
    }
    public void insertSMS(ContentValues values){
        SQLiteDatabase database = getWritableDatabase();
        database.insert(TABLE_NAME, null, values);
    }
    public void deleteSMS(String id){
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{id});
    }
    public Cursor getAllSMS(){
        SQLiteDatabase database = getReadableDatabase();
        return database.query(TABLE_NAME,null,null,null,null,null,null);
    }
    public void deleteAllSMS(){
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_NAME, null, null);
    }
}
