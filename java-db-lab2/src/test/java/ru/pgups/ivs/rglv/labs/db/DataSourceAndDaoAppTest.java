package ru.pgups.ivs.rglv.labs.db;

import org.junit.BeforeClass;
import ru.pgups.ivs.rglv.labs.db.dao.DAO;
import ru.pgups.ivs.rglv.labs.db.dao.FilmCategoriesDAO;
import ru.pgups.ivs.rglv.labs.db.dao.simple.LanguagesDAO;
import ru.pgups.ivs.rglv.labs.db.model.FilmCategory;
import ru.pgups.ivs.rglv.labs.db.model.Language;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DataSourceAndDaoAppTest {
    private static DataSource dataSource;

    @BeforeClass
    public static void init() {
        dataSource = DataSourceAndDaoApp.createDataSource();
    }

    @org.junit.Test
    public void categoriesDaoScenario() throws Exception {
        FilmCategoriesDAO categoriesDAO = new FilmCategoriesDAO(dataSource);

        FilmCategory a = new FilmCategory("Some films category");

        long aId = categoriesDAO.save(a);

        assertTrue(aId != 0);

        FilmCategory aLoaded = categoriesDAO.get(aId);

        assertNotNull(aLoaded);
        assertEquals(aLoaded.getName(), a.getName());

        aLoaded.setName("Changed category name");
        long aId2 = categoriesDAO.save(aLoaded);

        assertEquals("Ids should be same", aId, aId2);

        FilmCategory aReloaded = categoriesDAO.get(aId2);

        assertNotNull(aReloaded);
        assertEquals("Names should be the same", aReloaded.getName(), aLoaded.getName());

        assertTrue("Categories list should contain saved category", categoriesDAO.list().contains(aReloaded));

        categoriesDAO.delete(aReloaded.getId());

        assertFalse("Category must not be found in list", categoriesDAO.list().contains(aReloaded));
    }

    @org.junit.Test
    public void testSimpleLanguageDAO() throws Exception {
        languagesDaoScenario(new LanguagesDAO(dataSource));
    }

    @org.junit.Test
    public void testCachingAbstractLanguageDAO() throws Exception {
        languagesDaoScenario(new ru.pgups.ivs.rglv.labs.db.dao.LanguagesDAO(dataSource));
    }

    private void languagesDaoScenario(DAO<Language> dao) throws Exception {
        Language l = new Language("some fancy language");

        long id1 = dao.save(l);

        assertTrue("Object must get an ID", id1 != 0);

        Language lLoaded = dao.get(id1);

        assertNotNull(lLoaded);
        // use trim as column is character(20), not varchar - so it is filled with spaces to get full length
        assertEquals(lLoaded.getLanguage().trim(), l.getLanguage().trim());

        lLoaded.setLanguage("fancy language 2");

        long id2 = dao.save(lLoaded);

        assertEquals(id1, id2);

        Language lLoaded2 = dao.get(id2);
        assertNotNull(lLoaded2);
        assertEquals(lLoaded2.getLanguage().trim(), lLoaded.getLanguage().trim());

        dao.delete(id1);

        assertNull(dao.get(id1));

    }
}