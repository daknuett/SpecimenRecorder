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
    public static final String ARG_FILENAME = "com.daknuett.specimenrecorder.settings.filename";
    private String settingsFilename;

    public OnSettingsClickedListener(AppCompatActivity activity, String settingsFilename)
    {
        this.activity = activity;
        this.settingsFilename = settingsFilename;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent = new Intent(this.activity, SettingsActivity.class);
        intent.putExtra(ARG_FILENAME, settingsFilename);
        activity.startActivity(intent);
        return true;
    }
}
