package com.focusstart.miller777.focusstartandroidrssreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.focusstart.miller777.focusstartandroidrssreader.model.ItemModel;
import com.focusstart.miller777.focusstartandroidrssreader.net.DownloadService;
import com.focusstart.miller777.focusstartandroidrssreader.net.NetHelper;
import com.focusstart.miller777.focusstartandroidrssreader.parsers.RssParser;

import java.util.ArrayList;
import java.util.List;

public class ChannelListActivity extends AppCompatActivity {

    RecyclerView ChannelListecyclerView;
    EditText etRSSUrl;
    Button btnSubscribe;
    TextView label;
    String baseRssUrl;
    String rssText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);


        ChannelListecyclerView = findViewById(R.id.channelListRecyclerView);
        etRSSUrl = findViewById(R.id.et_rssURL);
        label = findViewById(R.id.channel_label);
        baseRssUrl = etRSSUrl.getText().toString();


        btnSubscribe = findViewById(R.id.btn_subscribe);
        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newsListActivityIntent = new Intent(ChannelListActivity.this, NewsListActivity.class);
                newsListActivityIntent.putExtra("CHANNEL_RSS_URL", baseRssUrl);
                startActivity(newsListActivityIntent);
            }
        });
    }
}
