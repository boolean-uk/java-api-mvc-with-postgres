package com.booleanuk.api.employee;

import javax.sql.DataSource;
import java.sql.Connection;

public class EmployeeController {
    private DataSource dataSource;
    private String dbUser;
    private String dbURL;
    private String dbPassword;
    private Connection connection;
}
