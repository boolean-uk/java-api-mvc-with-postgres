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
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class EmployeeRepository {
    DataSource dataSource;
    String dbURL;
    String dbUser;
    String dbPassword;
    String dbDatabase;
    Connection connection;

    public EmployeeRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.dataSource = this.getDataSource();
        this.connection = this.dataSource.getConnection();

    }
    private void getDatabaseCredentials(){
        try (InputStream inputStream = new
                FileInputStream("src/main/resources/config.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            this.dbURL = properties.getProperty("db.url");
            this.dbUser = properties.getProperty("db.user");
            this.dbPassword = properties.getProperty("db.password");
            this.dbDatabase = properties.getProperty("db.database");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    private DataSource getDataSource(){
        String URL = this.dbURL + this.dbDatabase + "?user=" + this.dbUser + "&password=" +this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(URL);
        return dataSource;
    }
    public List<Employee> getAll() throws SQLException{
        List<Employee> allEmployees = new ArrayList<>();
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM employees");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Employee employee = new Employee(resultSet.getInt("id"), resultSet.getString("name"),
                    resultSet.getString("jobName"), resultSet.getString("salaryGrade"),
                    resultSet.getString("department"));
            allEmployees.add(employee);
        }
        return allEmployees;
    }
    public Employee getOne(int id) throws SQLException{
        PreparedStatement preparedStatement = this.connection.prepareStatement(
                "SELECT * FROM Employees WHERE id = ?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Employee employee = null;
        if (resultSet.next()){
            employee = new Employee(resultSet.getInt("id"), resultSet.getString("name"),
                    resultSet.getString("jobName"), resultSet.getString("salaryGrade"),
                    resultSet.getString("department"));
        }
        return employee;
    }
}
