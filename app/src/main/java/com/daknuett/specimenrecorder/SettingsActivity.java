package com.daknuett.specimenrecorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.daknuett.specimenrecorder.listeners.OnSettingsClickedListener;
import com.daknuett.specimenrecorder.listeners.settings.LocationRadiusEditListener;
import com.daknuett.specimenrecorder.model.FirstRunException;
import com.daknuett.specimenrecorder.model.settings.SettingsStorageManager;

import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {

    private String settingsFilename;
    private SettingsStorageManager storageManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();
        settingsFilename = intent.getStringExtra(OnSettingsClickedListener.ARG_FILENAME);

        EditText location_radius = (EditText) findViewById(R.id.location_radius);
        location_radius.setText("" + getResources().getInteger(R.integer.default_location_radius));
        try {
            storageManager = SettingsStorageManager.load(settingsFilename);
            location_radius.setText(storageManager.locationSettings.radius + "");
        }
        catch (IOException e)
        {
            storageManager = new SettingsStorageManager(settingsFilename);
        }
        catch (FirstRunException e)
        {
            storageManager = new SettingsStorageManager(settingsFilename);
            Toast.makeText(getApplicationContext(),
                    "This appears to be your first run. Make sure to check all the settings.",
                    Toast.LENGTH_LONG).show();
        }

        location_radius.setOnEditorActionListener(new LocationRadiusEditListener(storageManager, this));



    }

}
