package com.example.admin.dictbooktest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by admin on 2017/10/17.
 */

public class WordsSQLiteOpenHelper extends SQLiteOpenHelper {
    /**建表语句*/
    private String CREATE_WORDS1 = "create table listWords(id Integer primary key autoincrement," +
            "isChinese text,key text,fy text,psE text,pronE text,psA text,pronA text,posAcceptation text,sent text)";
    private String CREATE_WORDS2 = "create table   newWords(id Integer primary key autoincrement," +
            "isChinese text,key text,fy text,psE text,pronE text,psA text,pronA text,posAcceptation text,sent text)";

    public WordsSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WORDS1);
        db.execSQL(CREATE_WORDS2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("drop if table exists Words");
       db.execSQL("drop if table exists  newWords");
        onCreate(db);

    }
}