package com.daknuett.specimenrecorder.listeners.settings;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.daknuett.specimenrecorder.model.settings.SettingsStorageManager;

import java.io.IOException;

/**
 * Created by daniel on 12.08.17.
 */

public class SpeciesLocationURISpinnerListener
        implements AdapterView.OnItemSelectedListener{
    private  SettingsStorageManager storageManager;
    private Activity activity;
    boolean ignoreOnce = true;
    public SpeciesLocationURISpinnerListener(SettingsStorageManager storageManager, Activity settingsActivity)
    {
        activity = settingsActivity;
        this.storageManager = storageManager;
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*
        * I have no Idea why, but my spinner thinks, that if the view is created
        * (or some stuff like that happened) it has to trigger onItemSelected on the
        * first element in the list. This is my hacky way to avoid storing wrong stuff.
        * */
        if(ignoreOnce)
        {
            ignoreOnce = false;
            return;
        }
        Spinner spinner = (Spinner) parent;
        storageManager.speciesSettings.locationURI = (String) spinner.getSelectedItem();
        try {
            storageManager.dump();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity.getApplicationContext(), "Unable to store settings", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(activity.getApplicationContext(),
                "Stored species locationURI: " + storageManager.speciesSettings.locationURI,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // do nothing
    }
}
