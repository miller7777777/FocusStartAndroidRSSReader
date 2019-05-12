package com.focusstart.miller777.focusstartandroidrssreader.DAO;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.focusstart.miller777.focusstartandroidrssreader.App;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelModel;

public class DataBaseService extends IntentService {

    public static final String ACTION_WRITETODB = "com.focusstart.miller777.focusstartandroidrssreader.DAO.writeToDB";
    public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
    String extraOut = "данные записаны в базу";
    private Context context;

    public static final String TAG = DataBaseService.class.getSimpleName();


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


            ChannelModel channel = (ChannelModel) intent.getSerializableExtra("channel");
            Log.d(TAG, "Попытка получить из интента объект ChannelModel: " + channel.toString());

        }
    }

}
