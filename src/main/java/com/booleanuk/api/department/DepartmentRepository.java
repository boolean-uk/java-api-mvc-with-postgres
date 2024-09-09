package com.booleanuk.api.department;

import com.booleanuk.api.server.ServerConnection;

import java.sql.*;
import java.util.ArrayList;


public class DepartmentRepository {
    private Connection connection = ServerConnection.getInstance();

    public DepartmentRepository() throws SQLException {
    }


    public ArrayList<Department> getAll() throws SQLException {
        ArrayList<Department> departments = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM departments");

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            Department department = new Department(
                    result.getLong("id"),
                    result.getString("name"),
                    result.getString("location")
            );
            departments.add(department);
        }

        return departments;
    }

    public Department get(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM departments WHERE id = ?");
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Department department = null;

        if (results.next()) {
            department = new Department(results.getLong("id"), results.getString("name"), results.getString("location"));
        }
        return department;
    }

    public Department update(long id, Department department) throws SQLException {
        String SQL = "UPDATE departments " +
                "SET name = ? ," +
                "location = ? ," +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        statement.setLong(5, id);
        int rowsAffected = statement.executeUpdate();
        Department updatedDepartment = null;
        if (rowsAffected > 0) {
            updatedDepartment = this.get(id);
        }
        return updatedDepartment;
    }

    public Department delete(long id) throws SQLException {
        String SQL = "DELETE FROM departments WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        // Get the customer we're deleting before we delete them
        Department deletedDepartment = null;
        deletedDepartment = this.get(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the customer we're deleting if we didn't delete them
            deletedDepartment = null;
        }
        return deletedDepartment;
    }

    public Department add(Department department) throws SQLException {
        String SQL = "INSERT INTO departments(name, location) VALUES(?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        int rowsAffected = statement.executeUpdate();
        long newId = 0;
        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getLong(1);
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
}
