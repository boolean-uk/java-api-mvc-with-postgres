package com.booleanuk.api.repositories;

import com.booleanuk.api.config.DatabaseConfig;
import com.booleanuk.api.models.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    private final Connection connection;

    public EmployeeRepository() throws SQLException {
        DatabaseConfig dbConfig = new DatabaseConfig();
        this.connection = dbConfig.getConnection();
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees");
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            Employee theEmployee = new Employee(results.getLong("id"), results.getString("name"), results.getString("jobName"), results.getInt("salary_id"), results.getInt("department_id"));
            everyone.add(theEmployee);
        }
        return everyone;
    }

    public Employee getOne(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees WHERE id = ?");
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        if (results.next()) {
            return new Employee(results.getLong("id"), results.getString("name"), results.getString("jobName"), results.getInt("salary_id"), results.getInt("department_id"));
        }
        return null;
    }

    public Employee add(Employee employee) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("INSERT INTO Employees (name, jobName, salary_id, department_id) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setInt(3, employee.getSalary_id());
        statement.setInt(4, employee.getDepartment_id());
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return getOne(generatedKeys.getLong(1));
                }
            } catch (SQLException e) {
                System.out.println("Oops: " + e);
            }
        }
        return null;
    }

    public Employee update(long id, Employee employee) throws SQLException {
        if (employee.getName() == null || employee.getName().trim().isEmpty() ||
                employee.getJobName() == null || employee.getJobName().trim().isEmpty() ||
                employee.getSalary_id() < 0 || employee.getDepartment_id() < 0) {
            throw new SQLException("Could not update the employee, please check all required fields are correct.");
        }

        PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE Employees " +
                        "SET name = ? ," +
                        "jobName = ? ," +
                        "salary_id = ? ," +
                        "department_id = ? " +
                        "WHERE id = ? ");
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setInt(3, employee.getSalary_id());
        statement.setInt(4, employee.getDepartment_id());
        statement.setLong(5, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return getOne(id);
        }
        return null;
    }

    public Employee delete(long id) throws SQLException {
        Employee employeeToDelete = getOne(id);
        PreparedStatement statement = this.connection.prepareStatement("DELETE FROM Employees WHERE id = ?");
        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return employeeToDelete;
        }
        return null;
    }
}