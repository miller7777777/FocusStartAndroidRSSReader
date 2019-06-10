package com.focusstart.miller777.focusstartandroidrssreader.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.focusstart.miller777.focusstartandroidrssreader.apps.App;

public class DBChannelHelper extends SQLiteOpenHelper {

    private Context context = App.getContext();
    private static final String DATABASE_NAME = "channels.db";
    private static final int DATABASE_VERSION = 2;

    public DBChannelHelper() {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_CHANNELS_TABLE = "CREATE TABLE " + ChannelContract.ChannelEntry.TABLE_NAME + "("
                + ChannelContract.ChannelEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ChannelContract.ChannelEntry.COLUMN_TITLE + " TEXT, "
                + ChannelContract.ChannelEntry.COLUMN_LINK + " TEXT, "
                + ChannelContract.ChannelEntry.COLUMN_RSS_LINK + " TEXT, "
                + ChannelContract.ChannelEntry.COLUMN_DESCRIPTION + " TEXT, "
                + ChannelContract.ChannelEntry.COLUMN_LASTBUILDDATE + " TEXT);";

        String SQL_CREATE_NEWS_TABLE = "CREATE TABLE " + ChannelContract.ItemEntry.TABLE_NAME + "("
                + ChannelContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ChannelContract.ItemEntry.COLUMN_TITLE + " TEXT, "
                + ChannelContract.ItemEntry.COLUMN_LINK + " TEXT, "
                + ChannelContract.ItemEntry.COLUMN_DESCRIPTION + " TEXT, "
                + ChannelContract.ItemEntry.COLUMN_PUBDATE + " TEXT, "
                + ChannelContract.ItemEntry.COLUMN_DOWNLOAD_DATE + " INTEGER, "
                + ChannelContract.ItemEntry.COLUMN_CHANNEL_RSS_LINK + " TEXT, "
                + ChannelContract.ItemEntry.COLUMN_CHANNEL_LINK + " TEXT" + ");";


        db.execSQL(SQL_CREATE_CHANNELS_TABLE);
        db.execSQL(SQL_CREATE_NEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion == 1 && newVersion == 2){
            String SQL_CREATE_NEWS_TABLE = "CREATE TABLE " + ChannelContract.ItemEntry.TABLE_NAME + "("
                    + ChannelContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ChannelContract.ItemEntry.COLUMN_TITLE + " TEXT, "
                    + ChannelContract.ItemEntry.COLUMN_LINK + " TEXT, "
                    + ChannelContract.ItemEntry.COLUMN_DESCRIPTION + " TEXT, "
                    + ChannelContract.ItemEntry.COLUMN_PUBDATE + " TEXT, "
                    + ChannelContract.ItemEntry.COLUMN_DOWNLOAD_DATE + " INTEGER, "
                    + ChannelContract.ItemEntry.COLUMN_CHANNEL_LINK + " TEXT);";

            db.execSQL(SQL_CREATE_NEWS_TABLE);
        }
    }
}
