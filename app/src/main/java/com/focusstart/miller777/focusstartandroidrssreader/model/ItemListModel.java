package com.focusstart.miller777.focusstartandroidrssreader.model;

import java.io.Serializable;
import java.util.List;

public class ItemListModel implements Serializable {

    private List<ItemModel> newsItems;

    public ItemListModel(List<ItemModel> newsItems) {
        this.newsItems = newsItems;
    }

    public List<ItemModel> getNewsItems() {
        return newsItems;
    }

    public void setNewsItems(List<ItemModel> newsItems) {
        this.newsItems = newsItems;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
