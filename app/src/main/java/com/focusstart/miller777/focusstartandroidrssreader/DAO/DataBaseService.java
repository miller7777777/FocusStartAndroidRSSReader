package com.focusstart.miller777.focusstartandroidrssreader.DAO;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.focusstart.miller777.focusstartandroidrssreader.apps.App;
import com.focusstart.miller777.focusstartandroidrssreader.apps.Constants;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelListModel;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelModel;
import com.focusstart.miller777.focusstartandroidrssreader.model.ItemListModel;
import com.focusstart.miller777.focusstartandroidrssreader.model.ItemModel;

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
                    readFromDBAndSendIntent();
                    break;

                case (ACTION_DELETE_CHANNELS_FROM_DB_BY_LINK):
                    deleteChannelByLink(intent);
                    readFromDBAndSendIntent();
                    break;
                case (Constants.ACTION_WRITE_NEWS_TO_DB):
                    writeNewsToDB(intent);
                    break;
            }

        }
    }

    private void writeNewsToDB(Intent intent) {

        ItemListModel itemListModel = (ItemListModel) intent.getSerializableExtra(Constants.EXTRA_OUT_NEWS_SEND_KEY);
        List<ItemModel> items = itemListModel.getNewsItems();
        Log.d(TAG, "Получили из интента: " + items.size());
        Log.d(TAG, "item.get(0).title: " + items.get(0).getTitle());

        DBChannelHelper dbChannelHelper = new DBChannelHelper();
        SQLiteDatabase db = dbChannelHelper.getWritableDatabase();

        for (ItemModel item : items) {

            if (!checkDBContainsNewsWithLink(db, item.getLink())) {
                //пишем новость в базу
                db.beginTransaction();

                ContentValues cv = new ContentValues();
                cv.put(ChannelContract.ItemEntry.COLUMN_TITLE, item.getTitle());
                cv.put(ChannelContract.ItemEntry.COLUMN_DESCRIPTION, item.getDescription());
                cv.put(ChannelContract.ItemEntry.COLUMN_LINK, item.getLink());
                cv.put(ChannelContract.ItemEntry.COLUMN_PUBDATE, item.getPubDate());
                cv.put(ChannelContract.ItemEntry.COLUMN_DOWNLOAD_DATE, item.getDownloadDate());
                cv.put(ChannelContract.ItemEntry.COLUMN_CHANNEL_LINK, item.getChannelLink());
                cv.put(ChannelContract.ItemEntry.COLUMN_CHANNEL_RSS_LINK, item.getChannelRssLink());

                db.insert(ChannelContract.ItemEntry.TABLE_NAME, null, cv);
                db.setTransactionSuccessful();
                db.endTransaction();
            }
        }

        db.close();
        dbChannelHelper.close();

    }

    private boolean checkDBContainsNewsWithLink(SQLiteDatabase db, String link) {
        //проверяем, есть ли новость с таким link в базе
        boolean result = false;

        Cursor c = db.query(ChannelContract.ItemEntry.TABLE_NAME, null, null, null, null, null, null);

        int count = 0;

        if (c.moveToFirst()) {
            int linkColIndex = c.getColumnIndex(ChannelContract.ItemEntry.COLUMN_LINK);

            do {
                if (c.getString(linkColIndex).equalsIgnoreCase(link)) {
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

    private void readFromDBAndSendIntent() {
        channelList = readChannelsFromDB();

        //Посылаем BroadCast
        Intent readFromDBIntent = new Intent();
        readFromDBIntent.setAction(ACTION_SEND_LIST_OF_CHANNELS);
        readFromDBIntent.addCategory(Intent.CATEGORY_DEFAULT);

        ChannelListModel model = new ChannelListModel(channelList);
        ArrayList<ChannelModel> testList = (ArrayList<ChannelModel>) model.getChannels(); //? Зачем?
        readFromDBIntent.putExtra(EXTRA_KEY_OUT_SEND, model);
        sendBroadcast(readFromDBIntent);
    }

    private void deleteChannelByLink(Intent intent) {

        String channelLink = intent.getStringExtra("LINK");
        Log.d(TAG, "Получили линк канала: " + channelLink);

        DBChannelHelper dbChannelHelper = new DBChannelHelper();
        SQLiteDatabase db = dbChannelHelper.getWritableDatabase();

        String SQL_COMMAND_DELETE_CHANNEL_BY_LINK = ChannelContract.ChannelEntry.COLUMN_LINK + " = "
                + "\'"
                + channelLink
                + "\'";
        Log.d(TAG, "SQL_COMMAND_DELETE_CHANNEL_BY_LINK = " + SQL_COMMAND_DELETE_CHANNEL_BY_LINK);


        int delCount = db.delete(ChannelContract.ChannelEntry.TABLE_NAME, SQL_COMMAND_DELETE_CHANNEL_BY_LINK, null);
        Log.d(TAG, "deleted rows count = " + delCount);

        db.close();
        dbChannelHelper.close();
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
            int idColRsslink = cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_RSS_LINK);

            do {
                String title = cursor.getString(idColTitle);
                String link = cursor.getString(idColLink);
                String description = cursor.getString(idColDescription);
                String lastBuildDate = cursor.getString(idColLastBuildDate);
                String rssLink = cursor.getString(idColRsslink);

                ChannelModel channel = new ChannelModel(title, lastBuildDate, link, description, rssLink);
                channelList.add(channel);

            } while (cursor.moveToNext());
        }

        db.close();
        dbChannelHelper.close();

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
            cv.put(ChannelContract.ChannelEntry.COLUMN_RSS_LINK, channel.getRssLink());

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
