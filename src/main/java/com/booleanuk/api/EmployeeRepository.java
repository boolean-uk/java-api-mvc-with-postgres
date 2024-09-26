package com.booleanuk.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

    private final Connection connection;

    @Autowired
    public EmployeeRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Employees");
             ResultSet results = statement.executeQuery()) {
            while (results.next()) {
                Employee employee = new Employee(
                        (long) results.getInt("id"),
                        results.getString("name"),
                        results.getString("jobName"),
                        results.getString("salaryGrade"),
                        results.getString("department"));
                employees.add(employee);
            }
        }
        return employees;
    }

    public Employee get(int id) throws SQLException {
        Employee employee = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Employees WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    employee = new Employee(
                            (long) results.getInt("id"),
                            results.getString("name"),
                            results.getString("jobName"),
                            results.getString("salaryGrade"),
                            results.getString("department"));
                }
            }
        }
        return employee;
    }

    public Employee update(int id, Employee employee) throws SQLException {
        String SQL = "UPDATE Employees " +
                "SET name = ?, " +
                "jobName = ?, " +
                "salaryGrade = ?, " +
                "department = ? " +
                "WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getJobName());
            statement.setString(3, employee.getSalaryGrade());
            statement.setString(4, employee.getDepartment());
            statement.setInt(5, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return get(id);
            }
        }
        return null;
    }

    public Employee delete(int id) throws SQLException {
        Employee employee = get(id);
        if (employee != null) {
            String SQL = "DELETE FROM Employees WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setInt(1, id);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    return employee;
                }
            }
        }
        return null;
    }

    public Employee add(Employee employee) throws SQLException {
        String SQL = "INSERT INTO Employees (name, jobName, salaryGrade, department) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getJobName());
            statement.setString(3, employee.getSalaryGrade());
            statement.setString(4, employee.getDepartment());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        employee.setId((long) rs.getInt(1));
                    }
                }
                return employee;
            }
        }
        return null;
    }
}
