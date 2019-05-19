package com.focusstart.miller777.focusstartandroidrssreader.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.focusstart.miller777.focusstartandroidrssreader.App;

public class DBChannelHelper extends SQLiteOpenHelper {

    private Context context = App.getContext();
    private static final String DATABASE_NAME = "channels.db";
    private static final int DATABASE_VERSION = 1;


    public DBChannelHelper() {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_CHANNELS_TABLE = "CREATE TABLE " + ChannelContract.ChannelEntry.TABLE_NAME + "("
                + ChannelContract.ChannelEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ChannelContract.ChannelEntry.COLUMN_TITLE + " TEXT, "
                + ChannelContract.ChannelEntry.COLUMN_LINK + " TEXT, "
                + ChannelContract.ChannelEntry.COLUMN_DESCRIPTION + " TEXT, "
                + ChannelContract.ChannelEntry.COLUMN_LASTBUILDDATE + " TEXT);";

        db.execSQL(SQL_CREATE_CHANNELS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
