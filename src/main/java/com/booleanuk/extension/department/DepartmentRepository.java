package com.booleanuk.extension.department;

import com.booleanuk.extension.DatabaseManager;
import com.booleanuk.extension.department.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository {
    Connection connection;

    public DepartmentRepository() throws SQLException {
        this.connection = DatabaseManager.getInstance().getDataSource().getConnection();
    }

    public List<Department> getAll() throws SQLException  {
        List<Department> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM DepartmentS");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Department theDepartment = new Department(results.getInt("id"), results.getString("name"), results.getString("location"));
            everyone.add(theDepartment);
        }
        return everyone;
    }

    public Department getOne(int id) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM departments WHERE id = ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Department department = null;
        if (results.next()) {
            department = new Department(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("location")

            );
            return department;
        }
        return null;
    }

    public Department add(Department department) throws SQLException {
        String SQL = "INSERT INTO Departments(name, location) VALUES(?, ?)";
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

    public Department update(int id, Department department) throws SQLException {
        String SQL = "UPDATE departments " +
                "SET name = ? ," +
                "location = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        statement.setLong(3, id);
        int rowsAffected = statement.executeUpdate();
        Department updatedDepartment = null;
        if (rowsAffected > 0) {
            updatedDepartment = this.getOne(id);
        }
        return updatedDepartment;
    }

    public Department delete(int id) throws SQLException {
        // Get the customer we're deleting before we delete them
        Department deletedDepartment = this.getOne(id);

        String SQL = "DELETE FROM Departments WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the customer we're deleting if we didn't delete them
            deletedDepartment = null;
        }
        return deletedDepartment;
    }
}
