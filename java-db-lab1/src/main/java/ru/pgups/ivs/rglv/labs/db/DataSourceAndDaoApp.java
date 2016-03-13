package ru.pgups.ivs.rglv.labs.db;

import org.postgresql.ds.PGPoolingDataSource;
import ru.pgups.ivs.rglv.labs.db.dao.ActorsDAO;
import ru.pgups.ivs.rglv.labs.db.model.Actor;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;


public class DataSourceAndDaoApp
{
    public static DataSource createDataSource() {
        PGPoolingDataSource poolingDataSource = new PGPoolingDataSource();
        poolingDataSource.setDataSourceName("Lab 1 data source");

        poolingDataSource.setServerName("localhost");
        poolingDataSource.setDatabaseName("dvd");
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

        System.out.println("= = = = = Actors list = = = = = =");
        List<Actor> actors = actorsDAO.getAllActors();
        for (Actor actor : actors) {
            System.out.println(String.format("%s %s (id: %03d)",
                    actor.getLastName(), actor.getFirstName(), actor.getId()));
        }
    }
}
