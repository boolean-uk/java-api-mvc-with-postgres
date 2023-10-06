package com.booleanuk.api.Employee;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeRepository {
    private DataSource dataSource;

    public EmployeeRepository() {
        this.dataSource = createDataSource();
    }

    private DataSource createDataSource() {
        Properties properties = loadProperties();
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(properties.getProperty("db.url"));
        dataSource.setUser(properties.getProperty("db.user"));
        dataSource.setPassword(properties.getProperty("db.password"));
        return dataSource;
    }

    private Properties loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return properties;
            }
            properties.load(input);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties", e);
        }
    }

    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery("SELECT * FROM employee")) {

            while (results.next()) {
                Employee employee = mapResultSetToEmployee(results);
                employees.add(employee);  // Add each retrieved employee to the list
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve employees", e);
        }
        return employees;  
    }

    public Employee get(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee WHERE id = ?")) {

            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return mapResultSetToEmployee(result);
                }
            }
            // Return null if no employee found
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve employee with ID " + id, e);
        }
    }

    public Employee add(Employee employee) {
        String SQL = "INSERT INTO employee(name, jobName, salaryGrade, department) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, employee.getName());
            statement.setString(2, employee.getJobName());
            statement.setString(3, employee.getSalaryGrade());
            statement.setString(4, employee.getDepartment());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        employee.setId(generatedId);
                        return employee;
                    } else {
                        throw new SQLException("Failed to retrieve generated ID.");
                    }
                }
            } else {
                throw new SQLException("Creating employee failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add employee", e);
        }
    }

    public Employee update(int id, Employee employee) {
        String SQL = "UPDATE employee SET name = ?, jobName = ?, salaryGrade = ?, department = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {

            statement.setString(1, employee.getName());
            statement.setString(2, employee.getJobName());
            statement.setString(3, employee.getSalaryGrade());
            statement.setString(4, employee.getDepartment());
            statement.setInt(5, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return get(id);
            } else {
                throw new SQLException("Update failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update employee with ID " + id, e);
        }
    }

    public Employee delete(int id) {
        Employee employee = get(id);
        if (employee == null) {
            return null;  // Employee not found
        }

        String SQL = "DELETE FROM employee WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return employee;
            } else {
                throw new SQLException("Delete failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete employee with ID " + id, e);
        }
    }

    private Employee mapResultSetToEmployee(ResultSet resultSet) throws SQLException {
        return new Employee(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("jobName"),
                resultSet.getString("salaryGrade"),
                resultSet.getString("department")
        );
    }
}
