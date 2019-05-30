package com.focusstart.miller777.focusstartandroidrssreader.DAO;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.focusstart.miller777.focusstartandroidrssreader.apps.App;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelListModel;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseService extends IntentService {

    public static final String ACTION_WRITETODB = "com.focusstart.miller777.focusstartandroidrssreader.DAO.writeToDB";
    public static final String ACTION_READ_CHANNELS_FROM_DB = "com.focusstart.miller777.focusstartandroidrssreader.DAO.readChannelsFromDB";
    public static final String ACTION_SEND_LIST_OF_CHANNELS = "com.focusstart.miller777.focusstartandroidrssreader.DAO.SEND_LIST_OF_CHANNELS";
    public static final String ACTION_DELETE_CHANNELS_FROM_DB_BY_LINK = "com.focusstart.miller777.focusstartandroidrssreader.DAO.deleteChannelsFromDBByLink";
    public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
    public static final String EXTRA_KEY_OUT_SEND = "EXTRA_OUT_SEND";
    String extraOut = "данные записаны в базу";
    private Context context;
    private final String TAG = DataBaseService.class.getSimpleName();
    List<ChannelModel> channelList;


    public DataBaseService() {
        super("DataBaseService");
        this.context = App.getContext();

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            String action = intent.getStringExtra("ACTION");

            switch (action) {

                case (ACTION_WRITETODB):
                    writeToDB(intent);
                    break;

                case (ACTION_READ_CHANNELS_FROM_DB):
                    channelList = readChannelsFromDB();

                    //Посылаем BroadCast
                    Intent readFromDBIntent = new Intent();
                    readFromDBIntent.setAction(ACTION_SEND_LIST_OF_CHANNELS);
                    readFromDBIntent.addCategory(Intent.CATEGORY_DEFAULT);

                    ChannelListModel model = new ChannelListModel(channelList);
                    ArrayList<ChannelModel> testList = (ArrayList<ChannelModel>) model.getChannels();
                    readFromDBIntent.putExtra(EXTRA_KEY_OUT_SEND, model);
                    sendBroadcast(readFromDBIntent);
                    break;
                    
                case (ACTION_DELETE_CHANNELS_FROM_DB_BY_LINK):
                    deleteChannelByLink(intent);
            }

        }
    }

    private void deleteChannelByLink(Intent intent) {

        String channelLink = intent.getStringExtra("LINK");
        Log.d(TAG, "Получили линк канала: " + channelLink);

        DBChannelHelper dbChannelHelper = new DBChannelHelper();
        SQLiteDatabase db = dbChannelHelper.getWritableDatabase();

//        String SQL_COMMAND_DELETE_CHANNEL_BY_LINK = ChannelContract.ChannelEntry.COLUMN_LINK + " = "
//                + "\'"
//                + channelLink
//                + "\'";
//        Log.d(TAG, "SQL_COMMAND_DELETE_CHANNEL_BY_LINK = " + SQL_COMMAND_DELETE_CHANNEL_BY_LINK);


//        int delCount = db.delete(ChannelContract.ChannelEntry.TABLE_NAME, SQL_COMMAND_DELETE_CHANNEL_BY_LINK, null);
//        Log.d(TAG, "deleted rows count = " + delCount);
    }

    private List<ChannelModel> readChannelsFromDB() {

        DBChannelHelper dbChannelHelper = new DBChannelHelper();
        SQLiteDatabase db = dbChannelHelper.getWritableDatabase();
        channelList = new ArrayList<ChannelModel>();

        Cursor cursor = db.query(ChannelContract.ChannelEntry.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idColTitle = cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_TITLE);
            int idColLink = cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_LINK);
            int idColDescription = cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_DESCRIPTION);
            int idColLastBuildDate = cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_LASTBUILDDATE);

            do {
                String title = cursor.getString(idColTitle);
                String link = cursor.getString(idColLink);
                String description = cursor.getString(idColDescription);
                String lastBuildDate = cursor.getString(idColLastBuildDate);

                ChannelModel channel = new ChannelModel(title, lastBuildDate, link, description);
                channelList.add(channel);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return channelList;
    }

    private void writeToDB(Intent intent) {

        ChannelModel channel = (ChannelModel) intent.getSerializableExtra("channel");

        DBChannelHelper dbChannelHelper = new DBChannelHelper();
        SQLiteDatabase db = dbChannelHelper.getWritableDatabase();

        //Проверка: есть ли в базе канал с таким link
        if (!checkDBContainsChannelWithLink(db, channel.getLink())) {

            db.beginTransaction();

            ContentValues cv = new ContentValues();
            cv.put(ChannelContract.ChannelEntry.COLUMN_TITLE, channel.getTitle());
            cv.put(ChannelContract.ChannelEntry.COLUMN_LINK, channel.getLink());
            cv.put(ChannelContract.ChannelEntry.COLUMN_DESCRIPTION, channel.getDescription());
            cv.put(ChannelContract.ChannelEntry.COLUMN_LASTBUILDDATE, channel.getLastBuildDate());

            db.insert(ChannelContract.ChannelEntry.TABLE_NAME, null, cv);

            db.setTransactionSuccessful();
            db.endTransaction();

            db.close();
            dbChannelHelper.close();
        }
    }

    private boolean checkDBContainsChannelWithLink(SQLiteDatabase db, String title) {

        boolean result = false;

        Cursor c = db.query(ChannelContract.ChannelEntry.TABLE_NAME, null, null, null, null, null, null);

        int count = 0;

        if (c.moveToFirst()) {
            int linkColIndex = c.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_LINK);

            do {
                if (c.getString(linkColIndex).equalsIgnoreCase(title)) {
                    count++;
                }
            } while (c.moveToNext());
        }
        c.close();

        if (count > 0) {
            result = true;
        }
        return result;
    }
}
