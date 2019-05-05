package com.focusstart.miller777.focusstartandroidrssreader.model;

public class ChannelModel {

    private String title;
    private String lastBuildDate;
    private String link;
    private String description;

    public ChannelModel(String title, String lastBuildDate, String link, String description) {
        this.title = title;
        this.lastBuildDate = lastBuildDate;
        this.link = link;
        this.description = description;
    }

    public ChannelModel() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
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

    @Override
    public String toString() {
        return "ChannelModel{" +
                "title='" + title + '\'' +
                ", lastBuildDate='" + lastBuildDate + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
