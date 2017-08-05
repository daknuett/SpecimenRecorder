package com.daknuett.specimenrecorder.model.storage;

import android.util.JsonWriter;

import com.daknuett.specimenrecorder.R;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by daniel on 31.07.17.
 */

public class JSONWriteHelper {


    public static void  writeObject(JsonWriter writer, String name, Object object) throws IOException {
        if(object instanceof Integer)
        {
            JSONWriteHelper.doWriteObject(writer, name, (Integer) object);
        } else
        if (object instanceof  Double)
        {
            JSONWriteHelper.doWriteObject(writer, name, (Double) object);
        } else
        if (object instanceof String)
        {
            JSONWriteHelper.doWriteObject(writer, name, (String) object);
        } else
        if  (object instanceof Boolean )
        {
            JSONWriteHelper.doWriteObject(writer, name, (Boolean) object);
        } else
        if (object instanceof List)
        {
            JSONWriteHelper.doWriteObject(writer, name, (List) object);
        } else
        if (object instanceof JSONDocument)
        {
            JSONWriteHelper.doWriteObject(writer, name, (JSONDocument) object);
        } else
        if (object instanceof Number)
        {
            JSONWriteHelper.doWriteObject(writer, name, (Number) object);
        }
    }
    

    public static void doWriteObject(JsonWriter writer, String name, int obj)
            throws java.io.IOException
    {
        writer.name(name).value(obj);
    }
    public static void doWriteObject(JsonWriter writer, String name, double obj)
            throws java.io.IOException
    {
        writer.name(name).value(obj);
    }
    public static void doWriteObject(JsonWriter writer, String name, float obj)
            throws java.io.IOException
    {
        writer.name(name).value(obj);
    }
    public static void doWriteObject(JsonWriter writer, String name, Boolean obj)
            throws java.io.IOException
    {
        writer.name(name).value(obj);
    }


    public static void doWriteObject(JsonWriter writer, String name, String obj) throws IOException {
        writer.name(name).value( obj);
    }
    public static void doWriteObject(JsonWriter writer, String name, Number obj) throws IOException {
        writer.name(name).value(obj);
    }
    public static void doWriteObject(JsonWriter writer, String name, JSONDocument obj) throws IOException {
        writer.name(name);
        obj.writeJSON(writer);
    }
    public static void doWriteObject(JsonWriter writer, String name, List obj) throws IOException {
        writer.name(name);
        writer.beginArray();
        for (Object o: (List)obj)
        {
            JSONWriteHelper.doWriteArrayElement(writer, o);
        }
        writer.endArray();
    }

    private static void  doWriteArrayElement(JsonWriter writer, Object object) throws IOException {
        if(object instanceof Integer)
        {
            JSONWriteHelper.writeArrayObject(writer, (Integer) object);
        } else
        if (object instanceof  Double)
        {
            JSONWriteHelper.writeArrayObject(writer, (Double) object);
        } else
        if (object instanceof String)
        {
            JSONWriteHelper.writeArrayObject(writer, (String) object);
        } else
        if  (object instanceof Boolean )
        {
            JSONWriteHelper.writeArrayObject(writer, (Boolean) object);
        } else
        if (object instanceof List)
        {
            JSONWriteHelper.writeArrayObject(writer, (List) object);
        } else
        if (object instanceof JSONDocument)
        {
            JSONWriteHelper.writeArrayObject(writer, (JSONDocument) object);
        } else
        if (object instanceof Number)
        {
            JSONWriteHelper.writeArrayObject(writer, (Number) object);
        }
    }

    public static void doWriteObject(JsonWriter writer, String name, Double obj) throws IOException {
        writer.name(name).value(obj);
    }


    public static void writeArrayObject(JsonWriter writer, String obj)
            throws java.io.IOException
    {
        writer.value(obj);
    }
    public static void writeArrayObject(JsonWriter writer, Double obj)
            throws java.io.IOException
    {
        writer.value(obj);
    }
    public static void writeArrayObject(JsonWriter writer, JSONDocument obj)
            throws java.io.IOException
    {
        obj.writeJSON(writer);
    }
    public static void writeArrayObject(JsonWriter writer, Number obj)
            throws java.io.IOException
    {
        writer.value(obj);
    }
    public static void writeArrayObject(JsonWriter writer, List obj)
            throws java.io.IOException {
        writer.beginArray();
        for (Object o : (List) obj) {
            JSONWriteHelper.doWriteArrayElement(writer, o);
        }
        writer.endArray();
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
