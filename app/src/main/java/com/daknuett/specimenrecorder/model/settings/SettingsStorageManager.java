package com.daknuett.specimenrecorder.model.settings;

import com.daknuett.specimenrecorder.model.FirstRunException;
import com.daknuett.specimenrecorder.model.JSONMismatchException;
import com.daknuett.specimenrecorder.model.storage.JSONDocument;
import com.daknuett.specimenrecorder.model.storage.JSONDocumentBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by daniel on 03.08.17.
 */

public class SettingsStorageManager {
    public LocationSettings locationSettings;
    public SpeciesSettings speciesSettings;
    private String filename;

    public SettingsStorageManager(String filename)
    {
        locationSettings = new LocationSettings(100, false, "DE");
        speciesSettings = new SpeciesSettings("EDITME", "EMPTY", true);
        this.filename = filename;
    }

    public SettingsStorageManager(LocationSettings locationSettings, SpeciesSettings speciesSettings, String filename) {
        this.locationSettings = locationSettings;
        this.speciesSettings = speciesSettings;
        this.filename = filename;
    }

    public void dump() throws IOException {
        JSONDocumentBase db = new JSONDocumentBase();
        db.addDocument(locationSettings.toJSONDocument());
        db.addDocument(speciesSettings.toJSONDocument());
        System.out.println(filename);

        File outputFile = new File(filename);
        if(!outputFile.exists())
        {
            outputFile.createNewFile();
        }

        FileOutputStream stream = new FileOutputStream(filename);
        db.dump(stream);
        stream.close();
    }

    public static SettingsStorageManager load(String filename) throws IOException, FirstRunException {

        System.out.println(filename);

        FileInputStream stream = null;
        try {
            stream = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FirstRunException();
        }
        JSONDocumentBase base = JSONDocumentBase.load(stream);
        LocationSettings locationSettings = null;
        SpeciesSettings speciesSettings = null;

        for (JSONDocument document :
                base.getDocuments()) {
            try {
                locationSettings = LocationSettings.fromJSONDocument(document);
                break;
            } catch (JSONMismatchException e) {
                // pass
            }
        }

        for (JSONDocument document :
                base.getDocuments()) {
            try {
                speciesSettings = SpeciesSettings.fromJSONDocument(document);
                break;
            } catch (JSONMismatchException e) {
                // pass
            }
        }
        if(locationSettings == null || speciesSettings == null)
        {
            throw new FirstRunException();
        }

        return new SettingsStorageManager(locationSettings, speciesSettings, filename);
    }

    @Override
    public String toString() {
        return "SettingsStorageManager{" +
                "locationSettings=" + locationSettings +
                ", speciesSettings=" + speciesSettings +
                ", filename='" + filename + '\'' +
                '}';
    }
}
