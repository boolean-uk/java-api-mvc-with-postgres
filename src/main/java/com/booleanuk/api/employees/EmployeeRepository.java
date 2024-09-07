package com.booleanuk.api.employees;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.booleanuk.api.DatabaseResource;

public class EmployeeRepository {

    DataSource datasource;
    Connection connection;

    public EmployeeRepository() throws SQLException {
        this.datasource = DatabaseResource.getInstance().datasource;
        this.connection = this.datasource.getConnection();
    }

    public Employee create(Employee newEmployee) throws SQLException {
        String sql = "INSERT INTO employees(name, jobName, salaryGrade, department) VALUES(?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, newEmployee.getName());
        statement.setString(2, newEmployee.getJobName());
        statement.setInt(3, newEmployee.getSalaryGrade());
        statement.setInt(4, newEmployee.getDepartment());

        if (statement.executeUpdate() == 0) {
            return null;
        } else {
            long id = 0;
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    id = resultSet.getLong(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            newEmployee.setId(id);
        }
        return newEmployee;
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Employee employee = new Employee(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("jobName"),
                    resultSet.getInt("salaryGrade"),
                    resultSet.getInt("department")
            );
            employees.add(employee);
        }
        return employees;
    }

    public Employee getSpecific(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        Employee employee = null;
        if (resultSet.next()) {
            employee = new Employee(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("jobName"),
                    resultSet.getInt("salaryGrade"),
                    resultSet.getInt("department")
            );
        }
        return employee;
    }

    public Employee update(long id, Employee employee) throws SQLException {
        String SQL = "UPDATE employees " +
                "SET name = ? ," +
                "jobName = ? ," +
                "salaryGrade = ? ," +
                "department = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setInt(3, employee.getSalaryGrade());
        statement.setInt(4, employee.getDepartment());
        statement.setLong(5, id);
        Employee updatedEmployee = null;
        if (statement.executeUpdate() > 0) {
            updatedEmployee = this.getSpecific(id);
        }
        return updatedEmployee;
    }

    public Employee delete(long id) throws SQLException {
        String SQL = "DELETE FROM employees WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Employee deletedEmployee = null;
        deletedEmployee = this.getSpecific(id);
        statement.setLong(1, id);
        if (statement.executeUpdate() == 0) {
            deletedEmployee = null;
        }
        return deletedEmployee;
    }
}
