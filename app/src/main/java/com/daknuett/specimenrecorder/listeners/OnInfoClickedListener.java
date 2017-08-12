package com.daknuett.specimenrecorder.listeners;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.daknuett.specimenrecorder.InfoActivity;

/**
 * Created by daniel on 12.08.17.
 */

public class OnInfoClickedListener
        implements MenuItem.OnMenuItemClickListener {
    private AppCompatActivity activity;

    public OnInfoClickedListener(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent = new Intent(this.activity, InfoActivity.class);

        activity.startActivity(intent);
        return true;

    }
}
