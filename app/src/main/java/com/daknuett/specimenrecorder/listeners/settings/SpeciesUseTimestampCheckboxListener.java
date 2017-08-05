package com.daknuett.specimenrecorder.listeners.settings;

import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.daknuett.specimenrecorder.model.settings.SettingsStorageManager;

import java.io.IOException;

/**
 * Created by daniel on 05.08.17.
 */

public class SpeciesUseTimestampCheckboxListener
        implements CompoundButton.OnCheckedChangeListener
{
    private SettingsStorageManager storageManager;
    private AppCompatActivity activity;

    public SpeciesUseTimestampCheckboxListener(SettingsStorageManager storageManager, AppCompatActivity activity) {
        this.storageManager = storageManager;
        this.activity = activity;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        storageManager.speciesSettings.useTimestamp = isChecked;

        try {
            storageManager.dump();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity.getApplicationContext(), "Unable to store settings", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(activity.getApplicationContext(),
                "Stored species useTimestamp: " + storageManager.speciesSettings.useTimestamp,
                Toast.LENGTH_SHORT).show();
    }
}

