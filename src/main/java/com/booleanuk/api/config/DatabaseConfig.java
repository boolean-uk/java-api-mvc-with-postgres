package com.booleanuk.api.config;

import lombok.Getter;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private DataSource datasource;
    private String dbUser;
    private String dbURL;
    private String dbPassword;
    private String dbDatabase;
    @Getter
    private final Connection connection;

    public DatabaseConfig() throws SQLException {
        setDatabaseCredentials();
        createAndSetDataSource();
        this.connection = this.datasource.getConnection();
    }

    private void setDatabaseCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");
        } catch(Exception e) {
            System.out.println("Whats wrong?: " + e);
        }
    }

    private void createAndSetDataSource() {
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser +"&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        this.datasource = dataSource;
    }

}