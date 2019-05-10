package com.focusstart.miller777.focusstartandroidrssreader.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.focusstart.miller777.focusstartandroidrssreader.App;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelModel;
import com.focusstart.miller777.focusstartandroidrssreader.DAO.ChannelContract;


public class DataBase {
    //объект класса служит для записи и чтения данных в базу данных.

    private DBChannelHelper dbChannelHelper;
    private Context context;
    private ChannelModel channel;

    public DataBase() {
        this.context = App.getContext();
    }

    public void writeToDB(ChannelModel channel) {

        DBChannelHelper dbChannelHelper = new DBChannelHelper();
        SQLiteDatabase db = dbChannelHelper.getWritableDatabase();

        db.beginTransaction();

        ContentValues cv = new ContentValues();
        cv.put(ChannelContract.ChannelEntry.COLUMN_TITLE, channel.getTitle());
        cv.put(ChannelContract.ChannelEntry.COLUMN_LINK, channel.getLink());
        cv.put(ChannelContract.ChannelEntry.COLUMN_DESCRIPTION, channel.getDescription());
        cv.put(ChannelContract.ChannelEntry.COLUMN_LASTBUILDDATE, channel.getLastBuildDate());

//        int isUpdated = db.update(ChannelContract.ChannelEntry.TABLE_NAME,
////                cv,
////                ChannelContract.ChannelEntry.COLUMN_TITLE + " =?",
////                new String[] {channel.getTitle()});

        db.insert(ChannelContract.ChannelEntry.TABLE_NAME, null, cv);

//        if (isUpdated <= 0) {
//            long isInserted = db.insertWithOnConflict(ChannelContract.ChannelEntry.COLUMN_TITLE, null, cv,
//                    SQLiteDatabase.CONFLICT_REPLACE);
//        }

        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();
        dbChannelHelper.close();
    }
}
