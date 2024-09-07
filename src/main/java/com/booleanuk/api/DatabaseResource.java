package com.booleanuk.api;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

// Resource: https://refactoring.guru/design-patterns/singleton/java/example
public class DatabaseResource {
    private static DatabaseResource instance;

    public DataSource datasource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;

    private DatabaseResource() {
        this.getDatabaseCredentials();
        this.datasource = this.createDataSource();
    }

    public static DatabaseResource getInstance() {
        if (instance == null) {
            instance = new DatabaseResource();
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
            System.out.println(e.getMessage());
        }
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser +"&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }
}
