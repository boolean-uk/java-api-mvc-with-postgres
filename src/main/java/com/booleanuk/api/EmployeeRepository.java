package com.booleanuk.api;


import org.postgresql.ds.PGSimpleDataSource;

import javax.print.DocFlavor;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeRepository {
    private DataSource dataSource;
    private String dbUser;
    private String dbUrl;
    private String dbPassword;
    private String dbDatabase;
    private Connection connection;


    public EmployeeRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();

    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees");
        ResultSet results = statement.executeQuery();
        System.out.println(results);

        while (results.next()){
            Employee theEmployee = new Employee(results.getInt("id"),
                    results.getString("name"),results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department"));
            everyone.add(theEmployee);
        }
        return everyone;
    }


    public Employee get(int id) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees WHERE id= ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Employee employee = null;
        if (results.next()){
            employee = new Employee(results.getInt("id"),
                    results.getString("name"),results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department"));
        }
        return employee;
    }


    private void getDatabaseCredentials(){
        try(InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            this.dbUser = properties.getProperty("db.user");
            this.dbUrl = properties.getProperty("db.url");
            this.dbPassword = properties.getProperty("db.password");
            this.dbDatabase = properties.getProperty("db.database");

        } catch (Exception e) {
            System.out.println("Oops: " + e);
        }
    }

    private DataSource createDataSource(){
        String url = "jdbc:postgresql://" +this.dbUrl+ ":5432/" +this.dbDatabase+
                "?user=" +this.dbUser+ "&password" +this.dbPassword;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        return dataSource;
    }
}
