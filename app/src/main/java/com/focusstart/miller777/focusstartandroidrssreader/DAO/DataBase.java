package com.focusstart.miller777.focusstartandroidrssreader.DAO;

import android.content.Context;

import com.focusstart.miller777.focusstartandroidrssreader.App;

public class DataBase {
    //объект класса служит для записи и чтения данных в базу данных.

    private DBHelper dbHelper;
    private Context context;

    public DataBase(Context context) {
        this.context = App.getContext();
    }
}
