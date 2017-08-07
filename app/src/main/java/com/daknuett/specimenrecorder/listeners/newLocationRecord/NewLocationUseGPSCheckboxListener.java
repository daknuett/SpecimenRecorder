package com.daknuett.specimenrecorder.listeners.newLocationRecord;

import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * Created by daniel on 07.08.17.
 */

public class NewLocationUseGPSCheckboxListener
 implements CompoundButton.OnCheckedChangeListener
{
    private EditText newLocationLatitudeEdit;
    private EditText newLocationLongitudeEdit;

    public NewLocationUseGPSCheckboxListener(EditText newLocationLatitudeEdit, EditText newLocationLongitudeEdit) {
        this.newLocationLatitudeEdit = newLocationLatitudeEdit;
        this.newLocationLongitudeEdit = newLocationLongitudeEdit;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
        {
            newLocationLatitudeEdit.setEnabled(false);
            newLocationLatitudeEdit.setAlpha(.5f);
            newLocationLongitudeEdit.setEnabled(false);
            newLocationLongitudeEdit.setAlpha(.5f);
            newLocationLatitudeEdit.setFocusable(false);
            newLocationLongitudeEdit.setFocusable(false);
        }
        else
        {
            newLocationLatitudeEdit.setEnabled(true);
            newLocationLatitudeEdit.setAlpha(1);
            newLocationLongitudeEdit.setEnabled(true);
            newLocationLongitudeEdit.setAlpha(1);
            newLocationLatitudeEdit.setFocusableInTouchMode(true);
            newLocationLongitudeEdit.setFocusableInTouchMode(true);
            newLocationLatitudeEdit.setFocusable(true);
            newLocationLongitudeEdit.setFocusable(true);
            newLocationLatitudeEdit.findFocus();
        }
    }
}
