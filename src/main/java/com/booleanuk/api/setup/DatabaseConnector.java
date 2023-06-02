package com.booleanuk.api.setup;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class DatabaseConnector {

    private final DataSource dataSource;
    private String dbURL;
    private String dbUser;
    private String dbPassword;
    private String dbDatabase;
    private final Connection connection;

    public DatabaseConnector() throws SQLException {
        System.out.println("Setting up database connection...");
        this.getDatabaseCredentials();
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();
        System.out.println("Established database connection");
    }

    private void getDatabaseCredentials(){
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");
        } catch(Exception e) {
            System.out.println("Something went wrong!\n" + e);
        }
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser +"&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    public Connection getConnection(){return this.connection;}

}
