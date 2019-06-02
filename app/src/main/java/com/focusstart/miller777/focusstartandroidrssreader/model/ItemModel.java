package com.focusstart.miller777.focusstartandroidrssreader.model;

import java.io.Serializable;

public class ItemModel implements Serializable {

    //Объект дата-класса: новость. Элемент RSS-ленты

    private String title;
    private String pubDate;
    private String link;
    private String description;
    private String downloadDate;
    private String channelLink;
    private String channelRssLink;

    public ItemModel(String title, String pubDate, String link, String description) {
        this.title = title;
        this.pubDate = pubDate;
        this.link = link;
        this.description = description;
    }

    public ItemModel(String title, String pubDate, String link, String description, String downloadDate, String channelLink) {
        this.title = title;
        this.pubDate = pubDate;
        this.link = link;
        this.description = description;
        this.downloadDate = downloadDate;
        this.channelLink = channelLink;
    }

    public ItemModel(String title, String pubDate, String link, String description, String downloadDate, String channelLink, String channelRssLink) {
        this.title = title;
        this.pubDate = pubDate;
        this.link = link;
        this.description = description;
        this.downloadDate = downloadDate;
        this.channelLink = channelLink;
        this.channelRssLink = channelRssLink;
    }

    public ItemModel() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownloadDate() {
        return downloadDate;
    }

    public void setDownloadDate(String downloadDate) {
        this.downloadDate = downloadDate;
    }

    public String getChannelLink() {
        return channelLink;
    }

    public void setChannelLink(String channelLink) {
        this.channelLink = channelLink;
    }

    public String getChannelRssLink() {
        return channelRssLink;
    }

    public void setChannelRssLink(String channelRssLink) {
        this.channelRssLink = channelRssLink;
    }

    @Override
    public String toString() {


        String result = this.getTitle() + "  ( " + this.getPubDate() + " )";

        return result;
    }
}
