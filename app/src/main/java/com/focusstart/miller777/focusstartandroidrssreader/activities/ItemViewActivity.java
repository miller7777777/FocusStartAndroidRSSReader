package com.focusstart.miller777.focusstartandroidrssreader.activities;

import android.annotation.TargetApi;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.focusstart.miller777.focusstartandroidrssreader.R;
import com.focusstart.miller777.focusstartandroidrssreader.net.NetHelper;
import com.focusstart.miller777.focusstartandroidrssreader.parsers.RssParser;

import java.io.File;

public class ItemViewActivity extends AppCompatActivity {

    private WebView webView;
    ActionBar actionBar;
    String itemTitle;

    private static final String TAG = ItemViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        Log.d(TAG, "onCreate");

        actionBar = getSupportActionBar();
//        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);




        webView = findViewById(R.id.item_web_view);
        webView.setWebViewClient(new NewsWebClient());
        webView.setWebChromeClient(new WebChromeClient());
////        Uri data = getIntent().getData();

        webView.getSettings().setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
        webView.getSettings().setAppCachePath( getApplicationContext().getCacheDir().getAbsolutePath() );
        webView.getSettings().setAllowFileAccess( true );
        webView.getSettings().setAppCacheEnabled( true );
        webView.getSettings().setJavaScriptEnabled( true );
        webView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default

        String itemLink = getIntent().getStringExtra("LINK");
        itemTitle = getIntent().getStringExtra("TITLE");
        actionBar.setTitle(itemTitle);
        Uri data = Uri.parse(itemLink);

        Log.d(TAG, "getApplicationContext().getCacheDir().getAbsolutePath() = " + getApplicationContext().getCacheDir().getAbsolutePath());
        Log.d(TAG, "Title = " + itemTitle);

//        webView.saveWebArchive("test.mht");

        NetHelper netHelper = new NetHelper();
        Boolean isNetworkAvailable = netHelper.isNetworkAvailable();

        Log.d(TAG, "isNetworkAvailable = " + isNetworkAvailable);


        if ( !isNetworkAvailable ) { // loading offline
            webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        webView.loadUrl(data.toString());
        //Вызываем парсер и сохраняем данные новости в БД, чтобы отображать в Off-line
        RssParser parser = new RssParser(itemLink);
        parser.saveNewsData();


    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

//    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }

    private class NewsWebClient extends WebViewClient {

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        // Для старых устройств
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
