package com.booleanuk.api.db;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static Connection connection = null;

    private DatabaseConnection() {

    }

    public static Connection getConnection() throws SQLException {
        if (connection == null) setUpConnection();
        return connection;
    }

    private static void setUpConnection() throws SQLException {
        String dbUser = null;
        String dbURL = null;
        String dbPassword = null;
        String dbDatabase = null;

        InputStream input;
        try {
            input = new FileInputStream("src/main/resources/config.properties");

            Properties prop = new Properties();
            prop.load(input);
            dbUser = prop.getProperty("db.user");
            dbURL = prop.getProperty("db.url");
            dbPassword = prop.getProperty("db.password");
            dbDatabase = prop.getProperty("db.database");
        } catch (Exception e) {
            System.out.println("Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error when connecting to database.");
        }

        final String url = "jdbc:postgresql://" + dbURL + ":5432/" + dbDatabase + "?user=" + dbUser + "&password=" + dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        connection = dataSource.getConnection();
    }

}
