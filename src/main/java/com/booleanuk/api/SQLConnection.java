package com.booleanuk.api;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class SQLConnection {
    private String dbUser;
    private String dbURL;
    private String dbPassword;
    private String dbDatabase;
    private final Connection connection;
    private static SQLConnection instance;

    private SQLConnection() throws SQLException {
        this.getDatabaseCredentials();
        DataSource dataSource = createDataSource();
        this.connection = dataSource.getConnection();
    }

    public static SQLConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new SQLConnection();
        }
        return instance;
    }

    private void getDatabaseCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");
        } catch (Exception e) {
            System.out.println("Oops: " + e);
        }
    }

    private DataSource createDataSource() {
        String url = "jdbc:postgresql://" + this.dbURL + ":5432/"
                + this.dbDatabase + "?user=" + this.dbUser
                + "&password=" + this.dbPassword;
        PGSimpleDataSource theDataSource = new PGSimpleDataSource();
        theDataSource.setURL(url);
        return theDataSource;
    }

    public Connection getConnection() {
        return connection;
    }
}
