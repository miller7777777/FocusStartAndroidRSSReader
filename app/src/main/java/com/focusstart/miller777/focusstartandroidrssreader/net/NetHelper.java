package com.focusstart.miller777.focusstartandroidrssreader.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.focusstart.miller777.focusstartandroidrssreader.MainActivity;
import com.focusstart.miller777.focusstartandroidrssreader.model.ItemModel;
import com.focusstart.miller777.focusstartandroidrssreader.receivers.DownloadReceiver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NetHelper {
    //Объект класса загружает RSS ленту

    private String baseRssUrl;
    private Context context;
    private DownloadReceiver downloadReceiver;



    public NetHelper(String baseRssUrl, Context context) {

        this.baseRssUrl = baseRssUrl;
        this.context = context;


    }


    public String getRss() {
        //пока замокаем вывод

        downloadReceiver = new DownloadReceiver();


        Intent intentDownloadService = new Intent(context, DownloadService.class);
        intentDownloadService.putExtra("baseUrl", baseRssUrl);
        context.startService(intentDownloadService);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//        IntentFilter intentFilter = new IntentFilter(
//                DownloadService.ACTION_DOWNLOADSERVICE
//        );
//        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
//        context.registerReceiver(downloadServiceBroadcastReceiver, intentFilter);
        String answer = downloadReceiver.getResult();
        Log.d("TAG", "answer (от downloadReceiver) = " + answer);





        return null;
    }

    private class DownloadServiceReciver extends BroadcastReceiver{

        public String result;

        @Override
        public void onReceive(Context context, Intent intent) {
            result = intent.getStringExtra(DownloadService.EXTRA_KEY_OUT);
            Log.d("TAG", "NetHelper: result = " + result);
        }
    }
}
