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
                + this.dbUser + "&password=" + this.dbPassword;
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

    public Employee get(int id) throws SQLException {
       PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employee WHERE id=?");
       statement.setInt(1, id);
       ResultSet results = statement.executeQuery();
       Employee theEmployee = null;
       if (results.next()) {
            theEmployee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department"));
       }
       return theEmployee;
    }

    public Employee update(int id, Employee employee) throws SQLException {
        String SQL = "UPDATE employee " +
                "SET name = ?, " +
                "jobName = ?, " +
                "salaryGrade = ?, " +
                "department = ? " +
                "WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
        statement.setInt(5, id);
        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if (rowsAffected > 0) {
            updatedEmployee = this.get(id);
        }
        return updatedEmployee;
    }

    public Employee delete(int id) throws SQLException {
        String SQL = "DELETE FROM employee WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Employee deletedEmployee = null;
        deletedEmployee = this.get(id);
        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if(rowsAffected == 0) {
            deletedEmployee = null;
        }
        return deletedEmployee;
    }







}
