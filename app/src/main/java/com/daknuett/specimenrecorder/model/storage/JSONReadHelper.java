package com.daknuett.specimenrecorder.model.storage;

import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by daniel on 31.07.17.
 */

class JSONReadHelper {
    public static List readArray(JsonReader reader) throws IOException {
        List list = new LinkedList();
        reader.beginArray();
        while (reader.peek() != JsonToken.END_ARRAY)
        {
            list.add(JSONReadHelper.readObject(reader));
        }


        reader.endArray();
        return list;

    }

    @Nullable
    public static Object readObject(JsonReader reader) throws IOException {
        JsonToken token = reader.peek();
        System.err.println(token);
        if(token == JsonToken.BEGIN_OBJECT)
        {
            return JSONDocument.readJSON(reader);
        }
        else if (token == JsonToken.BOOLEAN)
        {
            return reader.nextBoolean();
        }
        else if (token == JsonToken.NUMBER)
        {
            return  reader.nextDouble();
        }
        else if (token == JsonToken.STRING)
        {
            return reader.nextString();
        }
        else if (token == JsonToken.NULL)
        {
            reader.nextNull();
            return null;
        }
        else if (token == JsonToken.BEGIN_ARRAY)
        {
            return JSONReadHelper.readArray(reader);
        }
        return "((ERR: UNKNOWN CLASS))";
    }
}
