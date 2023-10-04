package com.booleanuk.api.employee;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

@Repository
public class EmployeeRepo {

    private final Connection connection;

    public EmployeeRepo(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();

    }

    public ArrayList<Employee> getAllData() throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();
        try (
                PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees");
                ResultSet results = statement.executeQuery();
                ) {
            while (results.next()) {
                Employee theEmployee = new Employee(
                    results.getInt("id"),
                    results.getString("fullname"),
                    results.getString("jobName"),
                    results.getInt("salaryGrade"),
                    results.getInt("department")
                );
                employees.add(theEmployee);
            }
        }
        return employees;
    }

    public Employee getOne(int employeeID) throws SQLException {
        Employee theEmployee = null;
        String sql = "SELECT * FROM employees WHERE id = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, employeeID);
            try (ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    theEmployee = new Employee(
                            results.getInt("id"),
                            results.getString("fullname"),
                            results.getString("jobName"),
                            results.getInt("salaryGrade"),
                            results.getInt("department")
                    );
                }
            }
        }
        return theEmployee;
    }

    public Employee add(Employee employee) throws SQLException {
        String sql = "INSERT INTO EMPLOYEES (fullname, jobName, departmentId, salaryId) VALUES (?, ?, ?, ?) RETURNING id";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, employee.getFullname());
            statement.setString(2, employee.getJobName());
            statement.setInt(3, employee.getDepartmentId());
            statement.setInt(4, employee.getSalaryId());
            try (ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    int generateId = results.getInt(1);
                    employee.setId(generateId);
                }
            }
        }
        return employee;
    }

    public Employee update(int id, Employee employee) throws SQLException {
        String sql = "UPDATE employees SET fullname = ?, jobName = ?, departmentId = ?, salaryId = ? WHERE id = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, employee.getFullname());
            statement.setString(2, employee.getJobName());
            statement.setInt(3, employee.getDepartmentId());
            statement.setInt(4, employee.getSalaryId());
            statement.setInt(5, id);
            statement.executeUpdate();

            return getOne(employee.getId());
        }
    }

    public Employee delete (int id) throws SQLException {
        Employee employee = getOne(id);

        if (employee == null) {
            return null;
        }
        String sql = "DELETE FROM employees WHERE id = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();

            return (affectedRows > 0) ? employee : null;
        }
    }


}