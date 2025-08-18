package com.booleanuk.employee;

import javax.sql.DataSource;
import java.sql.Connection;

public class EmployeeRepository {
    private DataSource dataSource;
    private String dbUser;
    private String dbURL;
    private String dbPassword;
    private String dbDatabase;
    private Connection connection;


}
