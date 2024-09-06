package com.booleanuk.api.config;

import javax.sql.DataSource;
import java.sql.Connection;

public class DBConfig {
    private DataSource dataSource;
    private String dbUser;
    private String dbURL;
    private String dbPassword;
    private String dbDatabase;

    public DBConfig() {}

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbURL() {
        return dbURL;
    }

    public void setDbURL(String dbURL) {
        this.dbURL = dbURL;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbDatabase() {
        return dbDatabase;
    }

    public void setDbDatabase(String dbDatabase) {
        this.dbDatabase = dbDatabase;
    }
}
