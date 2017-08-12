package com.daknuett.specimenrecorder.listeners.specimenRecordList;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;

import com.daknuett.specimenrecorder.fragments.SpecimenRecordListFragment;
import com.daknuett.specimenrecorder.model.model.RecordDatabase;

/**
 * Created by daniel on 12.08.17.
 */

public class SpecimenListListener
        implements AdapterView.OnItemClickListener {
    private Activity activity;
    private Fragment fragment;
    private RecordDatabase recordDatabase;
    private RadioButton editElementRadioButton;
    private String path;
    private String databasePath;

    public SpecimenListListener(Activity activity, Fragment fragment, RecordDatabase recordDatabase, RadioButton editElementRadioButton, String path, String databasePath) {
        this.activity = activity;
        this.fragment = fragment;
        this.recordDatabase = recordDatabase;
        this.editElementRadioButton = editElementRadioButton;
        this.path = path;
        this.databasePath = databasePath;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
