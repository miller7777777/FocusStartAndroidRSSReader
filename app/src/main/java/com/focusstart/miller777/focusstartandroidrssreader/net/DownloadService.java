package com.focusstart.miller777.focusstartandroidrssreader.net;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class DownloadService extends IntentService {

    public static final String ACTION_DOWNLOADSERVICE = "com.focusstart.miller777.focusstartandroidrssreader.net.RESPONCE";
    public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
    String extraOut = "данные загружены из сети";


    public DownloadService() {
        super("DownloadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG", "DownloadService created.");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String url = intent.getStringExtra("baseUrl");
        Log.d("TAG", "baseurl (service) = " + url);
        getData(url);
    }

    private String getData(String path) {
        BufferedReader reader = null;
        Log.d("TAG", "Получаем данные из сети");

        try {
            URL url = new URL(path);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Accept-Charset", "UTF-8");
            c.setReadTimeout(10000);
            c.connect();

            reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
            StringBuilder buf = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                buf.append(line + "\n");
            }

            String answer = buf.toString();
//            Log.d("TAG", "Данные = " + answer);

            Intent responseIntent = new Intent();
            responseIntent.setAction(ACTION_DOWNLOADSERVICE);
            responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
            responseIntent.putExtra(EXTRA_KEY_OUT, answer);
            sendBroadcast(responseIntent);

            reader.close();
            c.disconnect();


        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
