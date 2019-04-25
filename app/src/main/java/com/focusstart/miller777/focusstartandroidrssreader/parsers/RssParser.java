package com.focusstart.miller777.focusstartandroidrssreader.parsers;

import com.focusstart.miller777.focusstartandroidrssreader.model.ItemModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RssParser {
    public RssParser(String rss) {

    }

    //Объект класса парсит строку и формирует список объектов ItemModel

    public List getRssItems() {

        List sampleList = new ArrayList<ItemModel>();
        sampleList.add(new ItemModel("test title1", new Date(), "test link1", "test description1"));
        sampleList.add(new ItemModel("test title2", new Date(), "test link2", "test description2"));
        sampleList.add(new ItemModel("test title3", new Date(), "test link3", "test description3"));

        return sampleList;
    }
}
