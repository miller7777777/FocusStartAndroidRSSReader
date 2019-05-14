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


public class DataBase {
    //объект класса служит для записи и чтения данных в базу данных.
    public static final String TAG = DataBase.class.getSimpleName();

    private DBChannelHelper dbChannelHelper;
    private Context context;
//    private ChannelModel channel;

    public DataBase() {
        this.context = App.getContext();
    }

    public void writeToDB(ChannelModel channel) {

        Log.d(TAG, "Создаем интент для запуска сервиса");

        Intent intentWriteToDB = new Intent(App.getContext(), DataBaseService.class);
        intentWriteToDB.putExtra("channel", channel);
        App.getContext().startService(intentWriteToDB);
        Log.d(TAG, "Попытка стартовать сервис интентом");


    }

}
