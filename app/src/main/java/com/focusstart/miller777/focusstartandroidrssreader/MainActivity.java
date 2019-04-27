package com.focusstart.miller777.focusstartandroidrssreader;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.focusstart.miller777.focusstartandroidrssreader.DAO.DBHelper;
import com.focusstart.miller777.focusstartandroidrssreader.model.ItemModel;
import com.focusstart.miller777.focusstartandroidrssreader.net.DownloadService;
import com.focusstart.miller777.focusstartandroidrssreader.net.NetHelper;
import com.focusstart.miller777.focusstartandroidrssreader.parsers.RssParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText etRSSUrl;
    Button btnFetchRss;
    TextView label;
    String baseRssUrl;
    List rssItems;
    Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appContext = getApplicationContext();


        recyclerView = findViewById(R.id.recyclerview);
        etRSSUrl = findViewById(R.id.et_rssURL);
        label = findViewById(R.id.label);
        btnFetchRss = findViewById(R.id.btn_fetchRss);
        btnFetchRss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseRssUrl = etRSSUrl.getText().toString();
                Log.d("TAG", "Кнопка нажата");
                fetchData();

            }
        });



    }

    private void fetchData() {
        rssItems = new ArrayList<ItemModel>();

        //запрашиваем из сети список ItemModel
        NetHelper netHelper = new NetHelper(baseRssUrl, appContext);
        Log.d("TAG", "netHelper создан");
        Log.d("TAG", "baseUrl = " + baseRssUrl);

        String rss = netHelper.getRss();
        Log.d("TAG", "Какие-то данные от netHelper получены");


//        //парсим результат
//        RssParser parser = new RssParser(rss);
//        rssItems = parser.getRssItems();
//
//        //Записываем данные в базу
//        DBHelper dbHelper = new DBHelper();
//        dbHelper.write(rssItems);
//
//        //показывам данные
//        showData(rssItems);
    }

    private void showData(List rssItems) {
        //вывод данных из базы в RecyclerView
    }
}
