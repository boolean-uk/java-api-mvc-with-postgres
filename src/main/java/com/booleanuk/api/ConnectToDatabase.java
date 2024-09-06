package com.booleanuk.api;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectToDatabase {
    private Connection connection;
    private DataSource datasource;
    private String dbUser;
    private String dbURL;
    private String dbPassword;
    private String dbDatabase;

    public ConnectToDatabase() throws SQLException {
        //get credentials
        this.getDatabaseCredentials();
        // set up the datasource
        this.datasource = this.createDataSource();
        // Set up connection
        this.connection = this.datasource.getConnection();

    }

    private void getDatabaseCredentials() {
        try(InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://"+ this.dbURL
                + ":5432/" + this.dbDatabase
                + "?user=" + this.dbUser
                + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        return dataSource;
    }

    public Connection getConnection() {
        return this.connection;
    }
}
