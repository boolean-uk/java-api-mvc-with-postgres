package com.booleanuk.api;


import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.http.ResponseEntity;

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
    private static String url;
    private static String user;
    private static String pass;

    private final Connection connection;

    static {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            url = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            pass = prop.getProperty("db.password");
        } catch (Exception e) {
            System.out.println("Oops: " + e);
        }
    }

    public EmployeeRepository() throws SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl(url + "?user=" + user + "&password=" + pass);

        connection = ds.getConnection();
    }

    public void testConnectionToDB() throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * from employees");
        System.out.println(statement);
        ResultSet results = statement.executeQuery();
        System.out.println(statement);

        while (results.next()) {
            String id = "" + results.getLong("id");
            String name = results.getString("name");
            String jobName = results.getString("jobname");
            System.out.println(id + " - " + name + " - " + jobName);
        }
    }


    public Employee addEmployee(Employee employee) throws SQLException {
        String SQL = "INSERT INTO employees (name, jobName, salaryGrade, department) VALUES (?, ?, ?, ?)";

        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());

        int rowsAffected = statement.executeUpdate();
        long newid = 0;

        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()){
                if (rs.next()) {
                    newid = rs.getLong(1);
                }
            } catch (Exception e) {
                System.out.println("Oops: " + e);
            }
            employee.setId(newid);
        } else {
            employee = null;
        }
        return employee;
    }

    public List<Employee> getAllEmployees() throws SQLException {
        String SQL = "SELECT * FROM employees";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        ResultSet results = statement.executeQuery();

        List<Employee> list = new ArrayList<>();

        while (results.next()) {
            Employee employee = new Employee(
                    results.getLong("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department")
            );
            list.add(employee);
        }
        return list;
    }

    public Employee getEmployeeByID(long id) throws SQLException {
        String SQL = "SELECT * FROM employees e WHERE e.id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setLong(1, id);

        ResultSet results = statement.executeQuery();
        if (results.next()) {
            return new Employee(
                    results.getLong("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department")
            );
        } else {
            return null;
        }
    }

    public Employee updateEmployeeByID(long id, Employee employee) throws SQLException {
        String SQL = "UPDATE employees as e" +
                    " SET name = ?, jobName = ?, salaryGrade = ?, department = ?" +
                    " WHERE e.id = ?";

        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
        statement.setLong(5, id);

        int rowsAffected = statement.executeUpdate();
        Employee updatedCustomer = null;
        if (rowsAffected > 0) {
            updatedCustomer = this.getEmployeeByID(id);
        }
        return updatedCustomer;
    }

    public Employee deleteEmployeeByID(long id) throws SQLException {
        String SQL = "DELETE FROM employees e WHERE e.id = ?";

        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setLong(1, id);

        // Get the customer we're deleting before we delete them
        Employee deletedEmployee = this.getEmployeeByID(id);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the customer we're deleting if we didn't delete them
            deletedEmployee = null;
        }
        return deletedEmployee;
    }

}
