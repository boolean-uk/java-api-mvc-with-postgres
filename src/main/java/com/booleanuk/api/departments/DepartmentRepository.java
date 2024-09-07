package com.booleanuk.api.departments;

import com.booleanuk.api.DatabaseResource;
import com.booleanuk.api.exceptions.InvalidRequest;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository {
    DataSource datasource;
    Connection connection;

    public DepartmentRepository() throws SQLException {
        this.datasource = DatabaseResource.getInstance().datasource;
        this.connection = this.datasource.getConnection();
    }

    public Department create(Department newDepartment) throws SQLException {

        // Check if name already exist
        String sqlCheck = "SELECT * FROM departments WHERE name = ?";
        PreparedStatement stm = this.connection.prepareStatement(sqlCheck, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, newDepartment.getName());
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            throw new InvalidRequest("Could not create new department, name already exist");
        }

        String sql = "INSERT INTO departments(name, location) VALUES(?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, newDepartment.getName());
        statement.setString(2, newDepartment.getLocation());

        if (statement.executeUpdate() == 0) {
            throw new InvalidRequest("Could not create new department, please check that all required fields are correct");
        } else {
            long id = 0;
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    id = resultSet.getLong(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            newDepartment.setId(id);
        }
        return newDepartment;
    }

    public List<Department> getAll() throws SQLException {
        List<Department> departments = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM departments");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Department employee = new Department(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("location")
            );
            departments.add(employee);
        }
        return departments;
    }

    public Department getSpecific(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM departments WHERE id = ?");
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        Department employee = null;
        if (resultSet.next()) {
            employee = new Department(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("location")
            );
        }
        return employee;
    }

    public Department update(long id, Department employee) throws SQLException {
        String SQL = "UPDATE departments " +
                "SET name = ? ," +
                "location = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getLocation());
        statement.setLong(3, id);
        Department updatedDepartment = null;
        if (statement.executeUpdate() > 0) {
            updatedDepartment = this.getSpecific(id);
        }
        return updatedDepartment;
    }

    public Department delete(long id) throws SQLException {
        String SQL = "DELETE FROM departments WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Department deletedDepartment = null;
        deletedDepartment = this.getSpecific(id);
        statement.setLong(1, id);
        if (statement.executeUpdate() == 0) {
            deletedDepartment = null;
        }
        return deletedDepartment;
    }
}
