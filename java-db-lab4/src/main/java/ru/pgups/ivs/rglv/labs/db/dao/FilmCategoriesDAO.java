package ru.pgups.ivs.rglv.labs.db.dao;

import ru.pgups.ivs.rglv.labs.db.model.FilmCategory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FilmCategoriesDAO extends AbstractCachingDAO<FilmCategory> {
    public static final String SELECT_BY_ID = "SELECT * FROM CATEGORY WHERE category_id = ?";
    public static final String SELECT_ALL = "SELECT * FROM CATEGORY ORDER BY name";
    public static final String SELECT_FOR_FILM = "SELECT C.* FROM CATEGORY c JOIN FILM_CATEGORY FC USING(category_id) WHERE film_id = ?";
    public static final String INSERT = "INSERT INTO CATEGORY (name) VALUES(?)";
    public static final String UPDATE = "UPDATE CATEGORY SET name = ? WHERE category_id = ?";
    public static final String DELETE = "DELETE FROM CATEGORY WHERE category_id = ?";

    public FilmCategoriesDAO(DataSource dataSource) {
        super(dataSource, SELECT_ALL, SELECT_BY_ID, INSERT, UPDATE, DELETE);
    }

    @Override
    public FilmCategory readObject(ResultSet rs) throws SQLException {
        FilmCategory s = new FilmCategory();
        s.setId(rs.getLong("category_id"));
        s.setName(rs.getString("name"));
        return s;
    }

    @Override
    protected int writeObject(FilmCategory obj, PreparedStatement ps, int idx) throws SQLException {
        ps.setString(idx++, obj.getName());
        return idx;
    }

    public List<FilmCategory> listForFilm(long id) {
        return this.listWithIdParameter(SELECT_FOR_FILM, id);
    }

    public void saveFilmCategories(long filmId, List<FilmCategory> categories) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM film_category WHERE film_id = ?");
            ps.setLong(1, filmId);
            ps.executeUpdate();

            ps.close();

            ps = connection.prepareStatement("INSERT INTO film_category (film_id, category_id) VALUES (?,?)");
            for (FilmCategory category : categories) {
                ps.setLong(1, filmId);
                ps.setLong(2, category.getId());
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteForFilm(long filmId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM film_category WHERE film_id = ?");
            ps.setLong(1, filmId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
