package com.daknuett.specimenrecorder.model.storage;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daniel on 30.07.17.
 */

public class JSONDocument {
    private Map<String, Object> data;
    public JSONDocument( Map<String, Object> values)
    {
        data = new HashMap(values);

    }

    public void writeJSON(JsonWriter writer) throws IOException

    {
        writer.beginObject();
        for (String key: data.keySet())
        {
            System.out.println("Writing " + key + ": " + data.get(key));
            JSONWriteHelper.writeObject(writer, key, data.get(key));
        }
        writer.endObject();

    }

    public static JSONDocument readJSON(JsonReader reader) throws IOException {

        Map<String, Object> data = new HashMap<String, Object>();
        JSONDocument result = new JSONDocument(data);
        reader.beginObject();


        JsonToken token = reader.peek();
        while (token != JsonToken.END_OBJECT)
        {
            String name = reader.nextName();
            data.put(name, JSONReadHelper.readObject(reader));
            token = reader.peek();
        }
        reader.endObject();
        return  result;
    }
    public Map getData()
    {
        return data;
    }
}
