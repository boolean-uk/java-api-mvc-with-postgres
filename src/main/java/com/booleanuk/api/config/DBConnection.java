
package com.booleanuk.api.config;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private final DBConfig dbConfiguration;
    private Connection dbConnection;

    public DBConnection() throws SQLException {
        this.dbConfiguration = new DBConfig();
        this.insertDBCredentials();
        this.dbConnection = this.dbConfiguration.getDataSource().getConnection();
    }

    private void insertDBCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            addProperties(input);
        }
        catch (Exception e) {
            System.out.println("Failed to set db properties: " + e.getCause());
        }
    }

    private void addProperties(InputStream input) throws IOException {
        Properties properties = new Properties();
        properties.load(input);
        dbConfiguration.setDbUser(properties.getProperty("db.user"));
        dbConfiguration.setDbURL(properties.getProperty("db.url"));
        dbConfiguration.setDbPassword(properties.getProperty("db.password"));
        dbConfiguration.setDbDatabase(properties.getProperty("db.database"));
    }

    private DataSource createDataSource() {
        String url = "jdbc:postgresql://" +
                dbConfiguration.getDbURL() +
                ":5432/" +
                dbConfiguration.getDbDatabase() +
                "?user=" +
                dbConfiguration.getDbUser() +
                "&password=" +
                dbConfiguration.getDbPassword();

        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        return dataSource;
    }
}
