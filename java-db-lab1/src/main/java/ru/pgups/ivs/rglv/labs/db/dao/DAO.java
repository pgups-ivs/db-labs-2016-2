package ru.pgups.ivs.rglv.labs.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T extends Object> {
    T get(long id);

    List<T> list();

    T readObject(ResultSet rs) throws SQLException;
}
