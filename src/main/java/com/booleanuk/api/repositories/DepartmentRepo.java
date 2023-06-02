package com.booleanuk.api.repositories;

import com.booleanuk.api.models.Department;
import com.booleanuk.api.sqlUtils.SQLConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepo {

    private final SQLConnection db;

    public DepartmentRepo() throws SQLException {
        db = SQLConnection.getInstance();
    }

    public List<Department> getAll() throws SQLException {
        List<Department> departments = new ArrayList<>();

        PreparedStatement statement = this.db.getConnection().prepareStatement("SELECT * FROM Department");
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Department department = new Department(results.getLong("id"),
                    results.getString("name"), results.getString("location"));
            departments.add(department);
        }
        return departments;
    }

    public Department get(long id) throws SQLException {
        PreparedStatement statement = this.db.getConnection().prepareStatement("SELECT * FROM Department WHERE id = ?");
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Department department = null;
        if (results.next()) {
            department = new Department(results.getLong("id"),
                    results.getString("name"), results.getString("location"));
        }
        return department;
    }

    public Department update(long id, Department department) throws SQLException {
        String SQL = "UPDATE Department " +
                "SET name = ? ," +
                "location = ? ," +
                "WHERE id = ? ";
        PreparedStatement statement = this.db.getConnection().prepareStatement(SQL);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        statement.setLong(3, id);
        int rowsAffected = statement.executeUpdate();
        Department updatedDepartment = null;
        if (rowsAffected > 0) {
            updatedDepartment = this.get(id);
        }
        return updatedDepartment;
    }

    public Department delete(long id) throws SQLException {
        String SQL = "DELETE FROM Department WHERE id = ?";
        PreparedStatement statement = this.db.getConnection().prepareStatement(SQL);
        statement.setLong(1, id);

        Department deletedDepartment = this.get(id);
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected == 0) {
            deletedDepartment = null;
        }
        return deletedDepartment;
    }

    public Department add(Department department) throws SQLException {
        String SQL = "INSERT INTO Department(name, location, maxDepartment) VALUES(?, ?, ?)";
        PreparedStatement statement = this.db.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
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
