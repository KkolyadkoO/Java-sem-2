package org.example.models;

public class City {
    private int id;
    private String name;
    private String countryCode;
    private String district;
    private int population;

    public City(int id, String name, String countryCode, String district, int population) {
        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
        this.district = district;
        this.population = population;
    }

    @Override
    public String toString() {
        return String.format("City{id=%d, name='%s', country='%s', district='%s', population=%d}",
                id, name, countryCode, district, population);
    }
}
