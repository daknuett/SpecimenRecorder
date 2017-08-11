package com.daknuett.specimenrecorder.fragments;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.daknuett.specimenrecorder.R;
import com.daknuett.specimenrecorder.listeners.locationRecordList.LocationListListener;
import com.daknuett.specimenrecorder.model.FirstRunException;
import com.daknuett.specimenrecorder.model.model.LocationRecord;
import com.daknuett.specimenrecorder.model.model.RecordDatabase;
import com.daknuett.specimenrecorder.model.settings.SettingsStorageManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by daniel on 06.08.17.
 */

public class LocationRecordListFragment
extends MyFragment
{
    List<LocationRecord> recordList = null;
    List<Map<String, String>> recordMapList = new LinkedList<>();
    Activity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_locationrecordlist, container, false);

        loadStorage(getArguments());




        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        System.err.println("INSIDE: onActivityCreated");

        activity = getActivity();

        ListView locationListView = (ListView) activity.findViewById(R.id.location_list_view);




        try {
            recordList = recordDatabase.getAllLocationRecords();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println("recordList: " + recordList);
        if(recordList != null)
        {
            recordMapList = new LinkedList<>();
            for (LocationRecord record :
                    recordList) {
                Map<String, String> map = new HashMap<>();
                System.err.println("name " + record.getName());
                map.put("name", record.getName());
                map.put("identifier", record.getIdentifier());
                map.put("latitude", String.valueOf(record.getLatitude()));
                map.put("longitude", String.valueOf(record.getLongitude()));

                recordMapList.add(map);
            }
        }
        else
        {
            System.out.println("WARN: recordList is empty.");
        }

        SimpleAdapter listAdapter = new SimpleAdapter(
                activity,
                recordMapList,
                R.layout.location_list_item,
                new String[]{"name", "identifier", "latitude", "longitude"},
                new int[]{R.id.location_name, R.id.location_id, R.id.location_latitude, R.id.location_longitude}
        );

        locationListView.setAdapter(listAdapter);

        RadioButton editElementRadioButton = (RadioButton) activity.findViewById(R.id.location_list_modify_item) ;


        locationListView.setOnItemClickListener(new LocationListListener(activity,
                this,
                recordDatabase,
                editElementRadioButton,
                getArguments().getString(ARG_STORAGE_PATH),
                getArguments().getString(ARG_DATA_PATH)
                ));
    }
}
