package com.focusstart.miller777.focusstartandroidrssreader.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.focusstart.miller777.focusstartandroidrssreader.App;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelModel;
import com.focusstart.miller777.focusstartandroidrssreader.DAO.ChannelContract;


public class DataBase {
    //объект класса служит для записи и чтения данных в базу данных.
    public static final String TAG = DataBase.class.getSimpleName();

    private DBChannelHelper dbChannelHelper;
    private Context context;
    private ChannelModel channel;

    public DataBase() {
        this.context = App.getContext();
    }

    public void writeToDB(ChannelModel channel) {

        Log.d(TAG, "Попытка записи в базу");

        DBChannelHelper dbChannelHelper = new DBChannelHelper();
        SQLiteDatabase db = dbChannelHelper.getWritableDatabase();

        //Проверка: есть ли в базе канал с таким link
        if (!checkDBContainsChannelWithLink(db, channel.getLink(), channel)){

            db.beginTransaction();

            ContentValues cv = new ContentValues();
            cv.put(ChannelContract.ChannelEntry.COLUMN_TITLE, channel.getTitle());
            cv.put(ChannelContract.ChannelEntry.COLUMN_LINK, channel.getLink());
            cv.put(ChannelContract.ChannelEntry.COLUMN_DESCRIPTION, channel.getDescription());
            cv.put(ChannelContract.ChannelEntry.COLUMN_LASTBUILDDATE, channel.getLastBuildDate());

//        int isUpdated = db.update(ChannelContract.ChannelEntry.TABLE_NAME,
//                cv,
//                ChannelContract.ChannelEntry.COLUMN_TITLE + " =?",
//                new String[] {channel.getTitle()});

            db.insert(ChannelContract.ChannelEntry.TABLE_NAME, null, cv);

//        if (isUpdated <= 0) {
//            long isInserted = db.insertWithOnConflict(ChannelContract.ChannelEntry.COLUMN_TITLE, null, cv,
//                    SQLiteDatabase.CONFLICT_REPLACE);
//        }

            db.setTransactionSuccessful();
            db.endTransaction();

            Log.d(TAG, "Данные записаны");

            db.close();
            dbChannelHelper.close();
        }


    }

    private boolean checkDBContainsChannelWithLink(SQLiteDatabase db, String title, ChannelModel channel) {

        boolean result = false;

        Log.d(TAG, "Создаем курсор");
        Log.d(TAG, "channel.getLink() = " + channel.getLink());
        Log.d(TAG, "title = " + title);

        Cursor c = db.query(ChannelContract.ChannelEntry.TABLE_NAME, null, null, null, null, null, null);


        int count = 0;

        if(c.moveToFirst()){
            int linkColIndex = c.getColumnIndex(ChannelContract.ChannelEntry.COLUMN_LINK);
            Log.d(TAG, "linkColIndex = " + linkColIndex);

            do{
                if(c.getString(linkColIndex).equalsIgnoreCase(title)){
                    count++;
                }
                Log.d(TAG, "c.getString(linkColIndex) = " + c.getString(linkColIndex));


            } while (c.moveToNext());
        }

        c.close();

        if (count > 0){
            result = true;
        }

        Log.d(TAG, "Count = : " + count);
        Log.d(TAG, "Канал с таким link есть в базе: " + result);



//        Cursor cursor = db.query(ChannelContract.ChannelEntry.TABLE_NAME, new String[] {ChannelContract.ChannelEntry.COLUMN_LINK},
//                ChannelContract.ChannelEntry.COLUMN_LINK + " = " + channel.getLink(),
//                null,null,null,null);
//
//
//
//
//        if (cursor != null && cursor.getCount() > 0) {
//            result = true; //в базе есть канал с таким link
//            Log.d(TAG, "Проверка: в базе есть канал с таким link");
//        }else {
//            Log.d(TAG, "Проверка: в базе нет канала с таким link");
//
//        }

        return result;
    }
}
