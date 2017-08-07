package com.daknuett.specimenrecorder.listeners.newLocationRecord;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.daknuett.specimenrecorder.model.URIGenerator.CompoundURIGenerator;
import com.daknuett.specimenrecorder.model.URIGenerator.URIGenerator;

/**
 * Created by daniel on 07.08.17.
 */

public class NewLocationCreateURIButtonListener implements View.OnClickListener {

    private EditText newLocationLatitudeEdit,
                    newLocationLongitudeEdit,
                    newLocationNameEdit,
                    newLocationURIEdit;
    private URIGenerator uriGenerator;

    public NewLocationCreateURIButtonListener(EditText newLocationLatitudeEdit, EditText newLocationLongitudeEdit, EditText newLocationNameEdit, EditText newLocationURIEdit) {
        this.newLocationLatitudeEdit = newLocationLatitudeEdit;
        this.newLocationLongitudeEdit = newLocationLongitudeEdit;
        this.newLocationNameEdit = newLocationNameEdit;
        this.newLocationURIEdit = newLocationURIEdit;
        uriGenerator = new CompoundURIGenerator();
    }

    @Override
    public void onClick(View v) {

        String name = newLocationNameEdit.getText().toString();
        String longitude = newLocationLongitudeEdit.getText().toString();
        String latitude = newLocationLatitudeEdit.getText().toString();

        if(name.equals(""))
        {
            name = "UNSET";
        }
        if(longitude.equals(""))
        {
            longitude = "0";
        }
        if (latitude.equals("")) {
            latitude = "0";
        }


        newLocationURIEdit.setText(
                uriGenerator.generateLocationURI(name,
                        Double.parseDouble(longitude),
                        Double.parseDouble(latitude)
                        )
        );
    }
}
