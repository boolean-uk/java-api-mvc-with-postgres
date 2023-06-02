package com.booleanuk.api.database;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresDatabase implements Database {
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String dbDatabase;
    private Connection connection;
    private DataSource dataSource;

    public PostgresDatabase(){
        this.getDatabaseCredentials();
        dataSource= this.createDataScource();
        try{
            this.connection = this.dataSource.getConnection();
        } catch (SQLException e){
            System.out.println(e.getMessage() );
        }

    }

    private DataSource createDataScource() {
        String url = "jdbc:postgresql://" + this.dbUrl +
                ":5432/" + this.dbDatabase +
                "?user=" + this.dbUser +
                "&password=" + this.dbPassword;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    private void getDatabaseCredentials() {
        try(InputStream input = new FileInputStream("src/main/resources/config.properties")){
            Properties prop = new Properties();
            prop.load(input);
            dbUrl = prop.getProperty("db.url");
            dbUser = prop.getProperty("db.user");
            dbPassword = prop.getProperty("db.password");
            dbDatabase = prop.getProperty("db.database");
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public PreparedStatement statement(String sqlQuery) {
        PreparedStatement s = null;
        try{
            s = connection.prepareStatement(sqlQuery);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return s;

    }

}
