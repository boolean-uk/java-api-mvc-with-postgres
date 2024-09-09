package com.booleanuk.api.repository;

import com.booleanuk.api.db.DatabaseConnection;
import com.booleanuk.api.model.Department;
import com.booleanuk.api.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository {

    public DepartmentRepository() {

    }

    public List<Department> getAll() throws SQLException {
        List<Department> toReturn = new ArrayList<>();
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM departments");

        ResultSet res = statement.executeQuery();

        while (res.next()) {
            Department d = new Department(
                    res.getLong("id"),
                    res.getString("name"));
            toReturn.add(d);
        }

        return toReturn;
    }

    public Department getOne(long id) throws SQLException {
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM departments WHERE id = ?");
        statement.setLong(1, id);

        ResultSet res = statement.executeQuery();
        Department d = null;
        if (res.next()) {
            d = new Department(
                    res.getLong("id"),
                    res.getString("name"));
        }
        return d;
    }

    public Department add(Department department) throws SQLException{
        String query = "INSERT INTO departments (name) VALUES (?)";
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, department.getName());

        final int rowsAffected = statement.executeUpdate();
        long newId = 0;
        if (rowsAffected > 0) {
            ResultSet res;
            try {
                res = statement.getGeneratedKeys();
                if (res.next()) {
                    newId = res.getLong(1);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            department.setId(newId);
        } else {
            department = null;
        }
        return department;
    }
    
    public Department delete(long id) throws SQLException{
        String query =  "DELETE FROM departments WHERE id = ?";
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(query);
        Department toDelete = null;
        toDelete = this.getOne(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0 ) return null;
        return toDelete;
    }

    public Department update(long id, Department newData) throws SQLException{
        String SQL = "UPDATE departments " +
                "SET name = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(SQL);
        statement.setString(1, newData.getName());
        statement.setLong(2, id);
        int rowsAffected = statement.executeUpdate();
        Department updatedDepartment = null;
        if (rowsAffected > 0) {
            updatedDepartment = this.getOne(id);
        }
        return updatedDepartment;
    }

}
