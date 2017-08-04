package com.daknuett.specimenrecorder.model.model;


import com.daknuett.specimenrecorder.model.JSONMismatchException;
import com.daknuett.specimenrecorder.model.storage.JSONDocument;
import com.daknuett.specimenrecorder.model.storage.JSONDocumentBase;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by daniel on 01.08.17.
 */

public class RecordDatabase {
    private String prefix,
                    filename;

    private int currentFileNo,
        totalFiles;

    public RecordDatabase(String filename, String prefix, int totalFiles)
    {
        this.filename = filename;
        this.prefix = prefix;
        this.totalFiles = totalFiles;
        this.currentFileNo = totalFiles;

    }

    public static RecordDatabase load(String prefix)
    {
        File file = new File(prefix + "/meta.json");
        InputStream stream;
        try {
             stream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            return new RecordDatabase("db", prefix, 0);
        }

        try {
            JSONDocumentBase db = JSONDocumentBase.load(stream);
            JSONDocument document = db.getDocuments().get(0);
            Map data = document.getData();
            return new RecordDatabase((String) data.get("filename"),
                    (String) data.get("prefix"),
                    (int) data.get("totalFiles")
                    );
        } catch (Exception e) {
            return new RecordDatabase("db", prefix, 0);
        }

    }

    public void dump() throws IOException {
        File file = new File(prefix + "/meta.json");
        OutputStream stream = new FileOutputStream(file);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("filename", filename);
        data.put("prefix", prefix);
        data.put("totalFiles", totalFiles);
        JSONDocument document = new JSONDocument(data);
        JSONDocumentBase base = new JSONDocumentBase();
        base.addDocument(document);

        base.dump(stream);
    }

    public void addRecord(LocationRecord record) throws IOException {
        File file = new File(prefix + "/" + filename + "." + currentFileNo + ".json");
        if(!file.exists())
        {
            OutputStream stream = new FileOutputStream(file);
            JSONDocumentBase db = new JSONDocumentBase();
            db.addDocument(record.toJSONDocument());
            db.dump(stream);
            stream.close();
            return;

        }

        if(file.getTotalSpace() > 8*1000)
        {
            currentFileNo++;
            totalFiles++;
            dump();
            addRecord(record);
        }

        InputStream streamIn = new FileInputStream(file);
        JSONDocumentBase db = JSONDocumentBase.load(streamIn);
        streamIn.close();

        OutputStream streamOut = new FileOutputStream(file);
        db.addDocument(record.toJSONDocument());
        db.dump(streamOut);

    }
    public void addRecord(SpeciesRecord record) throws IOException {
        File file = new File(prefix + "/" + filename + "." + currentFileNo + ".json");
        if(!file.exists())
        {
            OutputStream stream = new FileOutputStream(file);
            JSONDocumentBase db = new JSONDocumentBase();
            db.addDocument(record.toJSONDocument());
            db.dump(stream);
            stream.close();
            return;

        }

        if(file.getTotalSpace() > 8*1000)
        {
            currentFileNo++;
            dump();
            addRecord(record);
        }

        InputStream streamIn = new FileInputStream(file);
        JSONDocumentBase db = JSONDocumentBase.load(streamIn);
        streamIn.close();

        OutputStream streamOut = new FileOutputStream(file);
        db.addDocument(record.toJSONDocument());
        db.dump(streamOut);

    }

    public List<SpeciesRecord> getAllSpeciesRecords() throws IOException {
        List<SpeciesRecord> records = new LinkedList<>();

        for (int i = 0; i < totalFiles; i++)
        {
            File file = new File(prefix + "/" + filename + "." + i + ".json");
            InputStream streamIn = new FileInputStream(file);
            JSONDocumentBase db = JSONDocumentBase.load(streamIn);
            streamIn.close();

            for (JSONDocument document :
                    db.getDocuments()) {
                try {
                    records.add(SpeciesRecord.fromJSONDocument(document));
                } catch (JSONMismatchException e) {
                    // do nothing: document storage
                }
            }
        }

        return records;
    }

    public List<LocationRecord> getAllLocationRecords() throws IOException {
        List<LocationRecord> records = new LinkedList<>();

        for (int i = 0; i < totalFiles; i++)
        {
            File file = new File(prefix + "/" + filename + "." + i + ".json");
            InputStream streamIn = new FileInputStream(file);
            JSONDocumentBase db = JSONDocumentBase.load(streamIn);
            streamIn.close();

            for (JSONDocument document :
                    db.getDocuments()) {
                try {
                    records.add(LocationRecord.fromJSONDocument(document));
                } catch (JSONMismatchException e) {
                    // do nothing: document storage
                }
            }
        }

        return records;
    }

    public void exportByLocations(List<LocationRecord> locations, String zipFileName, String imagePrefix) throws IOException {
        JSONDocumentBase db = new JSONDocumentBase();
        int BUFFER_SIZE = 1000;

        for (LocationRecord location: locations
             ) {
            db.addDocument(location.toJSONDocument());
        }

        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFileName)));

        for (SpeciesRecord record: getAllSpeciesRecords()
             ) {
            for (LocationRecord location : locations
                    ) {
                if (record.getLocationURI().equals(location.getIdentifier())) {
                    File image = new File(imagePrefix + "/" + record.getImageURI());

                    if (image.exists()) {
                        ZipEntry entry = new ZipEntry(record.getImageURI());
                        out.putNextEntry(entry);
                        FileInputStream fi = new FileInputStream(image);
                        byte data[] = new byte[BUFFER_SIZE];
                        BufferedInputStream origin = new BufferedInputStream(fi, BUFFER_SIZE);
                        int count;
                        while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                            out.write(data, 0, count);
                        }
                        origin.close();
                    }

                    db.addDocument(record.toJSONDocument());

                }
            }
        }

        out.putNextEntry(new ZipEntry(filename + ".json"));
        db.dump(out);
        out.close();
    }
}
