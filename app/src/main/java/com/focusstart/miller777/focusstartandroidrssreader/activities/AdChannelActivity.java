package com.focusstart.miller777.focusstartandroidrssreader.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.focusstart.miller777.focusstartandroidrssreader.DAO.DataBase;
import com.focusstart.miller777.focusstartandroidrssreader.R;
import com.focusstart.miller777.focusstartandroidrssreader.adapters.ChannelListAdapter;
import com.focusstart.miller777.focusstartandroidrssreader.apps.Constants;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelModel;
import com.focusstart.miller777.focusstartandroidrssreader.net.DownloadService;
import com.focusstart.miller777.focusstartandroidrssreader.net.NetHelper;
import com.focusstart.miller777.focusstartandroidrssreader.parsers.RssParser;
import java.util.ArrayList;
import java.util.List;

public class AdChannelActivity extends AppCompatActivity {

    private EditText etRSSUrl;
    private Button btnSubscribe;
    private TextView label;
    private String baseRssUrl;
    private String rssText;
    private DownloadServiceReceiver downloadServiceReceiver;
    private ChannelModel channel;
    private List<ChannelModel> channels;
    private ChannelListAdapter channelListAdapter;
    private ActionBar actionBar;
    private SharedPreferences sharedPreferences;
    private Boolean returnToChannelList;

    public static final String ACTION_SEND_LIST_OF_CHANNELS = "com.focusstart.miller777.focusstartandroidrssreader.DAO.SEND_LIST_OF_CHANNELS";
    public static final String EXTRA_KEY_OUT_SEND = "EXTRA_OUT_SEND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_channel);

        Intent intent = getIntent();
        Uri uri = intent.getData();

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.add_channel);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        channels = new ArrayList<ChannelModel>();

        etRSSUrl = findViewById(R.id.et_rssURL);
        label = findViewById(R.id.channel_label);

        if (uri != null) {
            etRSSUrl.setText(uri.toString());
        }

        btnSubscribe = findViewById(R.id.btn_subscribe);
        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseRssUrl = etRSSUrl.getText().toString().trim();

                btnSubscribe.setEnabled(false);

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

        returnToChannelList = sharedPreferences.getBoolean("return_to_channel_list", true);

        btnSubscribe.setEnabled(!Constants.downloadServiceOnProcess);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(downloadServiceReceiver);
    }

    private class DownloadServiceReceiver extends BroadcastReceiver {

        public String result;
        public ChannelModel channel;
        public List<ChannelModel> channels;
        public String rssText;

        @Override
        public void onReceive(Context context, Intent intent) {

            btnSubscribe.setEnabled(true);
            result = intent.getStringExtra(DownloadService.EXTRA_KEY_OUT);
            rssText = result;
            channels = new ArrayList<ChannelModel>();

            //парсим результат
            RssParser parser = new RssParser(rssText, baseRssUrl);
            channel = parser.getChannel();
            channels.add(channel);

            DataBase dataBase = new DataBase();
            dataBase.writeToDB(channel);

            if (returnToChannelList){
                onBackPressed();
            }
        }
    }
}
