package com.daknuett.specimenrecorder.listeners.newSpecimenRecord;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daknuett.specimenrecorder.R;
import com.daknuett.specimenrecorder.model.model.RecordDatabase;
import com.daknuett.specimenrecorder.model.model.SpeciesRecord;

import java.io.IOException;

/**
 * Created by daniel on 11.08.17.
 */

public class NewSpecimenCreateButton
        implements View.OnClickListener {
    private EditText
        genusEdit,
        speciesEdit,
        subspeciesEdit,
        nicknameEdit,
        authorEdit;
    private TextView
        imageURIView;
    private Spinner
        locationURISpinner;
    private EditText
        identifierEdit;
    private TextView
        latitudeView,
        longitudeView;

    private Activity activity;
    private RecordDatabase recordDatabase;

    public NewSpecimenCreateButton(EditText genusEdit, EditText speciesEdit, EditText subspeciesEdit, EditText nicknameEdit, EditText authorEdit, TextView imageURIView, Spinner locationURISpinner, EditText identifierEdit, TextView latitudeView, TextView longitudeView, Activity activity, RecordDatabase recordDatabase) {
        this.genusEdit = genusEdit;
        this.speciesEdit = speciesEdit;
        this.subspeciesEdit = subspeciesEdit;
        this.nicknameEdit = nicknameEdit;
        this.authorEdit = authorEdit;
        this.imageURIView = imageURIView;
        this.locationURISpinner = locationURISpinner;
        this.identifierEdit = identifierEdit;
        this.latitudeView = latitudeView;
        this.longitudeView = longitudeView;
        this.activity = activity;
        this.recordDatabase = recordDatabase;
    }

    @Override
    public void onClick(View v) {

        String genus = genusEdit.getText().toString(),
                species = speciesEdit.getText().toString(),
                subspecies = subspeciesEdit.getText().toString(),
                nickname = nicknameEdit.getText().toString(),
                author = authorEdit.getText().toString();

        String locationURI = (String) locationURISpinner.getSelectedItem();
        String imageURI = imageURIView.getText().toString();
        String identifier = identifierEdit.getText().toString();
        double latitude = Double.valueOf(latitudeView.getText().toString());
        double longitude = Double.valueOf(longitudeView.getText().toString());

        if(identifier.equals(""))
        {
            Toast.makeText(activity.getApplicationContext(), R.string.identifier_required, Toast.LENGTH_SHORT).show();
            return;
        }

        SpeciesRecord record = new SpeciesRecord(
                author,
                genus,
                species,
                subspecies,
                nickname,
                imageURI,
                locationURI,
                identifier,
                System.currentTimeMillis() / 1000L,
                latitude,
                longitude
        );
        try {
            recordDatabase.addRecord(record);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity.getApplicationContext(), R.string.failed_to_store, Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(activity.getApplicationContext(),
                activity.getString(R.string.stored_ok),
                Toast.LENGTH_SHORT
        ).show();
        identifierEdit.setText("");
    }
}
