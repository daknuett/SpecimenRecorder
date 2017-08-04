package com.daknuett.specimenrecorder.model.URIGenerator;

/**
 * Created by daniel on 01.08.17.
 */

public class IncrementingURIGenerator
extends URIGenerator
{
    private long offset;
    public IncrementingURIGenerator(long offset)
    {
        this.offset = offset;
    }

    public String generateLocationImageURI()
    {
        return "locationimage-" + offset++;
    }
    public String generateLocationImageURI(String name, double longitude, double latitude)
    {
        return generateLocationImageURI();
    }
    public String generateSpeciesImageURI()
    {
        return "speciesimage-" + offset++;
    }
    public String generateSpeciesImageURI(String genus, String species, long timestamp)
    {
        return generateSpeciesImageURI();
    }

    public String generateLocationURI()
    {
        return "location-" + offset++;
    }
    public String generateLocationURI(String name, double longitude, double latitude)
    {
        return generateLocationURI();
    }
    public String generateSpeciesURI()
    {
        return "species-" + offset++;
    }
    public String generateSpeciesURI(String genus, String species, long timestamp)
    {
        return generateSpeciesURI();
    }

    public long getOffset() {
        return offset;
    }
}
