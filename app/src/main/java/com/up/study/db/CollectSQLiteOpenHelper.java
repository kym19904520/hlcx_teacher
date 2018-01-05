package com.up.study.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MR.孔 on 2017/5/17.
 */
public class CollectSQLiteOpenHelper extends SQLiteOpenHelper{

    private static String name = "temp.db";
    private static Integer version = 1;

    public CollectSQLiteOpenHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table records(_id integer primary key autoincrement,paperName varchar(20),typeName varchar(20),paperId integer(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }
}
