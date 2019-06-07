package com.focusstart.miller777.focusstartandroidrssreader.activities;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.focusstart.miller777.focusstartandroidrssreader.R;

import java.io.File;

public class ItemViewActivity extends AppCompatActivity {

    private WebView webView;

    private static final String TAG = ItemViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        Log.d(TAG, "onCreate");






        webView = findViewById(R.id.item_web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        webView.getSettings().setAppCacheEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        webView.setWebChromeClient(new WebChromeClient());

//        Uri data = getIntent().getData();
        Uri data = Uri.parse(getIntent().getStringExtra("LINK"));
        webView.loadUrl(data.toString());
        File sdcard = Environment.getExternalStorageDirectory();
        String path = sdcard.getAbsolutePath() + File.separator ;
        webView.saveWebArchive(path + "test.mht");
//        webView.loadUrl("test.web");


    }
}
