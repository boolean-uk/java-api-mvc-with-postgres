package com.booleanuk.api.repository;

import com.booleanuk.api.model.Employee;
import org.postgresql.ds.PGSimpleDataSource;


import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static javax.swing.text.html.HTML.Tag.P;

public class EmployeeRepository {
    DataSource dataSource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDataBase;
    Connection connection;

    public EmployeeRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();
    }

    private void getDatabaseCredentials(){
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDataBase = prop.getProperty("db.database");
        } catch (Exception e){
            System.out.println("Oopsi :" + e);
        }
    }

    private DataSource createDataSource(){
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDataBase + "?user="
                + this.dbUser + "?password=" + this.dbPassword;
        PGSimpleDataSource datasource = new PGSimpleDataSource();
        datasource.setUrl(url);
        return datasource;
    }

    public List<Employee> getAll() throws SQLException{
        List<Employee> employees = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employee");

        ResultSet result = statement.executeQuery();

        while(result.next()){
            Employee theEmployee = new Employee( result.getInt("id"),
                    result.getString("name"),
            result.getString("jobName"),
            result.getString("salaryGrade"),
            result.getString("department"));
            employees.add(theEmployee);
        }

        return employees;

    }


}
