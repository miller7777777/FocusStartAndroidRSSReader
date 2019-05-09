package com.focusstart.miller777.focusstartandroidrssreader.DAO;

import android.provider.BaseColumns;

public final class ChannelContract {

    public ChannelContract() {
    }

    public static final class ChannelEntry implements BaseColumns {
        public static final String TABLE_NAME = "channels";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_LINK = "link";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_LASTBUILDDATE = "lastBuildDate";
    }
}
