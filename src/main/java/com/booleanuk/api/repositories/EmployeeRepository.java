package com.booleanuk.api.repositories;

import com.booleanuk.api.data.DbAccess;
import com.booleanuk.api.models.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    private DbAccess dbAcces;
    private Connection employee;
    private Connection connection;

    public EmployeeRepository() throws SQLException {
        this.dbAcces = new DbAccess();
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> everyone = new ArrayList<>();
        PreparedStatement statement = this.dbAcces.getAccess().prepareStatement("SELECT * FROM Employee");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Employee theEmployee = new Employee(
                    results.getLong("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department"), results.getString("phone"));
            everyone.add(theEmployee);
        }
        return everyone;
    }

    public Employee get(long id) throws SQLException {
        PreparedStatement statement = this.employee.prepareStatement("SELECT * FROM Customers WHERE id = ?");
        // Choose set**** matching the datatype of the missing element
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Employee employee = null;
        if (results.next()) {
            employee = new Employee(results.getLong("id"), results.getString("name"), results.getString("jobName"), results.getString("salaryGrade"), results.getString("department"), results.getString("phone"));
        }
        return employee;
    }
//
    public Employee update(long id, Employee employee) throws SQLException {
        String SQL = "UPDATE Employee " +
                "SET name = ? ," +
                "jobName = ? ," +
                "salaryGrade = ? ," +
                "department = ? " +
                "phone = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
        statement.setString(5, employee.getPhone());
        statement.setLong(6, id);
        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if (rowsAffected > 0) {
            updatedEmployee = this.get(id);
        }
        return updatedEmployee;
    }


    public Employee delete(long id) throws SQLException {
        String SQL = "DELETE FROM Customers WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        // Get the employee we're deleting before we delete them
        Employee deletedEmployee = null;
        deletedEmployee = this.get(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the employee we're deleting if we didn't delete them
            deletedEmployee = null;
        }
        return deletedEmployee;
    }

    public Employee add(Employee employee) throws SQLException {
        String SQL = "INSERT INTO Customers(name, address, email, phone) VALUES(?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
        statement.setString(5, employee.getPhone());
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
            employee.setId(newId);
        } else {
            employee = null;
        }
        return employee;
    }
}
