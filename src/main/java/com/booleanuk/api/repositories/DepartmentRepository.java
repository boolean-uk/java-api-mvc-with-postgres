package com.booleanuk.api.repositories;

import com.booleanuk.api.config.DatabaseConfig;
import com.booleanuk.api.models.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository {
    private final Connection connection;

    public DepartmentRepository() throws SQLException {
        DatabaseConfig dbConfig = new DatabaseConfig();
        this.connection = dbConfig.getConnection();
    }

    public List<Department> getAll() throws SQLException {
        List<Department> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Departments");
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            Department theDepartment = new Department(results.getLong("id"), results.getString("name"), results.getString("location"));
            everyone.add(theDepartment);
        }
        return everyone;
    }

    public Department getOne(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Departments WHERE id = ?");
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        if (results.next()) {
            return new Department(results.getLong("id"), results.getString("name"), results.getString("location"));
        }
        return null;
    }

    public Department add(Department department) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("INSERT INTO Departments (name, location) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        int rowsAffected = statement.executeUpdate();
        System.out.println(rowsAffected);
        if (rowsAffected > 0) {
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    System.out.println(generatedKeys.getLong(1));
                    return getOne(generatedKeys.getLong(1));
                }
            } catch (SQLException e) {
                System.out.println("Oops: " + e);
            }
        }
        return null;
    }

    public Department update(long id, Department department) throws SQLException {
        if (department.getName() == null || department.getName().trim().isEmpty() ||
                department.getLocation() == null || department.getLocation().trim().isEmpty()) {
            throw new SQLException("Could not update the department, please check all required fields are correct.");
        }

        PreparedStatement statement = this.connection.prepareStatement("UPDATE Departments SET name = ?, location = ? WHERE id = ?");
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        statement.setLong(3, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return getOne(id);
        }
        return null;
    }

    public Department delete(long id) throws SQLException {
        Department toBeDeleted = getOne(id);
        PreparedStatement statement = this.connection.prepareStatement("DELETE FROM Departments WHERE id = ?");
        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return toBeDeleted;
        }
        return null;
    }
}
