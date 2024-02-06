package com.booleanuk.api.repositories;

import com.booleanuk.api.DatabaseManager;
import com.booleanuk.api.models.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DepartmentRepository {
    private Connection connection;

    @Autowired
    public DepartmentRepository(DatabaseManager databaseManager) {
        try {
            this.connection = databaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error getting database connection", e);
        }
    }

    public List<Department> getAll() throws SQLException  {
        List<Department> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Departments");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Department theDepartment = new Department(results.getInt("id"), results.getString("name"), results.getString("location"));
            everyone.add(theDepartment);
        }
        return everyone;
    }

    public Department add(Department department) throws SQLException {
        String SQL = "INSERT INTO Departments (name, location) VALUES(?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        int rowsAffected = statement.executeUpdate();
        int newId = 0;
        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("Oops: " + e);
            }
            department.setId(newId);
        } else {
            department = null;
        }
        return department;
    }

    public Department get(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Departments WHERE id = ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Department department = null;
        if (results.next()) {
            department = new Department(results.getInt("id"), results.getString("name"), results.getString("location"));
        }
        return department;
    }

    public Department update(int id, Department department) throws SQLException {
        String SQL = "UPDATE Departments " +
                "SET name = ? ," +
                "location = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        statement.setInt(3, id);
        int rowsAffected = statement.executeUpdate();
        Department updatedDepartment = null;
        if (rowsAffected > 0) {
            updatedDepartment = this.get(id);
        }
        return updatedDepartment;
    }

    public Department delete(int id) throws SQLException {
        String SQL = "DELETE FROM Departments WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Department deletedDepartment = this.get(id);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedDepartment = null;
        }
        return deletedDepartment;
    }
}
