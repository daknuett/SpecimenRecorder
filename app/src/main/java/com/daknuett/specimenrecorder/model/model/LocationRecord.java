package com.daknuett.specimenrecorder.model.model;

import com.daknuett.specimenrecorder.model.JSONMismatchException;
import com.daknuett.specimenrecorder.model.storage.JSONDocument;
import com.daknuett.specimenrecorder.model.storage.JSONDocumentBase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by daniel on 31.07.17.
 */

public class LocationRecord {
    private double latitude,
                    longitude,
                    radius;
    private String name,
                    description,
                    identifier,
                    country;
    private String[] tags;
    private String address;
    private String imageURI;
    private Double[][] colorMap;


    public LocationRecord(double latitude, double longitude, double radius, String name, String description, String identifier, String country, String[] tags, String address, String imageURI, Double[][] colorMap) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.name = name;
        this.description = description;
        this.identifier = identifier;
        this.country = country;
        this.tags = tags;
        this.address = address;
        this.imageURI = imageURI;
        this.colorMap = colorMap;
    }

    public JSONDocument toJSONDocument()
    {
        Map <String, Object> data = new HashMap<String, Object>();

        data.put("__type__", "locationrecord");



        data.put("latitude", latitude);
        data.put("longitude", longitude);
        data.put("radius", radius);
        data.put("name", name);
        data.put("description", description);
        data.put("identifier", identifier);
        data.put("country", country);
        data.put("tags", new LinkedList<String>(Arrays.asList(tags)));
        data.put("address", address);
        data.put("imageURI", imageURI);
        data.put("colorMap", colorMap);
        return new JSONDocument(data);
    }

    public static LocationRecord fromJSONDocument(JSONDocument document) throws JSONMismatchException {
        Map data = document.getData();
        if(!data.containsKey("__type__") || !data.get("__type__").equals("locationrecord"))
        {
            throw new JSONMismatchException();
        }

        return new LocationRecord(
                (double) data.get("latitude"),
                (double) data.get("longitude"),
                (double) data.get("radius"),
                (String) data.get("name"),
                (String) data.get("description"),
                (String) data.get("identifier"),
                (String) data.get("country"),
                (String[])((LinkedList)data.get("tags")).toArray(),
                (String) data.get("address"),
                (String) data.get("imageURI"),
                (Double[][])((LinkedList)data.get("colorMap")).toArray()
        );
    }

    public static List<LocationRecord> fromJSONDB(JSONDocumentBase db)
    {
        List<LocationRecord> recordList = new LinkedList<LocationRecord>();

        for (JSONDocument document: db.getDocuments())
        {
            try {
                recordList.add(LocationRecord.fromJSONDocument(document));
            } catch (JSONMismatchException e) {
                // do nothing. this is a document storage!
            }
        }
        return recordList;
    }


    public String getIdentifier() {
        return identifier;
    }
}
