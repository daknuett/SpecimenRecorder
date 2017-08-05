package com.daknuett.specimenrecorder.listeners;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.daknuett.specimenrecorder.SettingsActivity;

/**
 * Created by daniel on 03.08.17.
 */

public class OnSettingsClickedListener
        implements MenuItem.OnMenuItemClickListener {

    private AppCompatActivity activity;
    public static final String ARG_SETTINGS_FILENAME = "com.daknuett.specimenrecorder.settings.filename";
    public static final String ARG_DATA_PREFIX = "com.daknuett.specimenrecorder.data.prefix";

    private String settingsFilename;
    private String dataPrefix;

    public OnSettingsClickedListener(AppCompatActivity activity,
                                     String settingsFilename,
                                     String dataPrefix)
    {
        this.activity = activity;
        this.settingsFilename = settingsFilename;
        this.dataPrefix = dataPrefix;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent = new Intent(this.activity, SettingsActivity.class);
        intent.putExtra(ARG_SETTINGS_FILENAME, settingsFilename);
        intent.putExtra(ARG_DATA_PREFIX, dataPrefix);
        activity.startActivity(intent);
        return true;
    }
}
