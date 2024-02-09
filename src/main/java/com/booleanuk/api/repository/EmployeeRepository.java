package com.booleanuk.api.repository;

import com.booleanuk.api.model.Employee;
import org.postgresql.ds.PGSimpleDataSource;
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
    private String dbURL;
    private String dbPassword;
    private String dbDatabase;
    private Connection connection;


    public EmployeeRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();
    }

    public List<Employee> getAll()throws SQLException{
        List<Employee> allEmployees = new ArrayList<>();
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM employees");
        ResultSet results = preparedStatement.executeQuery();
        while (results.next()) {
            Employee employee = new Employee(
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department")
            );
            allEmployees.add(employee);
        }
        return allEmployees;
    }

    public Employee get(int id) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
        preparedStatement.setInt(1, id);
        ResultSet results = preparedStatement.executeQuery();
        results.next();
        return new Employee(
                results.getString("name"),
                results.getString("jobName"),
                results.getString("salaryGrade"),
                results.getString("department")
        );
    }

    public Employee add(Employee employee) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO employees (name, jobName, salaryGrade, department) VALUES (?, ?, ?, ?)");
        preparedStatement.setString(1, employee.getName());
        preparedStatement.setString(2, employee.getJobName());
        preparedStatement.setString(3, employee.getSalaryGrade());
        preparedStatement.setString(4, employee.getDepartment());
        preparedStatement.executeUpdate();
        return employee;
    }

    public Employee update(int id, Employee employee) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE employees SET name = ?, jobName = ?, salaryGrade = ?, department = ? WHERE id = ?");
        preparedStatement.setString(1, employee.getName());
        preparedStatement.setString(2, employee.getJobName());
        preparedStatement.setString(3, employee.getSalaryGrade());
        preparedStatement.setString(4, employee.getDepartment());
        preparedStatement.setInt(5, id);
        preparedStatement.executeUpdate();
        return employee;
    }

    public Employee delete(int id) throws SQLException {
        Employee employee = this.get(id);
        PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE FROM employees WHERE id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        return employee;
    }

    private DataSource createDataSource(){
        String url = "jdbc:postgresql://"
                + this.dbURL + ":5432/"
                + this.dbDatabase
                + "?user=" + this.dbUser
                + "&password=" + this.dbPassword;
        System.out.println(url);
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        return dataSource;
    }

    private void getDatabaseCredentials(){
        try(InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }
}
