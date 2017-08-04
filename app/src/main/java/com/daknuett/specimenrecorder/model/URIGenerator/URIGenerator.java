package com.daknuett.specimenrecorder.model.URIGenerator;

/**
 * Created by daniel on 01.08.17.
 */

public abstract class URIGenerator {
    public abstract String generateLocationImageURI();
    public abstract String generateLocationImageURI(String name, double longitude, double latitude);
    public abstract String generateSpeciesImageURI();
    public abstract String generateSpeciesImageURI(String genus, String species, long timestamp);

    public abstract String generateLocationURI();
    public abstract String generateLocationURI(String name, double longitude, double latitude);
    public abstract String generateSpeciesURI();
    public abstract String generateSpeciesURI(String genus, String species, long timestamp);
}
