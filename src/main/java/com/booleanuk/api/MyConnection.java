package com.booleanuk.api;

import org.postgresql.ds.PGSimpleDataSource;
import javax.sql.DataSource;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class MyConnection{
    private Connection connection;
    private DataSource datasource;
    private String dbUser,dbURL,dbPassword,dbDatabase;
    public MyConnection() throws SQLException {
        this.getDatabaseCredentials();
        this.datasource = this.createDataSource();
        this.connection = this.datasource.getConnection();
    }
    private DataSource createDataSource(){
        final String url = "jdbc:postgresql://"+this.dbURL+":5432/"+this.dbDatabase+"?user="+this.dbUser+"&password="+this.dbPassword;
        final PGSimpleDataSource datasource = new PGSimpleDataSource();
        datasource.setURL(url);
        return datasource;
    }
    private void getDatabaseCredentials(){
        try(InputStream input = new FileInputStream("src/main/resources/config.properties")){
            Properties prop = new Properties();
            prop.load(input);
            this.dbURL = prop.getProperty("db.url");
            this.dbUser = prop.getProperty("db.user");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("File not found");
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}
