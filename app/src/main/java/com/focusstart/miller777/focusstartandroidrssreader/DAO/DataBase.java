package com.focusstart.miller777.focusstartandroidrssreader.DAO;

import android.content.Context;
import android.content.Intent;
import com.focusstart.miller777.focusstartandroidrssreader.apps.App;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelModel;


public class DataBase {
    //объект класса служит для записи и чтения данных в базу данных.
    public static final String ACTION_WRITETODB = "com.focusstart.miller777.focusstartandroidrssreader.DAO.writeToDB";
    public static final String ACTION_READ_CHANNELS_FROM_DB = "com.focusstart.miller777.focusstartandroidrssreader.DAO.readChannelsFromDB";
    public static final String ACTION_DELETE_CHANNELS_FROM_DB_BY_LINK = "com.focusstart.miller777.focusstartandroidrssreader.DAO.deleteChannelsFromDBByLink";

    private Context context;
    private ChannelModel channel;

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

        deleteAllNewsAtChannelByChannelLink(itemLink);
        //TODO: сделать реализацию удаления канала из таблицы каналов
        Intent deleteChannelsFromDBIntent = new Intent(App.getContext(), DataBaseService.class);
        deleteChannelsFromDBIntent.putExtra("ACTION", ACTION_DELETE_CHANNELS_FROM_DB_BY_LINK);
        deleteChannelsFromDBIntent.putExtra("LINK", itemLink);
        App.getContext().startService(deleteChannelsFromDBIntent);

    }

    private void deleteAllNewsAtChannelByChannelLink(String itemLink) {
        //TODO: сделать реализацию удаления всех новостей канала из таблицы новостей

    }
}
