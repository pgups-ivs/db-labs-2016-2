package ru.pgups.ivs.rglv.labs.db.dao;

import ru.pgups.ivs.rglv.labs.db.model.Film;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmsDAO extends AbstractDAO<Film> {
    private FilmCategoriesDAO categoriesDAO;
    private ActorsDAO actorsDAO;
    private LanguagesDAO languageDAO;

    public FilmsDAO(DataSource dataSource) {
        super(dataSource, "SELECT * FROM FILM ORDER BY title", "SELECT * FROM FILM WHERE FILM_ID=?",
                "INSERT INTO film (title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating) VALUES(?,?,?,?,?,?,?,?,?)",
                "UPDATE FILM set title=?, description=?, release_year=?, language_id=?, rental_duration=?, rental_rate=?, length=?, replacement_cost=?, rating=? WHERE film_id = ?",
                "DELETE FROM film WHERE film_id = ?");
    }

    @Override
    public Film readObject(ResultSet rs) throws SQLException {
        Film f = new Film();
        f.setId(rs.getLong("film_id"));
        f.setTitle(rs.getString("title"));
        f.setDescription(rs.getString("description"));
        f.setLenght(rs.getInt("length"));

        f.setMpaaRating(rs.getString("rating"));
        f.setReleaseYear(rs.getInt("release_year"));

        f.setRentalDuration(rs.getShort("rental_duration"));
        f.setRentalRate(rs.getDouble("rental_rate"));
        f.setReplacementCost(rs.getDouble("replacement_cost"));

        f.setLanguage(languageDAO.get(rs.getLong("language_id")));
        f.setCategories(categoriesDAO.listForFilm(f.getId()));
        f.setActors(actorsDAO.listForFilm(f.getId()));

        return f;
    }

    @Override
    public long save(Film obj) throws SQLException {
        long id = super.save(obj);
        actorsDAO.saveFilmActors(id, obj.getActors());
        categoriesDAO.saveFilmCategories(id, obj.getCategories());
        return id;
    }

    @Override
    protected int writeObject(Film obj, PreparedStatement ps, int idx) throws SQLException {
        ps.setString(idx++,obj.getTitle());
        ps.setString(idx++, obj.getDescription());
        ps.setInt(idx++, obj.getReleaseYear());

        if (obj.getLanguage() != null) {
            ps.setLong(idx++, obj.getLanguage().getId());
        } else {
            ps.setLong(idx++, 0);
        }

        ps.setInt(idx++, obj.getRentalDuration());
        ps.setDouble(idx++, obj.getRentalRate());
        ps.setInt(idx++, obj.getLenght());
        ps.setDouble(idx++, obj.getReplacementCost());
        ps.setString(idx++, obj.getMpaaRating());

        return idx;
    }

    @Override
    public void delete(long id) {
        super.delete(id);
        actorsDAO.deleteForFilm(id);
        categoriesDAO.deleteForFilm(id);
    }

    public FilmCategoriesDAO getCategoriesDAO() {
        return categoriesDAO;
    }

    public void setCategoriesDAO(FilmCategoriesDAO categoriesDAO) {
        this.categoriesDAO = categoriesDAO;
    }

    public ActorsDAO getActorsDAO() {
        return actorsDAO;
    }

    public void setActorsDAO(ActorsDAO actorsDAO) {
        this.actorsDAO = actorsDAO;
    }

    public LanguagesDAO getLanguageDAO() {
        return languageDAO;
    }

    public void setLanguageDAO(LanguagesDAO languageDAO) {
        this.languageDAO = languageDAO;
    }
}
