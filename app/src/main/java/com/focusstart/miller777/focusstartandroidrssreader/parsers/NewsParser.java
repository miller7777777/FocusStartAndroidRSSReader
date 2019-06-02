package com.focusstart.miller777.focusstartandroidrssreader.parsers;
import android.util.Log;

import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelModel;
import com.focusstart.miller777.focusstartandroidrssreader.model.ItemModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class NewsParser {

    String channelLink;
    String newsText;
    List<ItemModel> items;
    ChannelModel channel;
    ItemModel newsItem;

    public NewsParser(String newsText) {

        this.newsText = newsText;
        items = new ArrayList<ItemModel>();
    }

    public List<ItemModel> getNewsItems() {



        return null;
    }
}
