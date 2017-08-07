package com.daknuett.specimenrecorder.listeners.newLocationRecord;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daknuett.specimenrecorder.R;
import com.daknuett.specimenrecorder.model.model.LocationRecord;
import com.daknuett.specimenrecorder.model.model.RecordDatabase;

import java.io.File;
import java.io.IOException;

/**
 * Created by daniel on 07.08.17.
 */

public class NewLocationCreateButtonListener implements View.OnClickListener {

    private RecordDatabase recordDatabase;
    private EditText nameEdit;
    private EditText descriptionEdit;
    private EditText longitudeEdit;
    private EditText latitudeEdit;
    private EditText radiusEdit;
    private EditText countryEdit;
    private EditText identifierEdit;
    private EditText tagsEdit;
    private EditText addressEdit;
    private TextView imageURIView;
    private Activity activity;
    private Fragment fragment;

    public NewLocationCreateButtonListener(RecordDatabase recordDatabase, EditText nameEdit, EditText descriptionEdit, EditText longitudeEdit, EditText latitudeEdit, EditText radiusEdit, EditText countryEdit, EditText identifierEdit, EditText tagsEdit, EditText addressEdit, TextView imageURIView, Activity activity, Fragment fragment) {
        this.recordDatabase = recordDatabase;
        this.nameEdit = nameEdit;
        this.descriptionEdit = descriptionEdit;
        this.longitudeEdit = longitudeEdit;
        this.latitudeEdit = latitudeEdit;
        this.radiusEdit = radiusEdit;
        this.countryEdit = countryEdit;
        this.identifierEdit = identifierEdit;
        this.tagsEdit = tagsEdit;
        this.addressEdit = addressEdit;
        this.imageURIView = imageURIView;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public void onClick(View v) {
        File storageDir = new File(Environment.getExternalStorageDirectory(), activity.getString(R.string.path));
        storageDir = new File(storageDir, activity.getString(R.string.image_prefix));
        File image = new File(storageDir, imageURIView.getText().toString());
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
        Bitmap colorBitMap = Bitmap.createScaledBitmap(bitmap, 5, 5, true);

        Double[][] colorMap = new Double[5][5];
        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < 5; j++)
            {
                colorMap[i][j] = (double) colorBitMap.getPixel(i, j);
            }
        }

        String latitude = latitudeEdit.getText().toString(),
                longitude = longitudeEdit.getText().toString();
        if(latitude.equals(""))
        {
            Toast.makeText(activity.getApplicationContext(),
                    activity.getString(R.string.latitude_required),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if(longitude.equals(""))
        {
            Toast.makeText(activity.getApplicationContext(),
                    activity.getString(R.string.longitude_required),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        LocationRecord locationRecord = new LocationRecord(
                Double.parseDouble(latitude),
                Double.parseDouble(longitude),
                Double.parseDouble(radiusEdit.getText().toString()),
                nameEdit.getText().toString(),
                descriptionEdit.getText().toString(),
                identifierEdit.getText().toString(),
                countryEdit.getText().toString(),
                tagsEdit.getText().toString().split(","),
                addressEdit.getText().toString(),
                imageURIView.getText().toString(),
                colorMap
        );
        try {
            recordDatabase.addRecord(locationRecord);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity.getApplicationContext(),
                    activity.getString(R.string.failed_to_store),
                    Toast.LENGTH_SHORT
                    ).show();
            return;
        }

        nameEdit.setText("");
        descriptionEdit.setText("");
        addressEdit.setText("");

        Toast.makeText(activity.getApplicationContext(),
                activity.getString(R.string.stored_ok),
                Toast.LENGTH_SHORT
        ).show();

    }
}
