package ru.pgups.ivs.rglv.labs.db.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T extends Object> {
    T get(long id);

    List<T> list();

    long save(T obj) throws SQLException;

    void delete(long id) throws SQLException;
}
