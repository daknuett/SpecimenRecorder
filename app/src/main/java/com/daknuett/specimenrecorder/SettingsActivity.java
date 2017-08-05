package com.daknuett.specimenrecorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.daknuett.specimenrecorder.listeners.OnSettingsClickedListener;
import com.daknuett.specimenrecorder.listeners.settings.LocationCountryEditListener;
import com.daknuett.specimenrecorder.listeners.settings.LocationRadiusEditListener;
import com.daknuett.specimenrecorder.listeners.settings.LocationUseGPSCheckboxListener;
import com.daknuett.specimenrecorder.listeners.settings.SpeciesAuthorEditListener;
import com.daknuett.specimenrecorder.listeners.settings.SpeciesUseTimestampCheckboxListener;
import com.daknuett.specimenrecorder.model.FirstRunException;
import com.daknuett.specimenrecorder.model.model.LocationRecord;
import com.daknuett.specimenrecorder.model.model.RecordDatabase;
import com.daknuett.specimenrecorder.model.settings.SettingsStorageManager;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private String settingsFilename;
    private String dataPath;
    private SettingsStorageManager storageManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();
        settingsFilename = intent.getStringExtra(OnSettingsClickedListener.ARG_SETTINGS_FILENAME);
        dataPath = intent.getStringExtra(OnSettingsClickedListener.ARG_DATA_PREFIX);


        EditText location_radius = (EditText) findViewById(R.id.location_radius);
        location_radius.setText("" + getResources().getInteger(R.integer.default_location_radius));

        AppCompatCheckBox location_useGPS = (AppCompatCheckBox) findViewById(R.id.location_useGPS);
        EditText location_country = (EditText) findViewById(R.id.location_country);
        EditText species_author = (EditText) findViewById(R.id.species_author);
        AppCompatCheckBox species_useTimestamp = (AppCompatCheckBox) findViewById(R.id.species_useTimestamp);
        Spinner species_locationURI = (Spinner) findViewById(R.id.species_locationURI);

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

        location_country.setText(storageManager.locationSettings.country);
        location_country.setOnEditorActionListener(new LocationCountryEditListener(storageManager, this));

        location_useGPS.setOnCheckedChangeListener(new LocationUseGPSCheckboxListener(storageManager, this));
        location_useGPS.setChecked(storageManager.locationSettings.useGPS);

        species_author.setText(storageManager.speciesSettings.author);
        species_author.setOnEditorActionListener(new SpeciesAuthorEditListener(storageManager, this));

        species_useTimestamp.setChecked(storageManager.speciesSettings.useTimestamp);
        species_useTimestamp.setOnCheckedChangeListener(new SpeciesUseTimestampCheckboxListener(storageManager, this));

        RecordDatabase recordDatabase = RecordDatabase.load(dataPath);


        try {
            List<CharSequence> locationURIList = new LinkedList<CharSequence>();
            for (LocationRecord record :
                    recordDatabase.getAllLocationRecords()) {
                locationURIList.add(record.getIdentifier());
            }
            ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    locationURIList);
            species_locationURI.setAdapter(spinnerAdapter);
        } catch (IOException e) {
            //e.printStackTrace();
        }



    }

}
