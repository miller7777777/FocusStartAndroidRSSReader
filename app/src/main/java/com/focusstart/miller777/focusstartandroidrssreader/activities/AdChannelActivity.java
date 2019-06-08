package com.focusstart.miller777.focusstartandroidrssreader.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.focusstart.miller777.focusstartandroidrssreader.DAO.DataBase;
import com.focusstart.miller777.focusstartandroidrssreader.R;
import com.focusstart.miller777.focusstartandroidrssreader.adapters.ChannelListAdapter;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelModel;
import com.focusstart.miller777.focusstartandroidrssreader.net.DownloadService;
import com.focusstart.miller777.focusstartandroidrssreader.net.NetHelper;
import com.focusstart.miller777.focusstartandroidrssreader.parsers.RssParser;

import java.util.ArrayList;
import java.util.List;

public class AdChannelActivity extends AppCompatActivity {

    EditText etRSSUrl;
    Button btnSubscribe;
    TextView label;
    String baseRssUrl;
    String rssText;
    DownloadServiceReceiver downloadServiceReceiver;
    ChannelModel channel;
    List<ChannelModel> channels;
    private ChannelListAdapter channelListAdapter;
    ActionBar actionBar;


    public static final String ACTION_SEND_LIST_OF_CHANNELS = "com.focusstart.miller777.focusstartandroidrssreader.DAO.SEND_LIST_OF_CHANNELS";
    public static final String EXTRA_KEY_OUT_SEND = "EXTRA_OUT_SEND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_channel);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.add_channel);

        channels = new ArrayList<ChannelModel>();



        etRSSUrl = findViewById(R.id.et_rssURL);
        label = findViewById(R.id.channel_label);


        btnSubscribe = findViewById(R.id.btn_subscribe);
        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseRssUrl = etRSSUrl.getText().toString().trim();

                //TODO: Проверка на валидность RSS ленты

                //Здесь получаем информацию о канале;
                NetHelper netHelper = new NetHelper(baseRssUrl);
                netHelper.processRss();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        downloadServiceReceiver = new DownloadServiceReceiver();

        IntentFilter intentFilter = new IntentFilter(
                DownloadService.ACTION_DOWNLOADSERVICE
        );
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(downloadServiceReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(downloadServiceReceiver);
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
            RssParser parser = new RssParser(rssText, baseRssUrl);
            channel = parser.getChannel();
            channels.add(channel);

            DataBase dataBase = new DataBase();
            dataBase.writeToDB(channel);
//            initView(ChannelListRecyclerView, channels);


//            Toast.makeText(AdChannelActivity.this, channel.toString(), Toast.LENGTH_LONG).show(); //Для отладки
            onBackPressed();
        }
    }
}
