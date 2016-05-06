package ru.pgups.ivs.rglv.labs.db.model;

public class FilmCategory implements Identifiable {
    private long id;
    private String name;

    public FilmCategory() {
    }

    public FilmCategory(String name) {
        this.name = name;
    }

    public FilmCategory(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilmCategory that = (FilmCategory) o;

        if (id != that.id) return false;
        return name.equals(that.name);

    }
}
