package com.booleanuk.api.Employees;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class EmployeeRepository {
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String dbDatabase;
    private Connection connection;

    public EmployeeRepository() {
        this.getDatabaseCredentials();
        DataSource dataSource = createDataSource();
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("Repository Creation: " + e);
        }
    }

    private void getDatabaseCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")){
            Properties properties = new Properties();
            properties.load(input);
            this.dbUrl = properties.getProperty("db.url");
            this.dbUser = properties.getProperty("db.user");
            this.dbPassword = properties.getProperty("db.password");
            this.dbDatabase = properties.getProperty("db.database");
        } catch (Exception e) {
            System.out.println("Database Credentials: " + e);
        }
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbUrl + ":5432/" + this.dbDatabase + "?user=" + this.dbUser +"&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }
}
