package ru.pgups.ivs.rglv.labs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcWithoutAutoclosable {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        Class.forName("org.postgresql.Driver");

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/lab-dvd", "postgres", "postgres");

            statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM actor ORDER BY last_name, first_name");

            while (resultSet.next()) {
                System.out.println(
                        String.format("%s %s (id: %03d)",
                                resultSet.getString("last_name"), resultSet.getString("first_name"), resultSet.getInt("actor_id")
                        )
                );
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
