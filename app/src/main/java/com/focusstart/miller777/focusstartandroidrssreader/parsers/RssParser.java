package com.focusstart.miller777.focusstartandroidrssreader.parsers;

import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelModel;
import com.focusstart.miller777.focusstartandroidrssreader.model.ItemModel;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RssParser {
    private String rss;
    private String channelUrl;
    private String channelRssUrl;
    private List<ItemModel> items;
    private ChannelModel channel;

    public RssParser(String rss) {

        this.rss = rss;
        items = new ArrayList<ItemModel>();
    }

    public RssParser(String rss, String channelUrl) {

        this.rss = rss;
        this.channelUrl = channelUrl;
        items = new ArrayList<ItemModel>();
    }

    public RssParser(String rss, String channelUrl, String channelRssUrl) {

        this.rss = rss;
        this.channelUrl = channelUrl;
        this.channelRssUrl = channelRssUrl;
        items = new ArrayList<ItemModel>();
    }

    //Объект класса парсит строку и формирует список объектов ItemModel

    public List<ItemModel> getRssItems() {

        long date = System.currentTimeMillis();
        long downloadDate = System.currentTimeMillis();

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
                        insideItem = true;

                    } else if (xpp.getName().equalsIgnoreCase("title")) {

                        if (insideItem) {

                            String title = xpp.nextText();
                            itemModel.setTitle(title);
                        }
                    } else if (xpp.getName().equalsIgnoreCase("link")) {
                        if (insideItem) {
                            String link = xpp.nextText();
                            itemModel.setLink(link);
                        }
                    } else if (xpp.getName().equalsIgnoreCase("description")) {
                        if (insideItem) {
                            String description = xpp.nextText();
                            itemModel.setDescription(description);
                        }
                    } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                        if (insideItem) {
                            String pubDate = xpp.nextText();
                            itemModel.setPubDate(pubDate);
                        }
                    }

                } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                    insideItem = false;
                    itemModel.setChannelLink(channelUrl);
                    itemModel.setChannelRssLink(channelRssUrl);
                    itemModel.setDownloadDate(downloadDate);
                    items.add(itemModel);
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    public ChannelModel getChannel() {

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);

            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(rss));

            boolean insideItem = false;

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {

                    if (xpp.getName().equalsIgnoreCase("channel")) {

                        channel = new ChannelModel("", "", "", "", channelUrl);
                        insideItem = true;

                    } else if (xpp.getName().equalsIgnoreCase("title")) {

                        if (insideItem && channel.getTitle().isEmpty()) {

                            String title = xpp.nextText();
                            channel.setTitle(title);
                        }
                    } else if (xpp.getName().equalsIgnoreCase("link")) {
                        if (insideItem && channel.getLink().isEmpty()) {

                            String link = xpp.nextText();
                            channel.setLink(link);
                        }
                    } else if (xpp.getName().equalsIgnoreCase("description")) {
                        if (insideItem && channel.getDescription().isEmpty()) {

                            String description = xpp.nextText();
                            channel.setDescription(description);
                        }
                    } else if (xpp.getName().equalsIgnoreCase("lastBuildDate")) {
                        if (insideItem && channel.getLastBuildDate().isEmpty()) {

                            String lastBuildDate = xpp.nextText();
                            channel.setLastBuildDate(lastBuildDate);
                            return channel;
                        }
                    }

                } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("channel")) {
                    insideItem = false;
                }

                eventType = xpp.next();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return channel;
    }
}
