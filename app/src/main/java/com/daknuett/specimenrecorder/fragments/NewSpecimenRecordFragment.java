package com.daknuett.specimenrecorder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daknuett.specimenrecorder.R;

/**
 * Created by daniel on 06.08.17.
 */

public class NewSpecimenRecordFragment
extends MyFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_newspecimenrecord, container, false);

        loadStorage(getArguments());
        return rootView;
    }
}
