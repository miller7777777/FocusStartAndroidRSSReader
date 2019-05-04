package com.focusstart.miller777.focusstartandroidrssreader.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.List;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context, String name) {
        super(context, "rssReaderDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table items ("
                + "title text,"
                + "link text,"
                + "description text,"
                + "pubdate text"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
