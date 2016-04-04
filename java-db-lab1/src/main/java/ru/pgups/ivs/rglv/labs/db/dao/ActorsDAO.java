package ru.pgups.ivs.rglv.labs.db.dao;

import ru.pgups.ivs.rglv.labs.db.model.Actor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActorsDAO extends AbstractDAO<Actor> {
    public static final String SELECT_BY_ID = "SELECT * FROM ACTOR WHERE id = ?";
    public static final String SELECT_ALL = "SELECT * FROM ACTOR ORDER BY last_name, first_name";

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
}
