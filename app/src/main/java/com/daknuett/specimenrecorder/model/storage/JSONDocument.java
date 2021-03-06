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
            JSONWriteHelper.writeObject(writer, key, data.get(key));
        }
        writer.endObject();

    }

    public static JSONDocument readJSON(JsonReader reader) throws IOException {

        Map<String, Object> data = new HashMap<String, Object>();

        reader.beginObject();


        JsonToken token = reader.peek();
        while (token != JsonToken.END_OBJECT)
        {
            String name = reader.nextName();
            data.put(name, JSONReadHelper.readObject(reader));
            token = reader.peek();
        }
        reader.endObject();

        return new JSONDocument(data);
    }
    public Map getData()
    {
        return data;
    }

    @Override
    public String toString() {
        return "JSONDocument{" +
                "data=" + data +
                '}';
    }
}
