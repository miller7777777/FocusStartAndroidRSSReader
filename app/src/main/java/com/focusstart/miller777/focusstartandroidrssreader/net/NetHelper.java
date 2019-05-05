package com.focusstart.miller777.focusstartandroidrssreader.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.focusstart.miller777.focusstartandroidrssreader.App;


public class NetHelper {
    //Объект класса загружает RSS ленту

    private String baseRssUrl;

    public NetHelper(String baseRssUrl) {

        this.baseRssUrl = baseRssUrl;
    }


    public void processRss() {

        Intent intentDownloadService = new Intent(App.getContext(), DownloadService.class);
        intentDownloadService.putExtra("baseUrl", baseRssUrl);
        App.getContext().startService(intentDownloadService);
    }
}
