package com.daknuett.specimenrecorder.listeners.locationRecordList;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;

import com.daknuett.specimenrecorder.activities.EditLocationActivity;
import com.daknuett.specimenrecorder.model.model.LocationRecord;
import com.daknuett.specimenrecorder.model.model.RecordDatabase;

import java.util.List;

/**
 * Created by daniel on 08.08.17.
 */

public class LocationListListener
        implements AdapterView.OnItemClickListener {

    private Activity activity;
    private Fragment fragment;
    private RecordDatabase recordDatabase;
    private RadioButton editElementRadioButton;
    private String path;
    private String databasePath;

    public LocationListListener(Activity activity,
                                Fragment fragment,
                                RecordDatabase recordDatabase,
                                RadioButton editElementRadioButton,
                                String path,
                                String databasePath) {
        this.activity = activity;
        this.fragment = fragment;
        this.recordDatabase = recordDatabase;
        this.editElementRadioButton = editElementRadioButton;
        this.path = path;
        this.databasePath = databasePath;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if(editElementRadioButton.isChecked())
        {
            Intent startActivityIntent = new Intent(this.activity, EditLocationActivity.class);
            startActivityIntent.putExtra("record_database_path", databasePath);
            startActivityIntent.putExtra("path", path);
            startActivityIntent.putExtra("record_number", position);

            activity.startActivity(startActivityIntent);
        }
    }
}
