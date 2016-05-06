package ru.pgups.ivs.rglv.labs.db.dao;

import ru.pgups.ivs.rglv.labs.db.model.Actor;
import ru.pgups.ivs.rglv.labs.db.model.FilmCategory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ActorsDAO extends AbstractCachingDAO<Actor> {
    public static final String SELECT_BY_ID = "SELECT * FROM ACTOR WHERE actor_id = ?";
    public static final String SELECT_ALL = "SELECT * FROM ACTOR ORDER BY last_name, first_name";
    public static final String SELECT_FOR_FILM = "SELECT A.* FROM ACTOR a JOIN film_actor fa USING (actor_id) WHERE film_id = ?";

    public static final String INSERT = "INSERT INTO ACTOR (last_name, first_name) VALUES (?,?)";
    public static final String UPDATE = "UPDATE ACTOR SET last_name = ?, first_name = ? where actor_id = ?";
    public static final String DELETE = "DELETE FROM ACTOR WHERE actor_id = ?";

    public ActorsDAO(DataSource dataSource) {
        super(dataSource, SELECT_ALL, SELECT_BY_ID, INSERT, UPDATE, DELETE);
    }

    public Actor readObject(ResultSet rs) throws SQLException {
        Actor s = new Actor();
        s.setId(rs.getLong("actor_id"));
        s.setFirstName(rs.getString("first_name"));
        s.setLastName(rs.getString("last_name"));
        return s;
    }

    @Override
    protected int writeObject(Actor obj, PreparedStatement ps, int idx) throws SQLException {
        ps.setString(idx++, obj.getLastName());
        ps.setString(idx++, obj.getFirstName());

        return idx;
    }

    public List<Actor> listForFilm(long id) {
        return this.listWithIdParameter(SELECT_FOR_FILM, id);
    }

    public void saveFilmActors(long filmId, List<Actor> actors) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM film_actor WHERE film_id = ?");
            ps.setLong(1, filmId);
            ps.executeUpdate();

            ps.close();

            ps = connection.prepareStatement("INSERT INTO film_actor (film_id, actor_id) VALUES (?,?)");
            for (Actor actor : actors) {
                ps.setLong(1, filmId);
                ps.setLong(2, actor.getId());
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
            PreparedStatement ps = connection.prepareStatement("DELETE FROM film_actor WHERE film_id = ?");
            ps.setLong(1, filmId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
