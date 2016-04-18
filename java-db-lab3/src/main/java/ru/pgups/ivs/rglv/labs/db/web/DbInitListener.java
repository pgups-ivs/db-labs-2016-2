package ru.pgups.ivs.rglv.labs.db.web;

import org.postgresql.ds.PGPoolingDataSource;
import ru.pgups.ivs.rglv.labs.db.dao.ActorsDAO;
import ru.pgups.ivs.rglv.labs.db.dao.FilmCategoriesDAO;
import ru.pgups.ivs.rglv.labs.db.dao.FilmsDAO;
import ru.pgups.ivs.rglv.labs.db.dao.LanguagesDAO;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener()
public class DbInitListener implements ServletContextListener {

    // Public constructor is required by servlet spec
    public DbInitListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().log("Initializing data source connection");

        PGPoolingDataSource poolingDataSource = new PGPoolingDataSource();
        poolingDataSource.setDataSourceName("Lab 2 data source");

        poolingDataSource.setServerName("localhost");
        poolingDataSource.setDatabaseName("lab-dvd");
        poolingDataSource.setUser("postgres");
        poolingDataSource.setPassword("postgres");
        poolingDataSource.setMaxConnections(8);
        poolingDataSource.setInitialConnections(1);

        LanguagesDAO languagesDAO = new LanguagesDAO(poolingDataSource);
        ActorsDAO actorsDAO = new ActorsDAO(poolingDataSource);
        FilmCategoriesDAO categoriesDAO = new FilmCategoriesDAO(poolingDataSource);

        FilmsDAO filmsDAO = new FilmsDAO(poolingDataSource);

        filmsDAO.setActorsDAO(actorsDAO);
        filmsDAO.setCategoriesDAO(categoriesDAO);
        filmsDAO.setLanguageDAO(languagesDAO);

        sce.getServletContext().setAttribute("actorsDAO", actorsDAO);
        sce.getServletContext().setAttribute("categoriesDAO", categoriesDAO);
        sce.getServletContext().setAttribute("filmsDAO", filmsDAO);
        sce.getServletContext().setAttribute("languagesDAO", languagesDAO);

        sce.getServletContext().setAttribute("datasource", poolingDataSource);

        sce.getServletContext().log("Initialized all DAOs");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().log("Closing connections pool");

        PGPoolingDataSource poolingDataSource = (PGPoolingDataSource) sce.getServletContext().getAttribute("datasource");
        poolingDataSource.close();

    }
}
