package com.focusstart.miller777.focusstartandroidrssreader.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.focusstart.miller777.focusstartandroidrssreader.DAO.DataBase;
import com.focusstart.miller777.focusstartandroidrssreader.R;
import com.focusstart.miller777.focusstartandroidrssreader.adapters.NewsListAdapter;
import com.focusstart.miller777.focusstartandroidrssreader.apps.Constants;
import com.focusstart.miller777.focusstartandroidrssreader.model.ItemListModel;
import com.focusstart.miller777.focusstartandroidrssreader.model.ItemModel;
import com.focusstart.miller777.focusstartandroidrssreader.net.DownloadService;
import com.focusstart.miller777.focusstartandroidrssreader.net.NetHelper;
import com.focusstart.miller777.focusstartandroidrssreader.parsers.RssParser;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsListActivity extends AppCompatActivity {

    private static final String TAG = NewsListActivity.class.getSimpleName();

    private RecyclerView newsListRecyclerView;
    private NewsListAdapter newsListAdapter;
    private DownloadServiceReceiver receiver;
    private DataBaseServiceReceiver dataBaseServiceReceiver;
    private String channelLink;
    private String channelRssLink;
    private String channelTitle;
    private List<ItemModel> newsItems;
    private String rssText;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        Intent activityIntent = getIntent();
        channelLink = activityIntent.getStringExtra("CHANNEL_URL");
        channelRssLink = activityIntent.getStringExtra("CHANNEL_RSS_URL");
        channelTitle = activityIntent.getStringExtra(Constants.EXTRA_CHANNEL_TITLE_KEY);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(channelTitle);
        newsItems = new ArrayList<ItemModel>();

        newsListRecyclerView = findViewById(R.id.newsListRecyclerView);

        readNewsFromDBByChannelLink(channelLink);
        initView(newsListRecyclerView, newsItems);

        fetchData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initView(RecyclerView newsListRecyclerView, List<ItemModel> newsItems) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        newsListRecyclerView.setLayoutManager(layoutManager);

        newsListAdapter = new NewsListAdapter(this, newsItems);
        newsListRecyclerView.setAdapter(newsListAdapter);
    }

    private void readNewsFromDBByChannelLink(String channelLink) {

        DataBase db = new DataBase();
        db.readNewsOfChannelByChannelLink(channelLink);
    }

    @Override
    protected void onResume() {
        super.onResume();
        readNewsFromDBByChannelLink(channelLink);
        initView(newsListRecyclerView, newsItems);

        receiver = new NewsListActivity.DownloadServiceReceiver();

        IntentFilter intentFilter = new IntentFilter(
                DownloadService.ACTION_DOWNLOADSERVICE
        );
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiver, intentFilter);

        dataBaseServiceReceiver = new DataBaseServiceReceiver();

        IntentFilter dataBaseIntentFilter = new IntentFilter(Constants.ACTION_SEND_LIST_OF_NEWS);
        dataBaseIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(dataBaseServiceReceiver, dataBaseIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        unregisterReceiver(dataBaseServiceReceiver);
    }

    private void fetchData() {
        newsItems = new ArrayList<ItemModel>();

        NetHelper netHelper = new NetHelper(channelRssLink);
        netHelper.processRss();
    }

    private class DownloadServiceReceiver extends BroadcastReceiver {

        public String result;
        public List<ItemModel> newsItems;
        String newsText;

        @Override
        public void onReceive(Context context, Intent intent) {
            result = intent.getStringExtra(DownloadService.EXTRA_KEY_OUT);

            newsText = result;

            //парсим результат
            RssParser parser = new RssParser(newsText, channelLink, channelRssLink);

            newsItems = parser.getRssItems();

            initView(newsListRecyclerView, newsItems);

            if (newsItems != null && newsItems.size() > 0) {
                DataBase db = new DataBase();
                db.writeNewsOfChannelToDB(newsItems);
            }
            initView(newsListRecyclerView, newsItems);
        }
    }

    private class DataBaseServiceReceiver extends BroadcastReceiver {

        public ItemListModel itemListModel;
        public List<ItemModel> newsItems;

        @Override
        public void onReceive(Context context, Intent intent) {

            itemListModel = (ItemListModel) intent.getSerializableExtra(Constants.ACTION_SEND_NEWS_LIST_MODEL);
            if (itemListModel != null) {
                newsItems = itemListModel.getNewsItems();
                initView(newsListRecyclerView, newsItems);
            }
        }
    }
}
