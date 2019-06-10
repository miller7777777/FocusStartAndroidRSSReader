package com.focusstart.miller777.focusstartandroidrssreader.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.focusstart.miller777.focusstartandroidrssreader.DAO.DataBase;
import com.focusstart.miller777.focusstartandroidrssreader.DAO.DataBaseService;
import com.focusstart.miller777.focusstartandroidrssreader.R;
import com.focusstart.miller777.focusstartandroidrssreader.adapters.ChannelListAdapter;
import com.focusstart.miller777.focusstartandroidrssreader.apps.Constants;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelListModel;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelModel;
import com.focusstart.miller777.focusstartandroidrssreader.model.ItemModel;
import com.focusstart.miller777.focusstartandroidrssreader.net.DownloadService;
import com.focusstart.miller777.focusstartandroidrssreader.parsers.RssParser;
import java.util.ArrayList;
import java.util.List;

public class ChannelListActivity extends AppCompatActivity {

    private RecyclerView ChannelListRecyclerView;

    private String baseRssUrl;
    private ChannelListActivity.DownloadServiceReceiver downloadServiceReceiver;
    private DataBaseReceiver dataBaseReceiver;
    private ChannelModel channel;
    private List<ChannelModel> channels;
    private ChannelListAdapter channelListAdapter;

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
    }

    private void initView(RecyclerView channelListRecyclerView, List<ChannelModel> channels) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        channelListRecyclerView.setLayoutManager(layoutManager);
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
                Intent prefIntent = new Intent(this, PrefActivity.class);
                startActivity(prefIntent);
                return true;
            case R.id.action_add_channel:
                //Запуск AdChannelActivity
                Intent addChannelIntent = new Intent(this, AdChannelActivity.class);
                startActivity(addChannelIntent);
//                Toast.makeText(this, "Channel added!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_exit:

                while (Constants.downloadServiceOnProcess || Constants.databaseServiceOnProcess){

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                finish();
                System.exit(0);
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
        public ChannelModel channel;
        public List<ChannelModel> channels;
        public List<ItemModel> newsOfChannel;
        public String rssText;

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
        }
    }

    private class DataBaseReceiver extends BroadcastReceiver {

        public ChannelListModel model;
        public List<ChannelModel> channels;

        @Override
        public void onReceive(Context context, Intent intent) {

            model = (ChannelListModel) intent.getSerializableExtra(EXTRA_KEY_OUT_SEND);
            if (model != null) {
                channels = model.getChannels();
                initView(ChannelListRecyclerView, channels);
            }
        }
    }

    private void writeToDb(ChannelModel channel) {
        DataBase dataBase = new DataBase();
        dataBase.writeToDB(channel);
    }
}
