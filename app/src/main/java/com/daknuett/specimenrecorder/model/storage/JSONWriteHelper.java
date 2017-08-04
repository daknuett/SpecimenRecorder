package com.daknuett.specimenrecorder.model.storage;

import android.util.JsonWriter;

import com.daknuett.specimenrecorder.R;

import java.io.IOException;
import java.util.List;

/**
 * Created by daniel on 31.07.17.
 */

public class JSONWriteHelper {
    public static void writeObject(JsonWriter writer, String name, Object obj)
            throws java.io.IOException
    {
        if(obj instanceof String)
        {
            writer.name(name).value((String) obj);
            return;
        }

        if(obj instanceof Number)
        {
            writer.name(name).value((Number)obj);
            return;
        }

        if(obj instanceof JSONDocument)
        {
            writer.name(name);

            JSONDocument document = (JSONDocument) obj;
            document.writeJSON(writer);

            return;
        }

        if(obj instanceof List)
        {
            writer.name(name);
            writer.beginArray();
            for (Object o: (List)obj)
            {
                    JSONWriteHelper.writeArrayObject(writer, o);
            }
            writer.endArray();
        }

        writer.name(name).value("((ERR: not serializable))");
    }

    private static void writeArrayObject(JsonWriter writer, Object obj) throws IOException {

        if(obj instanceof String)
        {
            writer.value((String) obj);
            return;
        }

        if(obj instanceof Number)
        {
            writer.value((Number)obj);
            return;
        }

        if(obj instanceof JSONDocument)
        {

            JSONDocument document = (JSONDocument) obj;
            document.writeJSON(writer);

            return;
        }

        if(obj instanceof List)
        {
            writer.beginArray();
            for (Object o: (List)obj)
            {
                JSONWriteHelper.writeArrayObject(writer, o);
            }
            writer.endArray();
        }

        writer.value("((ERR: not serializable))");
    }

    public static void writeObject(JsonWriter writer, String name, int obj)
        throws java.io.IOException
    {
        writer.name(name).value(obj);
    }
    public static void writeObject(JsonWriter writer, String name, double obj)
            throws java.io.IOException
    {
        writer.name(name).value(obj);
    }
    public static void writeObject(JsonWriter writer, String name, float obj)
            throws java.io.IOException
    {
        writer.name(name).value(obj);
    }
    public static void writeObject(JsonWriter writer, String name, boolean obj)
            throws java.io.IOException
    {
        writer.name(name).value(obj);
    }


    public static void writeArrayObject(JsonWriter writer, int obj)
            throws java.io.IOException
    {
        writer.value(obj);
    }
    public static void writeArrayObject(JsonWriter writer, double obj)
            throws java.io.IOException
    {
        writer.value(obj);
    }
    public static void writeArrayObject(JsonWriter writer, float obj)
            throws java.io.IOException
    {
        writer.value(obj);
    }
    public static void writeArrayObject(JsonWriter writer, boolean obj)
            throws java.io.IOException
    {
        writer.value(obj);
    }
}
