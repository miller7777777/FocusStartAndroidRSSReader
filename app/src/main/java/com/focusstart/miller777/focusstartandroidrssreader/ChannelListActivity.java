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
    Button btnFetchRss;
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

//        btnFetchRss = findViewById(R.id.btn_fetchRss);
//        btnFetchRss.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                baseRssUrl = etRSSUrl.getText().toString();
//                Log.d("TAG", "Кнопка нажата");
//                fetchData();
//
//            }
//        });

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



//    private void fetchData() {
//        rssItems = new ArrayList<ItemModel>();
//
//        //запрашиваем из сети список ItemModel
//        NetHelper netHelper = new NetHelper(baseRssUrl);
////        Log.d("TAG", "netHelper создан");
////        Log.d("TAG", "baseUrl = " + baseRssUrl);
//        netHelper.processRss();
//        Toast.makeText(ChannelListActivity.this, rssText, Toast.LENGTH_LONG).show(); //Для отладки
//
//
//
//
//
//
//
//
//
//
////
////        //Записываем данные в базу
////        DBHelper dbHelper = new DBHelper();
////        dbHelper.write(rssItems);
////
////        //показывам данные
////        showData(rssItems);
//    }

//    private void showData(List rssItems) {
//        //вывод данных из базы в RecyclerView
//    }

//    private class DownloadServiceReceiver extends BroadcastReceiver {
//
//        public String result;
//        public List <ItemModel> rssItems;
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            result = intent.getStringExtra(DownloadService.EXTRA_KEY_OUT);
//
//            rssText = result;
//
//            //парсим результат
//            RssParser parser = new RssParser(rssText);
//            rssItems = parser.getRssItems();
//
//
//
//        }
//    }
}
