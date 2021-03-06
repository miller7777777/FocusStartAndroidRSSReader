package com.focusstart.miller777.focusstartandroidrssreader.DAO;

import android.content.Context;
import android.content.Intent;
import com.focusstart.miller777.focusstartandroidrssreader.apps.App;
import com.focusstart.miller777.focusstartandroidrssreader.apps.Constants;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelModel;
import com.focusstart.miller777.focusstartandroidrssreader.model.ItemListModel;
import com.focusstart.miller777.focusstartandroidrssreader.model.ItemModel;

import java.util.List;


public class DataBase {
    //объект класса служит для записи и чтения данных в базу данных.
    public static final String ACTION_WRITETODB = "com.focusstart.miller777.focusstartandroidrssreader.DAO.writeToDB";
    public static final String ACTION_READ_CHANNELS_FROM_DB = "com.focusstart.miller777.focusstartandroidrssreader.DAO.readChannelsFromDB";
    public static final String ACTION_DELETE_CHANNELS_FROM_DB_BY_LINK = "com.focusstart.miller777.focusstartandroidrssreader.DAO.deleteChannelsFromDBByLink";

    private Context context;
    private ChannelModel channel;
    private List<ItemModel> newsOfChannel;

    public DataBase() {
        this.context = App.getContext();
    }

    public void writeToDB(ChannelModel channel) {

        Intent intentWriteToDB = new Intent(App.getContext(), DataBaseService.class);
        intentWriteToDB.putExtra("ACTION", ACTION_WRITETODB);
        intentWriteToDB.putExtra("channel", channel);
        App.getContext().startService(intentWriteToDB);
    }

    public void readChannelsFromDB() {

        Intent readFromDBIntent = new Intent(App.getContext(), DataBaseService.class);
        readFromDBIntent.putExtra("ACTION", ACTION_READ_CHANNELS_FROM_DB);
        App.getContext().startService(readFromDBIntent);
    }

    public void deleteChannelByLink(String itemLink) {

        Intent deleteChannelsFromDBIntent = new Intent(App.getContext(), DataBaseService.class);
        deleteChannelsFromDBIntent.putExtra("ACTION", ACTION_DELETE_CHANNELS_FROM_DB_BY_LINK);
        deleteChannelsFromDBIntent.putExtra("LINK", itemLink);
        App.getContext().startService(deleteChannelsFromDBIntent);
    }

    private void deleteAllNewsAtChannelByChannelLink(String itemLink) {

        Intent deleteNewsFromDBIntent = new Intent(App.getContext(), DataBaseService.class);
        deleteNewsFromDBIntent.putExtra("ACTION", Constants.ACTION_DELETE_NEWS_FROM_DB);
        deleteNewsFromDBIntent.putExtra("LINK", itemLink);
    }

    public void writeNewsOfChannelToDB(List<ItemModel> rssItems) {
        newsOfChannel = rssItems;

        ItemListModel newsListModel = new ItemListModel(newsOfChannel);
        Intent writeNewsToDBIntent = new Intent(App.getContext(), DataBaseService.class);
        writeNewsToDBIntent.putExtra("ACTION", Constants.ACTION_WRITE_NEWS_TO_DB);
        writeNewsToDBIntent.putExtra(Constants.EXTRA_OUT_NEWS_SEND_KEY, newsListModel);
        App.getContext().startService(writeNewsToDBIntent);
    }

    public void readNewsOfChannelByChannelLink(String channelLink) {

        Intent readNewsFromDBIntent = new Intent(App.getContext(), DataBaseService.class);
        readNewsFromDBIntent.putExtra("ACTION", Constants.ACTION_READ_NEWS_FROM_DB);
        readNewsFromDBIntent.putExtra("CHANNELLINK", channelLink);
        App.getContext().startService(readNewsFromDBIntent);
    }
}
