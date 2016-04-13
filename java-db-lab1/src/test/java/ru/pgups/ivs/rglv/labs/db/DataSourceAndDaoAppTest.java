package ru.pgups.ivs.rglv.labs.db;

import org.junit.Before;
import ru.pgups.ivs.rglv.labs.db.dao.FilmCategoriesDAO;
import ru.pgups.ivs.rglv.labs.db.model.FilmCategory;

import javax.sql.DataSource;

import static org.junit.Assert.*;

public class DataSourceAndDaoAppTest {
    private DataSource dataSource;

    @Before
    public void init() {
        this.dataSource = DataSourceAndDaoApp.createDataSource();
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
}