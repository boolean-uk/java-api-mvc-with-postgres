package com.booleanuk.api.extension.repositories;

import com.booleanuk.api.database.Database;
import com.booleanuk.api.database.PostgresDatabase;
import com.booleanuk.api.extension.models.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository {
    private final Database db = new PostgresDatabase();

    public List<Department> getAll() {
        List<Department> departments = new ArrayList<>();

        try (Connection connection = db.connection()) {
            String selectAll = "SELECT * FROM departments";
            PreparedStatement statement = connection.prepareStatement(selectAll);
            ResultSet results = statement.executeQuery();

            while(results.next()) {
                departments.add(
                        new Department(
                                results.getInt("id"),
                                results.getString("name"),
                                results.getString("location")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return departments;
    }

    public Department get(int id) {
        Department department = null;

        try (Connection connection = db.connection()) {
            String selectId = "SELECT * FROM departments WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(selectId);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

            while(results.next()) {
                department = new Department(
                        results.getInt("id"),
                        results.getString("name"),
                        results.getString("location")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return department;
    }

    public Department update(int id, Department department) {
        Department requestedDepartment = null;

        try (Connection connection = db.connection()) {
            String updateId = "UPDATE departments " +
                    "SET name=?, location=? " +
                    "WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(updateId);
            statement.setString(1, department.getName());
            statement.setString(2, department.getLocation());
            statement.setInt(3, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0)
                requestedDepartment = get(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return requestedDepartment;
    }

    public Department add(Department department) {
        Department newDepartment = null;

        try (Connection connection = db.connection()) {
            String addId = "INSERT INTO departments (name, location) " +
                    "values (?, ?)";
            PreparedStatement statement = connection.prepareStatement(addId, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, department.getName());
            statement.setString(2, department.getLocation());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet results = statement.getGeneratedKeys()) {
                    if (results.next()) {
                        int id = results.getInt(1);
                        newDepartment = get(id);
                    }
                } catch (SQLException e) {
                    System.out.println("Error getting the generated key" + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return newDepartment;
    }

    public Department delete(int id) {
        Department department = get(id);

        try (Connection connection = db.connection()) {
            String deleteId = "DELETE FROM departments WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(deleteId);
            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0)
                department = null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return department;
    }
}
