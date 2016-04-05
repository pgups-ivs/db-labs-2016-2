package ru.pgups.ivs.rglv.labs.db.dao;

import ru.pgups.ivs.rglv.labs.db.model.Identifiable;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCachingDAO<T extends Identifiable> extends AbstractDAO<T> {
    private Map<Long, T> cache;

    public AbstractCachingDAO(DataSource dataSource, String getAllQuery, String getByIdQuery) {
        super(dataSource, getAllQuery, getByIdQuery);
        this.cache = new HashMap<>();
    }

    @Override
    public T get(long id) {
        if (!cache.containsKey(id)) {
            cache.put(id, super.get(id));
        }
        return cache.get(id);
    }

    @Override
    public List<T> list() {
        List<T> list = super.list();

        list.forEach(t -> cache.put(t.getId(), t));

        return list;
    }
}
