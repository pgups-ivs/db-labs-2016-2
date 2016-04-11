package ru.pgups.ivs.rglv.labs.db.dao;

import ru.pgups.ivs.rglv.labs.db.model.Film;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
                "INSERT INTO film VALUES(?,?)",
                "UPDATE FILM set ... WHERE film_id = ?",
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
        throw new UnsupportedOperationException();
    }

    @Override
    protected int writeObject(Film obj, PreparedStatement ps, int idx) throws SQLException {
        throw new UnsupportedOperationException();
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
