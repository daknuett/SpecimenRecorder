package com.daknuett.specimenrecorder.model.model;

import com.daknuett.specimenrecorder.model.JSONMismatchException;
import com.daknuett.specimenrecorder.model.storage.JSONDocument;
import com.daknuett.specimenrecorder.model.storage.JSONDocumentBase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by daniel on 01.08.17.
 */

public class SpeciesRecord {
    private String author,
                    genus,
                    species,
                    subspecies,
                    nickname;
    private String imageURI,
                    locationURI,
                    identifier;

    private double timestamp;
    private double day,
                    month,
                    year,
                    hour,
                    minute;
    private double latitude, longitude;

    public SpeciesRecord(String author, String genus, String species, String subspecies, String nickname, String imageURI, String locationURI, String identifier, double timestamp, double day, double month, double year, double hour, double minute) {
        this.author = author;
        this.genus = genus;
        this.species = species;
        this.subspecies = subspecies;
        this.nickname = nickname;
        this.imageURI = imageURI;
        this.locationURI = locationURI;
        this.identifier = identifier;
        this.timestamp = timestamp;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    public SpeciesRecord(String author, String genus, String species, String subspecies, String nickname, String identifier, double day, double month, double year, double hour, double minute)
    {
        this.author = author;
        this.genus = genus;
        this.species = species;
        this.subspecies = subspecies;
        this.nickname = nickname;
        this.identifier = identifier;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;

        Locale locale = Locale.getDefault();

        String date_str = String.format(locale, "%2d-%2d-%04d %2d:%2d",
                (int) day,
                (int) month,
                (int) year,
                (int) hour,
                (int) minute);

        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm", locale);
        Date date;
        try {
             date = formatter.parse(date_str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }

        this.timestamp = date.getTime();

    }

    public SpeciesRecord(String author, String genus, String species, String subspecies, String nickname, String imageURI, String locationURI, String identifier, double timestamp) {
        this.author = author;
        this.genus = genus;
        this.species = species;
        this.subspecies = subspecies;
        this.nickname = nickname;
        this.imageURI = imageURI;
        this.locationURI = locationURI;
        this.identifier = identifier;
        this.timestamp = timestamp;


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((long) timestamp * 1000L);



        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.month = calendar.get(Calendar.MONTH) + 1;
        this.year = calendar.get(Calendar.YEAR);
        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.minute = calendar.get(Calendar.MINUTE);
    }

    public SpeciesRecord(String author, String genus, String species, String subspecies, String nickname, String imageURI, String locationURI, String identifier, double timestamp, double latitude, double longitude) {
        this.author = author;
        this.genus = genus;
        this.species = species;
        this.subspecies = subspecies;
        this.nickname = nickname;
        this.imageURI = imageURI;
        this.locationURI = locationURI;
        this.identifier = identifier;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((long) timestamp * 1000L);



        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.month = calendar.get(Calendar.MONTH) + 1;
        this.year = calendar.get(Calendar.YEAR);
        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.minute = calendar.get(Calendar.MINUTE);
    }

    public SpeciesRecord(String author, String genus, String species, String subspecies, String nickname, String imageURI, String locationURI, String identifier, double timestamp, double day, double month, double year, double hour, double minute, double latitude, double longitude) {
        this.author = author;
        this.genus = genus;
        this.species = species;
        this.subspecies = subspecies;
        this.nickname = nickname;
        this.imageURI = imageURI;
        this.locationURI = locationURI;
        this.identifier = identifier;
        this.timestamp = timestamp;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public JSONDocument toJSONDocument()
    {
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("__type__", "speciesrecord");

        data.put("author", author);
        data.put("genus", genus);
        data.put("species", species);
        data.put("subspecies", subspecies);
        data.put("nickname", nickname);
        data.put("imageURI", imageURI);
        data.put("locationURI", locationURI);
        data.put("identifier", identifier);
        data.put("timestamp", timestamp);
        data.put("day", day);
        data.put("month", month);
        data.put("year", year);
        data.put("hour", hour);
        data.put("minute", minute);
        data.put("latitude", latitude);
        data.put("longitude", longitude);

        return new JSONDocument(data);
    }

    public static SpeciesRecord fromJSONDocument(JSONDocument document) throws JSONMismatchException {
        Map data = document.getData();
        if(!data.containsKey("__type__") || !data.get("__type__").equals("speciesrecord"))
        {
            throw new JSONMismatchException();
        }

        return new SpeciesRecord(
                (String) data.get("author"),
                (String) data.get("genus"),
                (String) data.get("species"),
                (String) data.get("subspecies"),
                (String) data.get("nickname"),
                (String) data.get("imageURI"),
                (String) data.get("locationURI"),
                (String) data.get("identifier"),
                (double) data.get("timestamp"),
                (double) data.get("day"),
                (double) data.get("month"),
                (double) data.get("year"),
                (double) data.get("hour"),
                (double) data.get("minute"),
                (double) data.get("latitude"),
                (double) data.get("longitude")
        );
    }

    public static List<SpeciesRecord> fromJSONDB(JSONDocumentBase db)
    {
        List<SpeciesRecord> speciesRecords = new LinkedList<SpeciesRecord>();

        for (JSONDocument document :
                db.getDocuments()) {
            try {
                speciesRecords.add(SpeciesRecord.fromJSONDocument(document));
            } catch (JSONMismatchException e) {
                // do nothing. This is a document storage system.
            }
        }
        return speciesRecords;
    }


    public String getLocationURI() {
        return locationURI;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenus() {
        return genus;
    }

    public String getSpecies() {
        return species;
    }

    public String getSubspecies() {
        return subspecies;
    }

    public String getNickname() {
        return nickname;
    }

    public String getImageURI() {
        return imageURI;
    }

    public String getIdentifier() {
        return identifier;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public double getDay() {
        return day;
    }

    public double getMonth() {
        return month;
    }

    public double getYear() {
        return year;
    }

    public double getHour() {
        return hour;
    }

    public double getMinute() {
        return minute;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
