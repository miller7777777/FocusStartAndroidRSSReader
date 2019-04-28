package com.focusstart.miller777.focusstartandroidrssreader.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;


public class NetHelper {
    //Объект класса загружает RSS ленту

    private String baseRssUrl;
    private Context context;




    public NetHelper(String baseRssUrl, Context context) {

        this.baseRssUrl = baseRssUrl;
        this.context = context;


    }


    public String getRss() {
        //пока замокаем вывод




        Intent intentDownloadService = new Intent(context, DownloadService.class);
        intentDownloadService.putExtra("baseUrl", baseRssUrl);
        context.startService(intentDownloadService);


        DownloadServiceReceiver receiver = new DownloadServiceReceiver();

        IntentFilter intentFilter = new IntentFilter(
                DownloadService.ACTION_DOWNLOADSERVICE
        );
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        context.registerReceiver(receiver, intentFilter);






        return null;
    }

    private class DownloadServiceReceiver extends BroadcastReceiver{

        public String result;

        @Override
        public void onReceive(Context context, Intent intent) {
            result = intent.getStringExtra(DownloadService.EXTRA_KEY_OUT);
            Log.d("TAG", "NetHelper: result = " + result);
        }
    }
}
