package com.booleanuk.api.employee;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Date;

public class EmployeeRepository {
    DataSource dataSource;
    String dbURL;
    String dbUser;
    String dbPassword;
    String dbDatabase;
    Connection connection;

    public EmployeeRepository(){

    }
    private void getDatabaseCredentials(){

    }

}
