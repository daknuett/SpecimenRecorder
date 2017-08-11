package com.daknuett.specimenrecorder.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daknuett.specimenrecorder.R;
import com.daknuett.specimenrecorder.model.model.LocationRecord;
import com.daknuett.specimenrecorder.model.model.RecordDatabase;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by daniel on 08.08.17.
 */

public class EditLocationActivity
extends Activity
{
    private RecordDatabase recordDatabase;
    private String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        recordDatabase = RecordDatabase.load(intent.getStringExtra("record_database_path"));
        int recordNo = intent.getIntExtra("record_number", 0);
        path = intent.getStringExtra("path");

        List<LocationRecord> records;
        try {
            records = recordDatabase.getAllLocationRecords();
        } catch (IOException e) {
            e.printStackTrace();
            finish();
            return;
        }

        LocationRecord thisRecord = records.get(recordNo);
        this.setContentView(R.layout.activity_edit_location);

        TextView locationIdentifier = (TextView) findViewById(R.id.edit_location_id);
        EditText locationLatitude = (EditText) findViewById(R.id.edit_location_latitude);
        EditText locationLongitude = (EditText) findViewById(R.id.edit_location_longitude);
        EditText locationAddress = (EditText) findViewById(R.id.edit_location_address);
        EditText locationName = (EditText) findViewById(R.id.edit_location_name);
       // EditText locationDescription = (EditText) findViewById(R.id.edit_location_d)
        EditText locationRadius = (EditText) findViewById(R.id.edit_location_radius);
        EditText locationTags = (EditText) findViewById(R.id.edit_location_tags);
        TextView locationImageURI = (TextView) findViewById(R.id.edit_location_imageURI);
        ImageView locationImagePreview = (ImageView) findViewById(R.id.edit_location_imagePreview);
        Button saveEditsButton = (Button) findViewById(R.id.edit_location_save_button);

        locationIdentifier.setText(thisRecord.getIdentifier());
        locationLatitude.setText(String.valueOf(thisRecord.getLatitude()));
        locationLongitude.setText(String.valueOf(thisRecord.getLongitude()));
        locationAddress.setText(thisRecord.getAddress());
        locationName.setText(thisRecord.getName());
        locationRadius.setText(String.valueOf(thisRecord.getRadius()));

        String [] tags = thisRecord.getTags();
        String tagsString = "";
        for (String tag :
                tags) {
            tagsString += tag + ",";
        }
        tagsString = tagsString.substring(0, tagsString.length() - 1);
        locationTags.setText(tagsString);
        locationImageURI.setText(thisRecord.getImageURI());

        File imageFile = new File(path, getString(R.string.image_prefix));
        imageFile = new File(imageFile, thisRecord.getImageURI());

        if(imageFile.exists())
        {
            Bitmap image = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            locationImagePreview.setImageBitmap(image);
        }


    }
}
