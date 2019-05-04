package com.focusstart.miller777.focusstartandroidrssreader.parsers;

import android.util.Log;

import com.focusstart.miller777.focusstartandroidrssreader.model.ItemModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RssParser {
    String rss;
    List<ItemModel> items;

    public RssParser(String rss) {

        this.rss = rss;
        items = new ArrayList<ItemModel>();
        Log.d("TAG777", "Создан объект парсера ");

    }

    //Объект класса парсит строку и формирует список объектов ItemModel

    public List getRssItems() {


        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);

            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(rss));

            boolean insideItem = false;
            ItemModel itemModel = null;

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {


                    if (xpp.getName().equalsIgnoreCase("item")) {

                        itemModel = new ItemModel();
                        Log.d("TAG777", "Создан пустой item");

                        insideItem = true;

                    } else if (xpp.getName().equalsIgnoreCase("title")) {

                        if (insideItem) {

                            String title = xpp.nextText();
                            itemModel.setTitle(title);
                            Log.d("TAG777", "Title = " + title);


                        }
                    } else if (xpp.getName().equalsIgnoreCase("link")) {
                        if (insideItem) {

                            String link = xpp.nextText();
                            itemModel.setLink(link);
                            Log.d("TAG777", "Link = " + link);

                        }
                    } else if (xpp.getName().equalsIgnoreCase("description")) {
                        if (insideItem) {

                            String description = xpp.nextText();
                            itemModel.setDescription(description);
                            Log.d("TAG777", "Description = " + description);

                        }
                    } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                        if (insideItem) {

                            String pubDate = xpp.nextText();
                            itemModel.setPubDate(pubDate);
                            Log.d("TAG777", "PubDate = " + pubDate);
                            Log.d("TAG777", " ");
                            Log.d("TAG777", " ");
                            Log.d("TAG777", " ");
                        }
                    }

                } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                    insideItem = false;
                    items.add(itemModel);
                    Log.d("TAG777", "добавили item в List");
                }

                eventType = xpp.next();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}
