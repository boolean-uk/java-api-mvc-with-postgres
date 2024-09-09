package com.booleanuk.api.server;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ServerConnection {

    private static Connection myConnection = null;

    private DataSource dataSource;


    private ServerConnection() throws SQLException{
        // GET CREDENTIALS
        // SET UP DATASOURCE
        this.dataSource = this.createDataSource();

        // SET UP CONNECTION
        myConnection  = this.dataSource.getConnection();
    }

    public static synchronized Connection getInstance() throws SQLException {
        if(myConnection == null) {
            myConnection = createDataSource().getConnection();
        }

        return myConnection;
    }

    private static DataSource createDataSource() {
        String dbUser = null;
        String dbURL = null;
        String dbPassword = null;
        String dbDatabase = null;

        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            dbUser = prop.getProperty("db.user");
            dbURL = prop.getProperty("db.url");
            dbPassword = prop.getProperty("db.password");
            dbDatabase = prop.getProperty("db.database");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }


        // The url specifies the address of our database along with username and password credentials
        // you should replace these with your own username and password
        final String url = "jdbc:postgresql://" + dbURL + ":5432/" + dbDatabase + "?user=" + dbUser +"&password=" + dbPassword;
        final PGSimpleDataSource dataSource1 = new PGSimpleDataSource();
        dataSource1.setUrl(url);
        return dataSource1;
    }
}
