package com.focusstart.miller777.focusstartandroidrssreader.net;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.focusstart.miller777.focusstartandroidrssreader.apps.App;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class NetHelper {
    //Объект класса загружает RSS ленту

    private String baseRssUrl;

    public NetHelper(String baseRssUrl) {

        this.baseRssUrl = baseRssUrl;
    }

    public NetHelper() {

    }


    public void processRss() {

        Intent intentDownloadService = new Intent(App.getContext(), DownloadService.class);
        intentDownloadService.putExtra("baseUrl", baseRssUrl);
        App.getContext().startService(intentDownloadService);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getContext().getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
