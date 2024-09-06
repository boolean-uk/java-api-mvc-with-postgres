package com.booleanuk.api.database;

import lombok.Getter;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DatabaseConnection {
    private String dbUser;
    private String dbURL;
    private String dbPassword;
    private String dbDatabase;
    @Getter
    private final Connection connection;
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("src/main/java/com/booleanuk/api/database/database.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger handler", e);
        }
    }

    public DatabaseConnection() throws SQLException {
        this.getDatabaseCredentials();
        DataSource dataSource = this.createDataSource();
        this.connection = dataSource.getConnection();
    }

    private void getDatabaseCredentials() {
        logger.info("Getting database credentials");

        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");
            logger.info("Database credentials successfully fetched.");
        }  catch (Exception e) {
                logger.severe("An error occured: " + e);
        }
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }
}
