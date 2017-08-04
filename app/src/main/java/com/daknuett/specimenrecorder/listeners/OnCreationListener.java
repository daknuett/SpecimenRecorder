package com.daknuett.specimenrecorder.listeners;

import com.daknuett.specimenrecorder.model.model.RecordDatabase;

/**
 * Created by daniel on 01.08.17.
 */

public class OnCreationListener {
    public RecordDatabase loadInitial(String databasePrefix)
    {
        return RecordDatabase.load(databasePrefix);
    }

    //public void initSpeciesList()
}
