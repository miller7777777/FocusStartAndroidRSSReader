package com.focusstart.miller777.focusstartandroidrssreader.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.focusstart.miller777.focusstartandroidrssreader.DAO.DataBase;
import com.focusstart.miller777.focusstartandroidrssreader.DAO.DataBaseService;
import com.focusstart.miller777.focusstartandroidrssreader.R;
import com.focusstart.miller777.focusstartandroidrssreader.adapters.ChannelListAdapter;
import com.focusstart.miller777.focusstartandroidrssreader.apps.Constants;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelListModel;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelModel;
import com.focusstart.miller777.focusstartandroidrssreader.model.ItemModel;
import com.focusstart.miller777.focusstartandroidrssreader.net.DownloadService;
import com.focusstart.miller777.focusstartandroidrssreader.net.NetHelper;
import com.focusstart.miller777.focusstartandroidrssreader.parsers.RssParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChannelListActivity extends AppCompatActivity {

    RecyclerView ChannelListRecyclerView;

    String baseRssUrl;
    ChannelListActivity.DownloadServiceReceiver downloadServiceReceiver;
    DataBaseReceiver dataBaseReceiver;
    ChannelModel channel;
    List<ChannelModel> channels;
    private ChannelListAdapter channelListAdapter;
    private File extStorageAppBasePath;
    private File externalStorageDir;
    private File extStorageAppCachePath;


    public static final String TAG = ChannelListActivity.class.getSimpleName();
    public static final String ACTION_SEND_LIST_OF_CHANNELS = "com.focusstart.miller777.focusstartandroidrssreader.DAO.SEND_LIST_OF_CHANNELS";
    public static final String EXTRA_KEY_OUT_SEND = "EXTRA_OUT_SEND";
    private static final String EXTRA_KEY_URL = "EXTRA_URL";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);



        channels = new ArrayList<ChannelModel>();

        ChannelListRecyclerView = findViewById(R.id.channelListRecyclerView);

        //TODO: получаем из базы список каналов, заполняем RecyclerView.
        readFromDB();
        initView(ChannelListRecyclerView, channels);


//        btnReadFromDB.setOnClickListener(
//                v -> readFromDB()
//        );
    }

    private void initView(RecyclerView channelListRecyclerView, List<ChannelModel> channels) {

//        readFromDB();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        channelListRecyclerView.setLayoutManager(layoutManager);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(channelListRecyclerView.getContext(),
//                layoutManager.getOrientation());
//
//        channelListRecyclerView.addItemDecoration(dividerItemDecoration);


        channelListAdapter = new ChannelListAdapter(this, channels);
        channelListRecyclerView.setAdapter(channelListAdapter);

    }

    private void readFromDB() {
        DataBase dataBase = new DataBase();
        dataBase.readChannelsFromDB();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_channel_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                //Обработка настроек
                Intent intent = new Intent(this, PrefActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_channel:
                //Запуск AdChannelActivity
                Intent prefIntent = new Intent(this, AdChannelActivity.class);
                startActivity(prefIntent);
//                Toast.makeText(this, "Channel added!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_exit:
                Log.d(TAG, "Нажали Exit");
                Log.d(TAG, "Нажали Exit: " + Constants.downloadServiceOnProcess);
                Log.d(TAG, "Нажали Exit: " + Constants.databaseServiceOnProcess);

                while (Constants.downloadServiceOnProcess || Constants.databaseServiceOnProcess){

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                finish();
                Log.d(TAG, "Вызвали finish()");

//                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                Log.d(TAG, "System.exit(0)");

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(downloadServiceReceiver);
        unregisterReceiver(dataBaseReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        readFromDB();
        initView(ChannelListRecyclerView, channels);
        downloadServiceReceiver = new DownloadServiceReceiver();

        IntentFilter intentFilter = new IntentFilter(
                DownloadService.ACTION_DOWNLOADSERVICE
        );
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(downloadServiceReceiver, intentFilter);

        dataBaseReceiver = new DataBaseReceiver();

        IntentFilter dataBaseSendIntentFilter = new IntentFilter(
                DataBaseService.ACTION_SEND_LIST_OF_CHANNELS
        );
        dataBaseSendIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(dataBaseReceiver, dataBaseSendIntentFilter);

    }

    private class DownloadServiceReceiver extends BroadcastReceiver {

        public String result;
        ChannelModel channel;
        List<ChannelModel> channels;
        List<ItemModel> newsOfChannel;
        String rssText;

        @Override
        public void onReceive(Context context, Intent intent) {
            result = intent.getStringExtra(DownloadService.EXTRA_KEY_OUT);
            baseRssUrl = intent.getStringExtra(DownloadService.EXTRA_KEY_URL);

            rssText = result;
            channels = new ArrayList<ChannelModel>();

            //парсим результат
            RssParser parser = new RssParser(rssText, baseRssUrl);
            channel = parser.getChannel();
            channels.add(channel);

            DataBase dataBase = new DataBase();
            dataBase.writeToDB(channel);
            initView(ChannelListRecyclerView, channels);


            Toast.makeText(ChannelListActivity.this, channel.toString(), Toast.LENGTH_LONG).show(); //Для отладки
        }
    }

    private class DataBaseReceiver extends BroadcastReceiver {

        ChannelListModel model;
        List<ChannelModel> channels;

        @Override
        public void onReceive(Context context, Intent intent) {

            model = (ChannelListModel) intent.getSerializableExtra(EXTRA_KEY_OUT_SEND);
            if (model != null) {
                channels = model.getChannels();
                initView(ChannelListRecyclerView, channels);
                Toast.makeText(ChannelListActivity.this, "Получено " + channels.size() + " каналов", Toast.LENGTH_LONG).show(); //Для отладки
            }
        }
    }

    private void writeToDb(ChannelModel channel) {
        DataBase dataBase = new DataBase();
        dataBase.writeToDB(channel);
    }
}
