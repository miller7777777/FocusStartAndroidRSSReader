package com.focusstart.miller777.focusstartandroidrssreader.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.focusstart.miller777.focusstartandroidrssreader.DAO.ChannelContract;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "channels.db";
    private static final int DATABASE_VERSION = 1;


    public DBHelper(Context context, String name) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       String SQL_CREATE_CHANNELS_TABLE = "CREATE TABLE " + ChannelContract.ChannelEntry.TABLE_NAME + "("
               + ChannelContract.ChannelEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
               + ChannelContract.ChannelEntry.COLUMN_TITLE + " TEXT NOT NULL, "
               + ChannelContract.ChannelEntry.COLUMN_LINK + " TEXT NOT NULL, "
               + ChannelContract.ChannelEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, "
               + ChannelContract.ChannelEntry.COLUMN_LASTBUILDDATE + " TEXT NOT NULL);";

       db.execSQL(SQL_CREATE_CHANNELS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
