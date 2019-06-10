package com.focusstart.miller777.focusstartandroidrssreader.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.focusstart.miller777.focusstartandroidrssreader.R;

public class PrefActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
    }
}
