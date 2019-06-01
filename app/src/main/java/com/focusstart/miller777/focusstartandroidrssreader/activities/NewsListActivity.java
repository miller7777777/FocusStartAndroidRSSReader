package com.focusstart.miller777.focusstartandroidrssreader.activities;

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
import android.widget.Toast;

import com.focusstart.miller777.focusstartandroidrssreader.DAO.DataBase;
import com.focusstart.miller777.focusstartandroidrssreader.R;
import com.focusstart.miller777.focusstartandroidrssreader.model.ItemModel;
import com.focusstart.miller777.focusstartandroidrssreader.net.DownloadService;
import com.focusstart.miller777.focusstartandroidrssreader.net.NetHelper;
import com.focusstart.miller777.focusstartandroidrssreader.parsers.RssParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsListActivity extends AppCompatActivity {

    RecyclerView NewsListRecyclerView;
    Button btnFetchRss;
    DownloadServiceReceiver receiver;
    String channelLink;
    List rssItems;
    String rssText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        Intent activityIntent = getIntent();
        channelLink = activityIntent.getStringExtra("CHANNEL_RSS_URL");
        btnFetchRss = findViewById(R.id.btn_fetchRss);
        btnFetchRss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG777", "NewsListActivity: Кнопка нажата");
                fetchData();
            }
        });

        receiver = new NewsListActivity.DownloadServiceReceiver();

        IntentFilter intentFilter = new IntentFilter(
                DownloadService.ACTION_DOWNLOADSERVICE
        );
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void fetchData() {
        rssItems = new ArrayList<ItemModel>();

        //запрашиваем из сети список ItemModel
        NetHelper netHelper = new NetHelper(channelLink);
        netHelper.processRss();
    }

    private class DownloadServiceReceiver extends BroadcastReceiver {

        public String result;
        public List<ItemModel> rssItems;
        String rssText;

        @Override
        public void onReceive(Context context, Intent intent) {
            result = intent.getStringExtra(DownloadService.EXTRA_KEY_OUT);

            rssText = result;

            //парсим результат
            RssParser parser = new RssParser(rssText);
            try {
                rssItems = parser.getRssItems();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("TAG777", "NewsListActivity: onReceive(): rssItems.size() = " + rssItems.size());
            Toast.makeText(NewsListActivity.this, rssText, Toast.LENGTH_LONG).show(); //Для отладки

            if (rssItems != null && rssItems.size() > 0) {
                DataBase db = new DataBase();
                Date date = new Date();
                String downloadDate = date.toString();

                for (ItemModel rssItem : rssItems) {
                    rssItem.setChannelLink(channelLink);
                    rssItem.setDownloadDate(downloadDate);
                }
                db.writeNewsOfChannelToDB(rssItems);
            }

        }
    }
}
