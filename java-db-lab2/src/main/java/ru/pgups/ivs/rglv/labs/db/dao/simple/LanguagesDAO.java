package ru.pgups.ivs.rglv.labs.db.dao.simple;

import ru.pgups.ivs.rglv.labs.db.dao.DAO;
import ru.pgups.ivs.rglv.labs.db.model.Language;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class LanguagesDAO implements DAO<Language> {
    private DataSource dataSource;

    public LanguagesDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Language> list() {
        List<Language> list = new LinkedList<>();

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM language ORDER BY name");
                ResultSet rs = preparedStatement.executeQuery();
        ) {
            while (rs.next()) {
                Language obj = new Language();
                obj.setId(rs.getLong("language_id"));
                obj.setLanguage(rs.getString("name"));

                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Language get(long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM language WHERE language_id = ?")
        ) {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Language obj = new Language();
                obj.setId(rs.getLong("language_id"));
                obj.setLanguage(rs.getString("name"));

                return obj;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public long save(Language obj) throws SQLException {
        if (obj.getId() == 0) {
            return insert(obj);
        } else {
            return update(obj);
        }
    }

    private long insert(Language obj) throws SQLException {
        long id = -1;

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO language (name) VALUES(?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            preparedStatement.setString(1, obj.getLanguage());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs != null && rs.next()) {
                id = rs.getLong(1);
                rs.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private long update(Language obj) throws SQLException {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE language SET name = ? WHERE language_id = ?")
        ) {
            preparedStatement.setString(1, obj.getLanguage());
            preparedStatement.setLong(2, obj.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj.getId();
    }

    @Override
    public void delete(long id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM language WHERE language_id = ?");
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
