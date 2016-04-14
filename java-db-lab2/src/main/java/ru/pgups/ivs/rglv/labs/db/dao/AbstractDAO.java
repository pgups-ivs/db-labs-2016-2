package ru.pgups.ivs.rglv.labs.db.dao;

import ru.pgups.ivs.rglv.labs.db.model.Identifiable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractDAO<T extends Identifiable> implements DAO<T> {
    protected DataSource dataSource;

    protected String GET_ALL_QUERY;
    protected String GET_BY_ID_QUERY;
    protected String INSERT;
    protected String UPDATE;
    protected String DELETE_BY_ID;

    public AbstractDAO(DataSource dataSource, String getAllQuery, String getByIdQuery,
                       String insert, String update, String deleteById) {
        this.dataSource = dataSource;
        this.GET_ALL_QUERY = getAllQuery;
        this.GET_BY_ID_QUERY = getByIdQuery;
        this.INSERT = insert;
        this.UPDATE = update;
        this.DELETE_BY_ID = deleteById;
    }

    @Override
    public T get(long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(GET_BY_ID_QUERY);
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

    protected abstract T readObject(ResultSet rs) throws SQLException;

    protected abstract int writeObject(T obj, PreparedStatement ps, int idx) throws SQLException;

    protected void writeObject(T obj, PreparedStatement ps, boolean forInsert) throws SQLException {
        int idx = 1;
        if (forInsert) {
            // you do not need to set ID because it is auto-generated
            // ps.setLong(idx++, obj.getId());
        }
        idx = writeObject(obj, ps, idx);
        if (!forInsert) {
            ps.setLong(idx, obj.getId());
        }

    }

    @Override
    public List<T> list() {
        return list(GET_ALL_QUERY);
    }

    public List<T> list(String query) {
        List<T> ret = new LinkedList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ret.add(readObject(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    protected List<T> listWithIdParameter(String query, long id) {
        List<T> ret = new LinkedList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ret.add(readObject(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    @Override
    public long save(T obj) throws SQLException {
        if (obj.getId() == 0) {
            return insert(obj);
        } else {
            return update(obj);
        }
    }

    protected long insert(T obj) {
        long id = -1;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        ) {
            writeObject(obj, preparedStatement, true);

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

    protected long update(T obj) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
        ) {
            writeObject(obj, preparedStatement, false);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj.getId();
    }

    public void delete(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID);
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
