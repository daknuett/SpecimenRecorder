package com.daknuett.specimenrecorder.model.storage;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.JsonWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by daniel on 30.07.17.
 */

public class JSONDocumentBase {
    List<JSONDocument> documents;

    public JSONDocumentBase(List<JSONDocument> docs)
    {
        documents = docs;
    }
    public JSONDocumentBase()
    {
        documents = new LinkedList<JSONDocument>();
    }

    public void addDocument(JSONDocument document)
    {
        documents.add(document);
    }

    public void writeJSON(JsonWriter writer)
            throws IOException
    {
        writer.beginArray();
        for (JSONDocument document: documents)
        {
                document.writeJSON(writer);
        }
        writer.endArray();
    }
    public void dump(OutputStream out)
            throws IOException
    {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writeJSON(writer);
        writer.close();
    }
    public static JSONDocumentBase readJSON(JsonReader reader) throws IOException {
        List<JSONDocument> data = new LinkedList<>();

        reader.beginArray();

        while (reader.peek() != JsonToken.END_ARRAY)
        {
            data.add(JSONDocument.readJSON(reader));
        }
        reader.endArray();

        return new JSONDocumentBase(data);
    }

    public static JSONDocumentBase load(InputStream in) throws IOException {
        JSONDocumentBase result;
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            result = JSONDocumentBase.readJSON(reader);
        }
        catch (IOException e)
        {
            result = new JSONDocumentBase();
        }
        finally {
            reader.close();
        }
        return result;


    }

    public List<JSONDocument> getDocuments() {
        return documents;
    }
}
