package com.focusstart.miller777.focusstartandroidrssreader.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.focusstart.miller777.focusstartandroidrssreader.net.DownloadService;

public class DownloadReceiver extends BroadcastReceiver {

    public String result;
    public Context context;

//    public DownloadReceiver(Context context) {
//        this.context = context;
//    }

    public String getResult() {
        return result;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        result = intent.getStringExtra(DownloadService.EXTRA_KEY_OUT);
        Log.d("TAG", "NetHelper: result = " + result);
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();

//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
