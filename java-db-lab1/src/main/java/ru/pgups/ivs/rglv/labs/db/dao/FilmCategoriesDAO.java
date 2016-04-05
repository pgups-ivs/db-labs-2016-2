package ru.pgups.ivs.rglv.labs.db.dao;

import ru.pgups.ivs.rglv.labs.db.model.FilmCategory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FilmCategoriesDAO extends AbstractDAO<FilmCategory> {
    public static final String SELECT_BY_ID = "SELECT * FROM CATEGORY WHERE id = ?";
    public static final String SELECT_ALL = "SELECT * FROM CATEGORY ORDER BY name";
    public static final String SELECT_FOR_FILM = "SELECT C.* FROM CATEGORY c JOIN FILM_CATEGORY FC USING(category_id) WHERE film_id = ?";

    public FilmCategoriesDAO(DataSource dataSource) {
        super(dataSource, SELECT_ALL, SELECT_BY_ID);
    }

    @Override
    public FilmCategory readObject(ResultSet rs) throws SQLException {
        FilmCategory s = new FilmCategory();
        s.setId(rs.getLong("category_id"));
        s.setName(rs.getString("name"));
        return s;
    }

    public List<FilmCategory> listForFilm(long id) {
        return this.listWithIdParameter(SELECT_FOR_FILM, id);
    }
}
