package com.daknuett.specimenrecorder.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;

import com.daknuett.specimenrecorder.R;
import com.daknuett.specimenrecorder.listeners.locationRecordList.LocationListListener;
import com.daknuett.specimenrecorder.listeners.specimenRecordList.SpecimenListListener;
import com.daknuett.specimenrecorder.model.model.LocationRecord;
import com.daknuett.specimenrecorder.model.model.SpeciesRecord;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by daniel on 06.08.17.
 */

public class SpecimenRecordListFragment
extends MyFragment
{
    private Activity activity;
    private List<SpeciesRecord> recordList;
    List<Map<String, String>> recordMapList = new LinkedList<>();
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

        activity = getActivity();

        ListView locationListView = (ListView) activity.findViewById(R.id.specimen_list_view);

        try {
            recordList = recordDatabase.getAllSpeciesRecords();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println("recordList: " + recordList);
        if(recordList != null)
        {
            recordMapList = new LinkedList<>();
            for (SpeciesRecord record :
                    recordList) {
                Map<String, String> map = new HashMap<>();
                map.put("genus", record.getGenus());
                map.put("species", record.getSpecies());
                map.put("subspecies", record.getSubspecies());
                map.put("location_id", record.getLocationURI());
                map.put("specimen_id", record.getIdentifier());
                map.put("year", String.valueOf(record.getYear()));
                map.put("month", String.valueOf(record.getMonth()));
                map.put("day", String.valueOf(record.getDay()));
                map.put("hour", String.valueOf(record.getHour()));
                map.put("minute", String.valueOf(record.getMinute()));


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
                R.layout.specimen_list_item,
                new String[]{"genus", "species", "subspecies",
                            "location_id", "specimen_id",
                            "year", "month", "day",
                            "hour", "minute"},
                new int[]{R.id.specimen_genus, R.id.specimen_species, R.id.specimen_subspecies,
                            R.id.specimen_location_URI, R.id.specimen_identifier,
                            R.id.specimen_year, R.id.specimen_month, R.id.specimen_day,
                            R.id.specimen_hour, R.id.specimen_minute}
        );

        locationListView.setAdapter(listAdapter);

        RadioButton editElementRadioButton = (RadioButton) activity.findViewById(R.id.specimen_list_modify_item) ;


        locationListView.setOnItemClickListener(new SpecimenListListener(activity,
                this,
                recordDatabase,
                editElementRadioButton,
                getArguments().getString(ARG_STORAGE_PATH),
                getArguments().getString(ARG_DATA_PATH)
        ));


    }
}
