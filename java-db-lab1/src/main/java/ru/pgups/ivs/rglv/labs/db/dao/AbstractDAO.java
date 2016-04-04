package ru.pgups.ivs.rglv.labs.db.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractDAO<T> implements DAO<T> {
    protected DataSource dataSource;

    protected String GET_ALL_QUERY;
    protected String GET_BY_ID_QUERY;

    public AbstractDAO(DataSource dataSource, String getAllQuery, String getByIdQuery) {
        this.dataSource = dataSource;
        this.GET_ALL_QUERY = getAllQuery;
        this.GET_BY_ID_QUERY = getByIdQuery;
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

    @Override
    public List<T> list() {
        List<T> ret = new LinkedList<>();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_QUERY);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ret.add(readObject(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
