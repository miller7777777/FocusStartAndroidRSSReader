package com.focusstart.miller777.focusstartandroidrssreader.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.focusstart.miller777.focusstartandroidrssreader.R;
import com.focusstart.miller777.focusstartandroidrssreader.net.NetHelper;

public class AdChannelActivity extends AppCompatActivity {

    EditText etRSSUrl;
    Button btnSubscribe;
    TextView label;
    String baseRssUrl;
    String rssText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_channel);


        etRSSUrl = findViewById(R.id.et_rssURL);
        label = findViewById(R.id.channel_label);


        btnSubscribe = findViewById(R.id.btn_subscribe);
        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseRssUrl = etRSSUrl.getText().toString();

                //TODO: Проверка на валидность RSS ленты

                //Здесь получаем информацию о канале;
                NetHelper netHelper = new NetHelper(baseRssUrl);
                netHelper.processRss();
            }
        });
    }
}
