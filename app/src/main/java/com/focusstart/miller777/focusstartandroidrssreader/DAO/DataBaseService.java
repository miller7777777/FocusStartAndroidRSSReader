package com.focusstart.miller777.focusstartandroidrssreader.DAO;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.focusstart.miller777.focusstartandroidrssreader.App;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseService extends IntentService {

    public static final String ACTION_WRITETODB = "com.focusstart.miller777.focusstartandroidrssreader.DAO.writeToDB";
    public static final String ACTION_READ_CHANNELS_FROM_DB = "com.focusstart.miller777.focusstartandroidrssreader.DAO.readChannelsFromDB";
    public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
    String extraOut = "данные записаны в базу";
    private Context context;

    public static final String TAG = DataBaseService.class.getSimpleName();
    List<ChannelModel> channelList;


    public DataBaseService() {
        super("DataBaseService");
        this.context = App.getContext();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "DataBaseService создан");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            
            String action = intent.getStringExtra("ACTION");
            Log.d(TAG, "Пришел Intent с action = " + action);



            switch (action){

                case (ACTION_WRITETODB):
                    writeToDB(intent);
                    break;
                    
                case (ACTION_READ_CHANNELS_FROM_DB):
//                    channelList = new ArrayList<ChannelModel>();
                    channelList = readChannelsFromDB();
                    Log.d(TAG, "Попытка получить список каналов из базы");
                    Log.d(TAG, "Число записей в базе: " + channelList.size());
                    for (int i = 0; i < channelList.size(); i++) {
                        Log.d(TAG, "Канал: " + channelList.get(i).toString() + "\n");

                    }

                    break;
            }
           


        }
    }

    private List<ChannelModel> readChannelsFromDB() {

        Log.d(TAG, "Попытка чтения каналов из базы");

        DBChannelHelper dbChannelHelper = new DBChannelHelper();
        SQLiteDatabase db = dbChannelHelper.getWritableDatabase();
        channelList = new ArrayList<ChannelModel>();

        Cursor cursor = db.query(ChannelContract.ChannelEntry.TABLE_NAME, null, null, null, null, null, null);

        ///////////
        if (cursor.moveToFirst()){
            int idColTitle = cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_TITLE);
            int idColLink = cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_LINK);
            int idColDescription = cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_DESCRIPTION);
            int idColLastBuildDate = cursor.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_LASTBUILDDATE);


            do{
                String title = cursor.getString(idColTitle);
                String link = cursor.getString(idColLink);
                String description = cursor.getString(idColDescription);
                String lastBuildDate = cursor.getString(idColLastBuildDate);

                ChannelModel channel = new ChannelModel(title, lastBuildDate, link, description);
                channelList.add(channel);

            }while (cursor.moveToNext());
        }

        cursor.close();

        return channelList;
        ///////////

    }

    private void writeToDB(Intent intent) {
        
        ChannelModel channel = (ChannelModel) intent.getSerializableExtra("channel");
        Log.d(TAG, "Попытка получить из интента объект ChannelModel: " + channel.toString());


        Log.d(TAG, "Попытка записи в базу");

        DBChannelHelper dbChannelHelper = new DBChannelHelper();
        SQLiteDatabase db = dbChannelHelper.getWritableDatabase();

        //Проверка: есть ли в базе канал с таким link
        if (!checkDBContainsChannelWithLink(db, channel.getLink())){

            db.beginTransaction();

            ContentValues cv = new ContentValues();
            cv.put(ChannelContract.ChannelEntry.COLUMN_TITLE, channel.getTitle());
            cv.put(ChannelContract.ChannelEntry.COLUMN_LINK, channel.getLink());
            cv.put(ChannelContract.ChannelEntry.COLUMN_DESCRIPTION, channel.getDescription());
            cv.put(ChannelContract.ChannelEntry.COLUMN_LASTBUILDDATE, channel.getLastBuildDate());


            db.insert(ChannelContract.ChannelEntry.TABLE_NAME, null, cv);


            db.setTransactionSuccessful();
            db.endTransaction();

            Log.d(TAG, "Данные записаны");

            db.close();
            dbChannelHelper.close();
        }
    }

    private boolean checkDBContainsChannelWithLink(SQLiteDatabase db, String title) {

        boolean result = false;

        Log.d(TAG, "Создаем курсор");
//        Log.d(TAG, "channel.getLink() = " + channel.getLink());
        Log.d(TAG, "title = " + title);

        Cursor c = db.query(ChannelContract.ChannelEntry.TABLE_NAME, null, null, null, null, null, null);


        int count = 0;

        if(c.moveToFirst()){
            int linkColIndex = c.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_LINK);
            Log.d(TAG, "linkColIndex = " + linkColIndex);

            do{
                if(c.getString(linkColIndex).equalsIgnoreCase(title)){
                    count++;
                }
                Log.d(TAG, "c.getString(linkColIndex) = " + c.getString(linkColIndex));


            } while (c.moveToNext());
        }

        c.close();

        if (count > 0){
            result = true;
        }

        Log.d(TAG, "Count = : " + count);
        Log.d(TAG, "Канал с таким link есть в базе: " + result);


        return result;
    }

}
