package com.focusstart.miller777.focusstartandroidrssreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.focusstart.miller777.focusstartandroidrssreader.DAO.DataBase;
import com.focusstart.miller777.focusstartandroidrssreader.DAO.DataBaseService;
import com.focusstart.miller777.focusstartandroidrssreader.adapters.ChannelListAdapter;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelListModel;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelModel;
import com.focusstart.miller777.focusstartandroidrssreader.net.DownloadService;
import com.focusstart.miller777.focusstartandroidrssreader.net.NetHelper;
import com.focusstart.miller777.focusstartandroidrssreader.parsers.RssParser;

import java.util.ArrayList;
import java.util.List;

public class ChannelListActivity extends AppCompatActivity {

    RecyclerView ChannelListRecyclerView;
    EditText etRSSUrl;
    Button btnSubscribe;
    Button btnReadFromDB;
    TextView label;
    String baseRssUrl;
    String rssText;
    ChannelListActivity.DownloadServiceReceiver downloadServiceReceiver;
    DataBaseReceiver dataBaseReceiver;
    ChannelModel channel;
    List<ChannelModel> channels;
    private ChannelListAdapter channelListAdapter;


    public static final String TAG = ChannelListActivity.class.getSimpleName();
    public static final String ACTION_SEND_LIST_OF_CHANNELS = "com.focusstart.miller777.focusstartandroidrssreader.DAO.SEND_LIST_OF_CHANNELS";
    public static final String EXTRA_KEY_OUT_SEND = "EXTRA_OUT_SEND";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);

        channels = new ArrayList<ChannelModel>();

        ChannelListRecyclerView = findViewById(R.id.channelListRecyclerView);
        etRSSUrl = findViewById(R.id.et_rssURL);
        label = findViewById(R.id.channel_label);

        //TODO: получаем из базы список каналов, заполняем RecyclerView.
        initView(ChannelListRecyclerView, channels);

        btnSubscribe = findViewById(R.id.btn_subscribe);
        btnReadFromDB = findViewById(R.id.btn_read_from_db);
        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseRssUrl = etRSSUrl.getText().toString();

                //TODO: Проверка на валидность RSS ленты

                //Здесь получаем информацию о канале;
                NetHelper netHelper = new NetHelper(baseRssUrl);
                netHelper.processRss();


//                  Здесь код пока закомментирован, он будет перенесен в oNItemClickListener()
                //TODO: Перенести интент в onItemClickListener

//                Intent newsListActivityIntent = new Intent(ChannelListActivity.this, NewsListActivity.class);
//                newsListActivityIntent.putExtra("CHANNEL_RSS_URL", baseRssUrl);
//                startActivity(newsListActivityIntent);
            }
        });

        btnReadFromDB.setOnClickListener(
                v -> readFromDB()
        );
    }

    private void initView(RecyclerView channelListRecyclerView, List<ChannelModel> channels) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        channelListRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(channelListRecyclerView.getContext(),
                layoutManager.getOrientation());

        channelListRecyclerView.addItemDecoration(dividerItemDecoration);


        channelListAdapter = new ChannelListAdapter(this, channels);
        channelListRecyclerView.setAdapter(channelListAdapter);

    }

    private void readFromDB() {
        DataBase dataBase = new DataBase();
        dataBase.readChannelsFromDB();
    }



    @Override
    protected void onPostResume() {
        super.onPostResume();

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

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(downloadServiceReceiver);
        unregisterReceiver(dataBaseReceiver);
    }

    private class DownloadServiceReceiver extends BroadcastReceiver {

        public String result;
        ChannelModel channel;
        List<ChannelModel> channels;
        String rssText;

        @Override
        public void onReceive(Context context, Intent intent) {
            result = intent.getStringExtra(DownloadService.EXTRA_KEY_OUT);

            rssText = result;
            channels = new ArrayList<ChannelModel>();

            //парсим результат
            RssParser parser = new RssParser(rssText);
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
