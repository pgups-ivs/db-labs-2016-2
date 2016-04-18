package ru.pgups.ivs.rglv.labs.db.dao;

import ru.pgups.ivs.rglv.labs.db.model.Language;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LanguagesDAO extends AbstractCachingDAO<Language> {
    public LanguagesDAO(DataSource dataSource) {
        super(dataSource, "SELECT * FROM language ORDER BY name", "SELECT * FROM language WHERE language_id = ?",
                "INSERT INTO language (name) VALUES(?)", "UPDATE language SET name = ? WHERE language_id = ?", "DELETE FROM language WHERE language_id = ?");
    }

    @Override
    public Language readObject(ResultSet rs) throws SQLException {
        Language ret = new Language();
        ret.setId(rs.getLong("language_id"));
        ret.setLanguage(rs.getString("name"));
        return ret;
    }

    @Override
    protected int writeObject(Language obj, PreparedStatement ps, int idx) throws SQLException {
        ps.setString(idx++, obj.getLanguage());
        return idx;
    }
}
