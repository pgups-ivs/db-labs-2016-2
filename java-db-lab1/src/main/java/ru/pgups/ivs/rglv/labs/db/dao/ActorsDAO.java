package ru.pgups.ivs.rglv.labs.db.dao;

import ru.pgups.ivs.rglv.labs.db.model.Actor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ActorsDAO {
    private DataSource dataSource;

    public ActorsDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Actor> getAllActors() {
        List<Actor> actors = new LinkedList<>();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ACTOR ORDER BY last_name, first_name");

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                actors.add(readObject(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return actors;
    }

    public Actor get(long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ACTOR WHERE id = ?");
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return readObject(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Actor readObject(ResultSet rs) throws SQLException {
        Actor s = new Actor();
        s.setId(rs.getLong("actor_id"));
        s.setFirstName(rs.getString("first_name"));
        s.setLastName(rs.getString("last_name"));
        return s;
    }
}
