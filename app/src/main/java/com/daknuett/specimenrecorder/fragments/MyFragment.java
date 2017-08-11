package com.daknuett.specimenrecorder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.daknuett.specimenrecorder.R;
import com.daknuett.specimenrecorder.model.FirstRunException;
import com.daknuett.specimenrecorder.model.model.RecordDatabase;
import com.daknuett.specimenrecorder.model.settings.SettingsStorageManager;

import java.io.IOException;

/**
 * Created by daniel on 06.08.17.
 */

public class MyFragment
extends Fragment
{
    public static final String ARG_STORAGE_PATH = "storage_path";
    protected RecordDatabase recordDatabase;
    protected SettingsStorageManager settingsStorageManager;
    public static final String ARG_DATA_PATH = "data_path";
    public static final String ARG_SETTINGS_FILENAME = "settings_filename";

    protected void loadStorage(Bundle savedInstanceState)
    {
        recordDatabase = RecordDatabase.load(savedInstanceState.getString(ARG_DATA_PATH));

        System.out.println(getClass().getName() + " : " + recordDatabase);

        String settingsFilename = savedInstanceState.getString(ARG_SETTINGS_FILENAME);
        try {
            settingsStorageManager = SettingsStorageManager.load(settingsFilename);
        } catch (IOException e) {
            e.printStackTrace();
            settingsStorageManager = new SettingsStorageManager(settingsFilename);
        } catch (FirstRunException e) {
            Toast.makeText(getContext(), getString(R.string.first_run), Toast.LENGTH_LONG);
            settingsStorageManager = new SettingsStorageManager(settingsFilename);
        }
    }

}
