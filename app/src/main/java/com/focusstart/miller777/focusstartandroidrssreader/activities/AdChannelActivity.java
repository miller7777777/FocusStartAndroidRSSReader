package com.focusstart.miller777.focusstartandroidrssreader.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.focusstart.miller777.focusstartandroidrssreader.R;

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
    }
}
