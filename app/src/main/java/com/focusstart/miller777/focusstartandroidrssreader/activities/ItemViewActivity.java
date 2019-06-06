package com.focusstart.miller777.focusstartandroidrssreader.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.focusstart.miller777.focusstartandroidrssreader.R;

public class ItemViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        webView = findViewById(R.id.item_web_view);
        webView.setWebViewClient(new WebViewClient());
        Uri data = getIntent().getData();
        webView.loadUrl(data.toString());

    }
}
