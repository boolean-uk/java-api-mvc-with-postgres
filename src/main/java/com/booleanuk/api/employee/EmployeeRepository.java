package com.booleanuk.api.employee;



import com.booleanuk.api.server.ServerConnection;

import java.sql.*;
import java.util.ArrayList;
public class EmployeeRepository {
    private Connection connection = ServerConnection.getInstance();

    public EmployeeRepository() throws SQLException {
    }

    public void connectToDatabase() throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees");

        ResultSet result = statement.executeQuery();

        while(result.next()) {
            String id = "" + result.getLong("id");
            String name = result.getString("name");
            String jobName = result.getString("jobName");
            String salaryGrade = result.getString("salaryGrade_id");
            String department = result.getString("department_id");
            System.out.println(id + " - " + name + " - "  + jobName + " - "  + salaryGrade + " - "  + department);
        }
    }


    public ArrayList<Employee> getAll() throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees");

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            Employee employee = new Employee(
                    result.getLong("id"),
                    result.getString("name"),
                    result.getString("jobName"),
                    result.getString("salaryGrade_id"),
                    result.getString("department_id")
            );
            employees.add(employee);
        }

        return employees;
    }

    public Employee get(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Employee employee = null;

        if (results.next()) {
            employee = new Employee(results.getLong("id"), results.getString("name"), results.getString("jobName"), results.getString("salaryGrade"), results.getString("department"));
        }
        return employee;
    }

    public Employee update(long id, Employee employee) throws SQLException {
        String SQL = "UPDATE employees " +
                "SET name = ? ," +
                "jobName = ? ," +
                "salaryGrade_id = ? ," +
                "department_id = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
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
        String SQL = "DELETE FROM employees WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        // Get the customer we're deleting before we delete them
        Employee deletedEmployee = null;
        deletedEmployee = this.get(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the customer we're deleting if we didn't delete them
            deletedEmployee = null;
        }
        return deletedEmployee;
    }

    public Employee add(Employee employee) throws SQLException {
        String SQL = "INSERT INTO employees(name, jobName, salaryGrade_id, department_id) VALUES(?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
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
