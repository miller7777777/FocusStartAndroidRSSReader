package com.focusstart.miller777.focusstartandroidrssreader.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.focusstart.miller777.focusstartandroidrssreader.App;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelModel;
import com.focusstart.miller777.focusstartandroidrssreader.DAO.ChannelContract;

import java.util.List;


public class DataBase {
    //объект класса служит для записи и чтения данных в базу данных.
    public static final String ACTION_WRITETODB = "com.focusstart.miller777.focusstartandroidrssreader.DAO.writeToDB";
    public static final String ACTION_READ_CHANNELS_FROM_DB = "com.focusstart.miller777.focusstartandroidrssreader.DAO.readChannelsFromDB";

    public static final String TAG = DataBase.class.getSimpleName();

    private DBChannelHelper dbChannelHelper;
    private Context context;
//    private ChannelModel channel;

    public DataBase() {
        this.context = App.getContext();
        Log.d(TAG, "Объект DataBase создан");

    }

    public void writeToDB(ChannelModel channel) {

        Log.d(TAG, "Создаем интент для запуска сервиса");

        Intent intentWriteToDB = new Intent(App.getContext(), DataBaseService.class);
        intentWriteToDB.putExtra("ACTION", ACTION_WRITETODB);
        intentWriteToDB.putExtra("channel", channel);
        App.getContext().startService(intentWriteToDB);
        Log.d(TAG, "Попытка стартовать сервис интентом");


    }

    public void readChannelsFromDB() {

        Log.d(TAG, "Поймали вызов в методе readChannelsFromDB()");

        Intent readFromDBIntent = new Intent(App.getContext(), DataBaseService.class);
        readFromDBIntent.putExtra("ACTION", ACTION_READ_CHANNELS_FROM_DB);
        App.getContext().startService(readFromDBIntent);

        Log.d(TAG, "Попытка стартовать сервис интентом для чтения из базы");


    }
}
