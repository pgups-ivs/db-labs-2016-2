package ru.pgups.ivs.rglv.labs.db;

import org.postgresql.ds.PGPoolingDataSource;
import ru.pgups.ivs.rglv.labs.db.dao.ActorsDAO;
import ru.pgups.ivs.rglv.labs.db.dao.FilmCategoriesDAO;
import ru.pgups.ivs.rglv.labs.db.dao.FilmsDAO;
import ru.pgups.ivs.rglv.labs.db.dao.LanguagesDAO;
import ru.pgups.ivs.rglv.labs.db.model.Actor;
import ru.pgups.ivs.rglv.labs.db.model.Language;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;


public class DataSourceAndDaoApp
{
    public static DataSource createDataSource() {
        PGPoolingDataSource poolingDataSource = new PGPoolingDataSource();
        poolingDataSource.setDataSourceName("Lab 1 data source");

        poolingDataSource.setServerName("localhost");
        poolingDataSource.setDatabaseName("lab-dvd");
        poolingDataSource.setUser("postgres");
        poolingDataSource.setPassword("postgres");
        poolingDataSource.setMaxConnections(8);
        poolingDataSource.setInitialConnections(1);
        return poolingDataSource;
    }

    public static void main( String[] args ) throws SQLException {
        System.out.println("Hello World! This is lab1 example for java-db course.");

        DataSource dataSource = createDataSource();

        ActorsDAO actorsDAO = new ActorsDAO(dataSource);
        FilmCategoriesDAO categoriesDAO = new FilmCategoriesDAO(dataSource);
        LanguagesDAO languagesDAO = new LanguagesDAO(dataSource);

        System.out.println("= = = = = Actors list = = = = = =");
        List<Actor> actors = actorsDAO.list();
        for (Actor actor : actors) {
            System.out.println(String.format("%s %s (id: %03d)",
                    actor.getLastName(), actor.getFirstName(), actor.getId()));
        }

        System.out.println("= = = = = Categories list = = = = = =");
        categoriesDAO
                .list()
                .stream()
                .forEach(filmCategory ->
                        System.out.println(String.format("%s (id: %03d)", filmCategory.getName(), filmCategory.getId()))
                );

        System.out.println("= = = = = Languages list = = = = = =");
        languagesDAO.list().stream().forEach(language -> System.out.println(String.format("%s (id: %03d)", language.getLanguage(), language.getId())));


        FilmsDAO filmDAO = new FilmsDAO(dataSource);

        filmDAO.setActorsDAO(actorsDAO);
        filmDAO.setCategoriesDAO(categoriesDAO);
        filmDAO.setLanguageDAO(languagesDAO);

        System.out.println("= = = = = Films list = = = = = =");
        filmDAO.list().stream().forEach(film -> System.out.println(String.format("%s (id: %03d) in %s", film.getTitle(), film.getId(), film.getLanguage().getLanguage())));
    }
}
