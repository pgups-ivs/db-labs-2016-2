package ru.pgups.ivs.rglv.labs.db.model;

public class Country implements Identifiable {
    private long id;
    private String country;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
