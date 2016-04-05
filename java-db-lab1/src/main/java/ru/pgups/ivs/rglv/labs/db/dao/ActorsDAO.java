package ru.pgups.ivs.rglv.labs.db.dao;

import ru.pgups.ivs.rglv.labs.db.model.Actor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ActorsDAO extends AbstractDAO<Actor> {
    public static final String SELECT_BY_ID = "SELECT * FROM ACTOR WHERE id = ?";
    public static final String SELECT_ALL = "SELECT * FROM ACTOR ORDER BY last_name, first_name";
    public static final String SELECT_FOR_FILM = "SELECT A.* FROM ACTOR a JOIN film_actor fa USING (actor_id) WHERE film_id = ?";

    public ActorsDAO(DataSource dataSource) {
        super(dataSource, SELECT_ALL, SELECT_BY_ID);
    }

    public Actor readObject(ResultSet rs) throws SQLException {
        Actor s = new Actor();
        s.setId(rs.getLong("actor_id"));
        s.setFirstName(rs.getString("first_name"));
        s.setLastName(rs.getString("last_name"));
        return s;
    }

    public List<Actor> listForFilm(long id) {
        return this.listWithIdParameter(SELECT_FOR_FILM, id);
    }
}
