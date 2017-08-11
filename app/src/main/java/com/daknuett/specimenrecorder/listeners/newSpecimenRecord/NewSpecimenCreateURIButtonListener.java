package com.daknuett.specimenrecorder.listeners.newSpecimenRecord;

import android.view.View;
import android.widget.EditText;

import com.daknuett.specimenrecorder.model.URIGenerator.CompoundURIGenerator;
import com.daknuett.specimenrecorder.model.URIGenerator.URIGenerator;

import java.util.Date;

/**
 * Created by daniel on 07.08.17.
 */

public class NewSpecimenCreateURIButtonListener implements View.OnClickListener {

    private EditText newSpecimenGenusEdit,
                    newSpecimenSpeciesEdit,
                    newSpecimenURIEdit;
    private URIGenerator uriGenerator;


    public NewSpecimenCreateURIButtonListener(EditText newSpecimenGenusEdit, EditText newSpecimenSpeciesEdit, EditText newSpecimenURIEdit) {
        this.newSpecimenGenusEdit = newSpecimenGenusEdit;
        this.newSpecimenSpeciesEdit = newSpecimenSpeciesEdit;
        this.newSpecimenURIEdit = newSpecimenURIEdit;
        uriGenerator = new CompoundURIGenerator();

    }

    @Override
    public void onClick(View v) {

        String species = newSpecimenSpeciesEdit.getText().toString();
        String genus = newSpecimenGenusEdit.getText().toString();
        long timestamp = System.currentTimeMillis() / 1000L;

        if(species.equals(""))
        {
            species = "UNSET";
        }
        if(genus.equals(""))
        {
            genus = "UNSET";
        }

        newSpecimenURIEdit.setText(
                uriGenerator.generateSpeciesURI(
                        genus, species, timestamp
                        )
        );
    }
}
