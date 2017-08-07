package com.daknuett.specimenrecorder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daknuett.specimenrecorder.R;
import com.daknuett.specimenrecorder.model.FirstRunException;
import com.daknuett.specimenrecorder.model.model.RecordDatabase;
import com.daknuett.specimenrecorder.model.settings.SettingsStorageManager;

import java.io.IOException;

/**
 * Created by daniel on 06.08.17.
 */

public class LocationRecordListFragment
extends MyFragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_locationrecordlist, container, false);

        loadStorage(getArguments());
        return rootView;
    }
}
