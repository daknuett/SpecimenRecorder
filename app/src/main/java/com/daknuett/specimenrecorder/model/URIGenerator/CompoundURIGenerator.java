package com.daknuett.specimenrecorder.model.URIGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by daniel on 07.08.17.
 */

public class CompoundURIGenerator
        extends URIGenerator
{
    private URIGenerator fallback;

    public CompoundURIGenerator()
    {
        fallback = new IncrementingURIGenerator(new Random().nextLong());
    }

    @Override
    public String generateLocationImageURI() {
        return fallback.generateLocationImageURI();
    }

    @Override
    public String generateLocationImageURI(String name, double longitude, double latitude) {
        return concatStringsURISafe(new String[]{"locationimage-", name, String.valueOf(longitude), String.valueOf(latitude)});
    }

    @Override
    public String generateSpeciesImageURI() {
        return fallback.generateSpeciesImageURI();
    }

    @Override
    public String generateSpeciesImageURI(String genus, String species, long timestamp) {
        return concatStringsURISafe(new String[]{"speciesimage-", genus, species, String.valueOf(timestamp)});
    }

    @Override
    public String generateLocationURI() {
        return fallback.generateLocationURI();
    }

    @Override
    public String generateLocationURI(String name, double longitude, double latitude) {
        return concatStringsURISafe(new String[]{"location-", name, String.valueOf(longitude), String.valueOf(latitude)});
    }

    @Override
    public String generateSpeciesURI() {
        return fallback.generateSpeciesURI();
    }

    @Override
    public String generateSpeciesURI(String genus, String species, long timestamp) {
        return concatStringsURISafe(new String[]{"species-", genus, species, String.valueOf(timestamp)});
    }

    private String concatStringsURISafe(String [] strings)
    {
        String concat = "";
        for (String thisString:
             strings) {
            String valid = "";
            for(char thisChar:
                    thisString.toCharArray())
            {
                if(Character.isISOControl(thisChar))
                {
                    valid += "_";
                } else
                if(Character.isSpaceChar(thisChar))
                {
                    valid += "_";
                }
                else
                {
                    valid += thisChar;
                }
            }
            concat += valid;
        }
        return concat;
    }
}
