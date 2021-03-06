package com.daknuett.specimenrecorder.listeners.settings;

import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.daknuett.specimenrecorder.model.settings.SettingsStorageManager;

import java.io.IOException;

/**
 * Created by daniel on 05.08.17.
 */

public class LocationUseGPSCheckboxListener
implements CompoundButton.OnCheckedChangeListener
{
    private SettingsStorageManager storageManager;
    private AppCompatActivity activity;
    boolean ignoreOnce = true;

    public LocationUseGPSCheckboxListener(SettingsStorageManager storageManager, AppCompatActivity activity) {
        this.storageManager = storageManager;
        this.activity = activity;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        /*
        * For some weird reasons, if I create the new activity and the view
        * gets launched this checkbox thinks that it should trigger onCheckedChanged.
        * It always stores true then. This is my hacky way to bypass the problem.
        * */

        if(ignoreOnce)
        {
            ignoreOnce = false;
            return;
        }

        storageManager.locationSettings.useGPS = isChecked;

        try {
            storageManager.dump();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity.getApplicationContext(), "Unable to store settings", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(activity.getApplicationContext(),
                "Stored location useGPS: " + storageManager.locationSettings.useGPS,
                Toast.LENGTH_SHORT).show();
    }
}
