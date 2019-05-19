package com.focusstart.miller777.focusstartandroidrssreader;

import android.app.Activity;
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

import com.focusstart.miller777.focusstartandroidrssreader.DAO.DBChannelHelper;
import com.focusstart.miller777.focusstartandroidrssreader.DAO.DataBase;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelModel;
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
    Button btnReadFromDB;
    TextView label;
    String baseRssUrl;
    String rssText;
    ChannelListActivity.DownloadServiceReceiver receiver;
    ChannelModel channel;
    List<ChannelModel> channels;


    public static final String TAG = ChannelListActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);

        Log.d("TAG777", "Создана ChannelListActivity");


        channels = new ArrayList<ChannelModel>();

        ChannelListecyclerView = findViewById(R.id.channelListRecyclerView);
        etRSSUrl = findViewById(R.id.et_rssURL);
        label = findViewById(R.id.channel_label);


        //TODO: получаем из базы список каналов, заполняем RecyclerView.



        btnSubscribe = findViewById(R.id.btn_subscribe);
        btnReadFromDB = findViewById(R.id.btn_read_from_db);
        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG777", "кнопка нажата");
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

    private void readFromDB() {
        Toast.makeText(this, "Button clicked!", Toast.LENGTH_LONG).show();
        DataBase dataBase = new DataBase();
        Log.d("TAG777", "Создан объект DataBase");

        dataBase.readChannelsFromDB();
        Log.d("TAG777", "Вызван метод dataBase.readChannelsFromDB()");

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        receiver = new DownloadServiceReceiver();
        Log.d("TAG777", "Receiver создан");


        IntentFilter intentFilter = new IntentFilter(
                DownloadService.ACTION_DOWNLOADSERVICE
        );
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiver, intentFilter);
        Log.d(TAG, "Receiver зарегистрирован");

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        Log.d(TAG, "Receiver разрегистрирован");

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
            
//            writeToDb(channel);
            DataBase dataBase = new DataBase();
            dataBase.writeToDB(channel);


//            dataBase.readChannelsFromDB();

            Log.d("TAG777", "ChannelListActivity: onReceive(): channel = " + channel.toString());
            Toast.makeText(ChannelListActivity.this, channel.toString(), Toast.LENGTH_LONG).show(); //Для отладки

        }
    }

    private void writeToDb(ChannelModel channel) {
        DataBase dataBase = new DataBase();
        dataBase.writeToDB(channel);
    }
}
