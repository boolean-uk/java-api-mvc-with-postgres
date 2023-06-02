package com.booleanuk.api.employee;

import com.booleanuk.api.SQLConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    private final SQLConnection db;

    public EmployeeRepository() throws SQLException {
        db = SQLConnection.getInstance();
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();

        PreparedStatement statement = this.db.getConnection().prepareStatement("SELECT * FROM Employee");
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Employee employee = new Employee(results.getLong("id"),
                    results.getString("name"), results.getString("jobName"),
                    results.getString("salarygrade"), results.getString("department"));
            employees.add(employee);
        }
        return employees;
    }

    public Employee get(long id) throws SQLException {
        PreparedStatement statement = this.db.getConnection().prepareStatement("SELECT * FROM Employee WHERE id = ?");
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Employee employee = null;
        if (results.next()) {
            employee = new Employee(results.getLong("id"),
                    results.getString("name"), results.getString("jobName"),
                    results.getString("salarygrade"), results.getString("department"));
        }
        return employee;
    }

    public Employee update(long id, Employee employee) throws SQLException {
        String SQL = "UPDATE Employee " +
                "SET name = ? ," +
                "jobName = ? ," +
                "salaryGrade = ? ," +
                "department = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.db.getConnection().prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
        statement.setLong(5, id);
        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if (rowsAffected > 0) {
            updatedEmployee = this.get(id);
        }
        return updatedEmployee;
    }

    public Employee delete(long id) throws SQLException {
        String SQL = "DELETE FROM Employee WHERE id = ?";
        PreparedStatement statement = this.db.getConnection().prepareStatement(SQL);
        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();

        Employee deletedEmployee = this.get(id);
        if (rowsAffected == 0) {
            deletedEmployee = null;
        }
        return deletedEmployee;
    }

    public Employee add(Employee customer) throws SQLException {
        String SQL = "INSERT INTO Employee(name, jobName, salaryGrade, department) VALUES(?, ?, ?, ?)";
        PreparedStatement statement = this.db.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, customer.getName());
        statement.setString(2, customer.getJobName());
        statement.setString(3, customer.getSalaryGrade());
        statement.setString(4, customer.getDepartment());
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
            customer.setId(newId);
        } else {
            customer = null;
        }
        return customer;
    }
}
