package com.daknuett.specimenrecorder.model.settings;

import com.daknuett.specimenrecorder.model.JSONMismatchException;
import com.daknuett.specimenrecorder.model.storage.JSONDocument;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daniel on 01.08.17.
 */

public class SpeciesSettings {
    public String author,
                    locationURI;
    public boolean useTimestamp;

    public SpeciesSettings(String author, String locationURI, boolean useTimestamp) {
        this.author = author;
        this.locationURI = locationURI;
        this.useTimestamp = useTimestamp;
    }

    public JSONDocument toJSONDocument()
    {
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("__type__", "speciessettings");

        data.put("author", author);
        data.put("locationURI", locationURI);
        data.put("useTimestamp", useTimestamp);

        return new JSONDocument(data);
    }

    public static SpeciesSettings fromJSONDocument(JSONDocument document) throws JSONMismatchException {
        Map data = document.getData();
        System.err.println("[speciesSettings]" +  data);
        if(!data.containsKey("__type__") || !data.get("__type__").equals("speciessettings"))
        {
            throw new JSONMismatchException();
        }
        return new SpeciesSettings(
                (String) data.get("author"),
                (String) data.get("locationURI"),
                (boolean) data.get("useTimestamp")
        );
    }

    @Override
    public String toString() {
        return "SpeciesSettings{" +
                "author='" + author + '\'' +
                ", locationURI='" + locationURI + '\'' +
                ", useTimestamp=" + useTimestamp +
                '}';
    }
}
