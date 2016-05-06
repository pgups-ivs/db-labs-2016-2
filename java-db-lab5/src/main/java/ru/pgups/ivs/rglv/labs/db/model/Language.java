package ru.pgups.ivs.rglv.labs.db.model;

public class Language implements Identifiable {
    private long id;
    private String language;

    public Language() {
    }

    public Language(String language) {
        this.language = language;
    }

    public Language(long id, String language) {
        this.id = id;
        this.language = language;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
