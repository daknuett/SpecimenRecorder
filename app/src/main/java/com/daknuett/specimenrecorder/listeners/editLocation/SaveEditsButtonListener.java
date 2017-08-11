package com.daknuett.specimenrecorder.listeners.editLocation;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daknuett.specimenrecorder.R;
import com.daknuett.specimenrecorder.model.model.LocationRecord;
import com.daknuett.specimenrecorder.model.model.RecordDatabase;

import java.io.File;
import java.io.IOException;

/**
 * Created by daniel on 10.08.17.
 */

public class SaveEditsButtonListener
implements View.OnClickListener
{
    private RecordDatabase recordDatabase;
    private TextView locationIdentifier;
    private EditText locationLatitude;
    private EditText locationLongitude;
    private EditText locationAddress;
    private EditText locationName;
    private EditText locationRadius;
    private EditText locationTags;
    private EditText locationDescription;
    private TextView locationImageURI;
    private ImageView locationImagePreview;
    private Activity activity;

    public SaveEditsButtonListener(RecordDatabase recordDatabase, TextView locationIdentifier, EditText locationLatitude, EditText locationLongitude, EditText locationAddress, EditText locationName, EditText locationRadius, EditText locationTags, EditText locationDescription, TextView locationImageURI, ImageView locationImagePreview, Activity activity) {
        this.recordDatabase = recordDatabase;
        this.locationIdentifier = locationIdentifier;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.locationAddress = locationAddress;
        this.locationName = locationName;
        this.locationRadius = locationRadius;
        this.locationTags = locationTags;
        this.locationDescription = locationDescription;
        this.locationImageURI = locationImageURI;
        this.locationImagePreview = locationImagePreview;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        File storageDir = new File(Environment.getExternalStorageDirectory(), activity.getString(R.string.path));
        storageDir = new File(storageDir, activity.getString(R.string.image_prefix));
        File image = new File(storageDir, locationImageURI.getText().toString());
        Double[][] colorMap;
        if(image.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
            Bitmap colorBitMap = Bitmap.createScaledBitmap(bitmap, 5, 5, true);

            colorMap = new Double[5][5];
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    colorMap[i][j] = (double) colorBitMap.getPixel(i, j);
                }
            }
        }
        else
        {
            colorMap = new Double[0][0];
        }
        String latitude = locationLatitude.getText().toString(),
                longitude = locationLongitude.getText().toString();
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

       /* LocationRecord locationRecord = new LocationRecord(
                Double.parseDouble(latitude),
                Double.parseDouble(longitude),
                Double.parseDouble(locationRadius.getText().toString()),
                locationName.getText().toString(),
                locat.getText().toString(),
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
*/
    }
}
