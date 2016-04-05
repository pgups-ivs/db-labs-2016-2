package ru.pgups.ivs.rglv.labs.db.dao;

import ru.pgups.ivs.rglv.labs.db.model.Language;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LanguagesDAO extends AbstractDAO<Language> {
    public LanguagesDAO(DataSource dataSource) {
        super(dataSource, "SELECT * FROM language ORDER BY name", "SELECT * FROM language WHERE language_id = ?");
    }

    @Override
    public Language readObject(ResultSet rs) throws SQLException {
        Language ret = new Language();
        ret.setId(rs.getLong("language_id"));
        ret.setLanguage(rs.getString("name"));
        return ret;
    }
}
