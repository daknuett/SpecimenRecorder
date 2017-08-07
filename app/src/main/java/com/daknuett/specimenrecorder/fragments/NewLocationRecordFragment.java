package com.daknuett.specimenrecorder.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daknuett.specimenrecorder.R;
import com.daknuett.specimenrecorder.listeners.newLocationRecord.NewLocationCreateButtonListener;
import com.daknuett.specimenrecorder.listeners.newLocationRecord.NewLocationCreateURIButtonListener;
import com.daknuett.specimenrecorder.listeners.newLocationRecord.NewLocationTakePictureButtonListener;
import com.daknuett.specimenrecorder.listeners.newLocationRecord.NewLocationUseGPSCheckboxListener;
import com.daknuett.specimenrecorder.model.intents.RequestCodes;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * Created by daniel on 06.08.17.
 */

public class NewLocationRecordFragment
        extends MyFragment {
    private LocationManager locationManager;
    private LocationListener locationListener;
    private NewLocationTakePictureButtonListener takePictureButtonListener;


    public static final int REQUEST_CODE_ACCESS_FINE_LOCATION = 0x200;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_newlocationrecord, container, false);

        loadStorage(getArguments());


        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();

        EditText newLocationRadiusEdit = (EditText) activity.findViewById(R.id.new_location_radius);
        EditText newLocationCountryEdit = (EditText) activity.findViewById(R.id.new_location_country);
        final CheckBox newLocationUseGPSCheck = (CheckBox) activity.findViewById(R.id.new_location_useGPS);

        final EditText newLocationLatitudeEdit = (EditText) activity.findViewById(R.id.new_location_latitude);
        final EditText newLocationLongitudeEdit = (EditText) activity.findViewById(R.id.new_location_longitude);
        EditText newLocationNameEdit = (EditText) activity.findViewById(R.id.new_location_name);
        EditText newLocationURIEdit = (EditText) activity.findViewById(R.id.new_location_identifier);
        Button newLocationAutogenerateURIButton = (Button) activity.findViewById(R.id.new_location_autoGenerateIdentifier);

        TextView newLocationImageURIView = (TextView) activity.findViewById(R.id.new_location_imageURI);
        ImageView newLocationImageView = (ImageView) activity.findViewById(R.id.new_location_imagePreview);
        Button newLocationTakeImageButton = (Button) activity.findViewById(R.id.new_location_capture_image_button);

        EditText newLocationAddressEdit = (EditText) activity.findViewById(R.id.new_location_address);
        EditText newLocationTagsEdit = (EditText) activity.findViewById(R.id.new_location_tags);
        EditText newLocationIdentifierEdit = (EditText) activity.findViewById(R.id.new_location_identifier);
        EditText newLocationDescriptionEdit = (EditText) activity.findViewById(R.id.new_location_description);
        Button newLocationCreateButton = (Button) activity.findViewById(R.id.new_location_create);

        newLocationCreateButton.setOnClickListener(new NewLocationCreateButtonListener(
                recordDatabase,
                newLocationNameEdit,
                newLocationDescriptionEdit,
                newLocationLongitudeEdit,
                newLocationLatitudeEdit,
                newLocationRadiusEdit,
                newLocationCountryEdit,
                newLocationIdentifierEdit,
                newLocationTagsEdit,
                newLocationAddressEdit,
                newLocationImageURIView,
                activity,
                this
        ));

        newLocationAutogenerateURIButton.setOnClickListener(new NewLocationCreateURIButtonListener(newLocationLatitudeEdit,
                newLocationLongitudeEdit, newLocationNameEdit, newLocationURIEdit));

        newLocationCountryEdit.setText(settingsStorageManager.locationSettings.country);
        newLocationRadiusEdit.setText(settingsStorageManager.locationSettings.radius + "");
        newLocationUseGPSCheck.setChecked(settingsStorageManager.locationSettings.useGPS);

        takePictureButtonListener = new NewLocationTakePictureButtonListener(newLocationImageURIView,
                newLocationImageView,
                activity,
                this);

        newLocationTakeImageButton.setOnClickListener(takePictureButtonListener);

        if (newLocationUseGPSCheck.isChecked()) {
            newLocationLatitudeEdit.setEnabled(false);
            newLocationLatitudeEdit.setAlpha(.5f);
            newLocationLongitudeEdit.setEnabled(false);
            newLocationLongitudeEdit.setAlpha(.5f);
            newLocationLatitudeEdit.setFocusable(false);
            newLocationLongitudeEdit.setFocusable(false);
        }

        newLocationUseGPSCheck.setOnCheckedChangeListener(new NewLocationUseGPSCheckboxListener(newLocationLatitudeEdit,
                newLocationLongitudeEdit));

        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if (newLocationUseGPSCheck.isChecked()) {
                    newLocationLatitudeEdit.setText(String.valueOf(location.getLatitude()));
                    newLocationLongitudeEdit.setText(String.valueOf(location.getLongitude()));
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
                if (newLocationUseGPSCheck.isChecked()) {
                    Toast.makeText(activity.getApplicationContext(),
                            getString(R.string.gps_is_required), Toast.LENGTH_LONG).show();

                }

            }
        };

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RequestCodes.REQUEST_CAPTURE_PHOTO && resultCode == RESULT_OK)
        {

            takePictureButtonListener.onActivityResult(data);

        }

    }
}
