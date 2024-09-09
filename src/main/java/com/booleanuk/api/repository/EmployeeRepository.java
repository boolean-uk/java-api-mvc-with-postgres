package com.booleanuk.api.repository;

import com.booleanuk.api.db.DatabaseConnection;
import com.booleanuk.api.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    public EmployeeRepository() throws SQLException {
    }

    public List<Employee> getall() throws SQLException {
        List<Employee> toReturn = new ArrayList<>();
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM employees");

        ResultSet res = statement.executeQuery();

        while (res.next()) {
            Employee e = new Employee(
                    res.getLong("id"),
                    res.getString("name"),
                    res.getString("jobName"),
                    res.getString("salaryGrade"),
                    res.getString("department")
            );
            toReturn.add(e);
        }

        return toReturn;
    }


    public Employee getOne(long id) throws SQLException {
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM employees WHERE id = ?");
        statement.setLong(1, id);

        ResultSet res = statement.executeQuery();
        Employee e = null;
        if (res.next()) {
            e = new Employee(
                    res.getLong("id"),
                    res.getString("name"),
                    res.getString("jobName"),
                    res.getString("salaryGrade"),
                    res.getString("department")
            );
        }
        return e;
    }

    public Employee add(Employee employee) throws SQLException {
        String query = "INSERT INTO employees (name, jobName, salaryGrade, department) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
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
            employee.setId(newId);
        } else {
            employee = null;
        }
        return employee;
    }

    public Employee delete(long id) throws SQLException {
        String query = "DELETE FROM employees WHERE id = ?";
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(query);
        Employee toDelete = null;
        toDelete = this.getOne(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            toDelete = null;
        }
        return toDelete;
    }

    public Employee update(long id, Employee newData) throws SQLException {
        String SQL = "UPDATE employees " +
                "SET name = ? ," +
                "jobName = ? ," +
                "salaryGrade = ? ," +
                "department = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(SQL);
        statement.setString(1, newData.getName());
        statement.setString(2, newData.getJobName());
        statement.setString(3, newData.getSalaryGrade());
        statement.setString(4, newData.getDepartment());
        statement.setLong(5, id);
        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if (rowsAffected > 0) {
            updatedEmployee = this.getOne(id);
        }
        return updatedEmployee;
    }

}
