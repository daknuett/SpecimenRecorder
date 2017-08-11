package com.daknuett.specimenrecorder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daknuett.specimenrecorder.R;

/**
 * Created by daniel on 06.08.17.
 */

public class SpecimenRecordListFragment
extends MyFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_specimenrecordlist, container, false);
        loadStorage(getArguments());
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }
}
