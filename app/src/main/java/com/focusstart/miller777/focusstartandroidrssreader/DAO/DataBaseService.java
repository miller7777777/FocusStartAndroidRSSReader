package com.focusstart.miller777.focusstartandroidrssreader.DAO;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.focusstart.miller777.focusstartandroidrssreader.App;

public class DataBaseService extends IntentService {

    public static final String ACTION_WRITETODB = "com.focusstart.miller777.focusstartandroidrssreader.DAO.writeToDB";
    public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
    String extraOut = "данные записаны в базу";
    private Context context;


    public DataBaseService() {
        super("DataBaseService");
        this.context = App.getContext();

    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

        }
    }

}
