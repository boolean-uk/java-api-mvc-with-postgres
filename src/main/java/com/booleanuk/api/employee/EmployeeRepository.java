package com.booleanuk.api.employee;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
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
    private String dbURL;
    private String dbPassword;
    private String dbDataBase;
    private Connection connection;

    public EmployeeRepository() throws SQLException{
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();
        this.getDatabaseCredentials();

    }

    private void getDatabaseCredentials() {
        try(InputStream inputStream = new FileInputStream("src/main/resources/config.properties")){
            Properties properties = new Properties();
            properties.load(inputStream);
            this.dbUser = properties.getProperty(dbUser);
            this.dbURL = properties.getProperty(dbURL);
            this.dbPassword = properties.getProperty(dbPassword);
            this.dbDataBase = properties.getProperty(dbDataBase);
        } catch (Exception e){
            System.out.println("Oh no: " + e);
        }
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgressql://" + this.dbURL + ":5432/" + this.dbDataBase + "?user=" + this.dbUser + "?password=" + this.dbPassword;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        return dataSource;
    }

    public List<Employee> getAll() throws SQLException{
        List<Employee> employeesList = new ArrayList<>();
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM employee");

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Employee employee = new Employee(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("jobName"),
                    resultSet.getString("salaryGrade"),
                    resultSet.getString("department"));
            employeesList.add(employee);
        }
        return employeesList;
    }
}
