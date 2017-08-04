package com.daknuett.specimenrecorder.model.settings;

import com.daknuett.specimenrecorder.model.JSONMismatchException;
import com.daknuett.specimenrecorder.model.storage.JSONDocument;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daniel on 01.08.17.
 */

public class LocationSettings {
    public double radius;
    public boolean useGPS;
    public String country;

    public LocationSettings(double radius, boolean useGPS, String country) {
        this.radius = radius;
        this.useGPS = useGPS;
        this.country = country;
    }

    public JSONDocument toJSONDocument()
    {
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("__type__", "locationsettings");

        data.put("radius", radius);
        data.put("useGPS", useGPS);
        data.put("country", country);

        return new JSONDocument(data);
    }

    public static LocationSettings fromJSONDocument(JSONDocument document) throws JSONMismatchException {
        Map data = document.getData();

        if(!data.containsKey("__type__") || data.get("__type__") == "locationsettings")
        {
            throw new JSONMismatchException();
        }

        return new LocationSettings(
                (double) data.get("radius"),
                (boolean) data.get("useGPS"),
                (String) data.get("country")
        );
    }
}
