package ru.pgups.ivs.rglv.labs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlainJdbcConnectionApp {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/dvd", "postgres", "postgres");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM actor ORDER BY last_name, first_name")
        ) {
            while (resultSet.next()) {
                System.out.println(
                        String.format("%s %s (id: %03d)",
                                resultSet.getString("last_name"), resultSet.getString("first_name"), resultSet.getInt("actor_id")
                        )
                );
            }
        }
    }
}
