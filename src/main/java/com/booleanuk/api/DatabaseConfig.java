package com.booleanuk.api;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource createDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            String dbUser = prop.getProperty("db.user");
            String dbURL = prop.getProperty("db.url");
            String dbPassword = prop.getProperty("db.password");
            String dbDatabase = prop.getProperty("db.database");
            String url = "jdbc:postgresql://" + dbURL + ":5432/" + dbDatabase + "?user=" + dbUser + "&password=" + dbPassword;

            dataSource.setURL(url);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load database credentials.", e);
        }

        return dataSource;
    }

}
