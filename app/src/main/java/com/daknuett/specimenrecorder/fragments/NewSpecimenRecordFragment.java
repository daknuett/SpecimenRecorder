package com.daknuett.specimenrecorder.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daknuett.specimenrecorder.R;
import com.daknuett.specimenrecorder.listeners.newLocationRecord.NewLocationCreateButtonListener;
import com.daknuett.specimenrecorder.listeners.newSpecimenRecord.NewSpecimenCreateButton;
import com.daknuett.specimenrecorder.listeners.newSpecimenRecord.NewSpecimenCreateURIButtonListener;
import com.daknuett.specimenrecorder.listeners.newSpecimenRecord.NewSpecimenTakePictureButtonListener;
import com.daknuett.specimenrecorder.model.intents.RequestCodes;
import com.daknuett.specimenrecorder.model.model.LocationRecord;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.daknuett.specimenrecorder.fragments.NewLocationRecordFragment.REQUEST_CODE_ACCESS_FINE_LOCATION;

/**
 * Created by daniel on 06.08.17.
 */

public class NewSpecimenRecordFragment
extends MyFragment
{

    private NewSpecimenTakePictureButtonListener takePictureButtonListener;
    private android.location.LocationManager locationManager;
    private LocationListener locationListener;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_newspecimenrecord, container, false);

        loadStorage(getArguments());
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = getActivity();

        EditText authorEdit = (EditText) activity.findViewById(R.id.new_species_author);
        authorEdit.setText(settingsStorageManager.speciesSettings.author);

        Spinner locationURISpinner = (Spinner) activity.findViewById(R.id.new_species_locationURI_spinner);

        List<LocationRecord> recordList;
        try {
            recordList = recordDatabase.getAllLocationRecords();
        } catch (IOException e) {
            e.printStackTrace();
            recordList = new LinkedList<>();
        }

        List<String> recordNameList = new LinkedList<String>();
        int default_record_offset = 0,
            i = 0;

        for (LocationRecord record : recordList) {
            recordNameList.add(record.getName());
            if(record.getIdentifier().equals(settingsStorageManager.speciesSettings.locationURI))
            {
                default_record_offset = i;
            }
            i++;
        }
        recordNameList.add("NONE");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, recordNameList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationURISpinner.setAdapter(adapter);

        locationURISpinner.setSelection(default_record_offset);

        Button createURIButton = (Button) activity.findViewById(R.id.new_species_autoGenerateIdentifier);
        EditText specimenURI = (EditText) activity.findViewById(R.id.new_species_identifier);
        EditText specimenGenus = (EditText) activity.findViewById(R.id.new_species_genus);
        EditText specimenSpecies = (EditText) activity.findViewById(R.id.new_species_species);

        createURIButton.setOnClickListener(new NewSpecimenCreateURIButtonListener(specimenGenus,
                specimenSpecies, specimenURI));

        TextView imageURIView = (TextView) activity.findViewById(R.id.new_species_imageURI);
        ImageView imageView = (ImageView) activity.findViewById(R.id.new_species_imagePreview);
        Button takeImageButton = (Button) activity.findViewById(R.id.new_species_capture_image_button);

        takePictureButtonListener = new NewSpecimenTakePictureButtonListener(imageURIView, imageView, activity, this);
        takeImageButton.setOnClickListener(takePictureButtonListener);

        final CheckBox addGPSLocation = (CheckBox) activity.findViewById(R.id.add_gps_location);
        final TextView gpsLocationLat = (TextView) activity.findViewById(R.id.gps_location_lat);
        final TextView gpsLocationLon = (TextView) activity.findViewById(R.id.gps_location_lon);


        final NewSpecimenRecordFragment This = this;


        addGPSLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    This.tryToGetLocationData();
                }
            }
        });


        EditText nicknameEdit = (EditText) activity.findViewById(R.id.new_species_nickname);
        EditText subspeciesEdit = (EditText) activity.findViewById(R.id.new_species_subspecies);









        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if (addGPSLocation.isChecked()) {
                    gpsLocationLat.setText(String.valueOf(location.getLatitude()));
                    gpsLocationLon.setText(String.valueOf(location.getLongitude()));
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
                if (addGPSLocation.isChecked()) {
                    try {
                        Toast.makeText(activity.getApplicationContext(),
                                getString(R.string.gps_is_required), Toast.LENGTH_LONG).show();
                    }
                    catch (IllegalStateException e)
                    {

                    }

                }

            }
        };

        tryToGetLocationData();


        Button createRecordButton = (Button) activity.findViewById(R.id.add_specimen_record);
        createRecordButton.setOnClickListener(
                new NewSpecimenCreateButton(
                        specimenGenus,
                        specimenSpecies,
                        subspeciesEdit,
                        nicknameEdit,
                        authorEdit,
                        imageURIView,
                        locationURISpinner,
                        specimenURI,
                        gpsLocationLat,
                        gpsLocationLon,
                        activity,
                        recordDatabase
                )
        );

    }

    public void tryToGetLocationData() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(activity.getApplicationContext(),
                        getString(R.string.gps_is_required), Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ACCESS_FINE_LOCATION);

            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ACCESS_FINE_LOCATION);
            }

        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RequestCodes.REQUEST_CAPTURE_PHOTO && resultCode == RESULT_OK)
        {

            takePictureButtonListener.onActivityResult(data);

        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                    } catch (SecurityException e) {
                    }
                }
        }
    }
}
