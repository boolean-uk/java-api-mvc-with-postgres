package com.booleanuk.extension.employee;

import com.booleanuk.extension.DatabaseManager;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeRepository {
    Connection connection;

    public EmployeeRepository() throws SQLException {
        this.connection = DatabaseManager.getInstance().getDataSource().getConnection();
    }

    public List<Employee> getAll() throws SQLException  {
        List<Employee> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM EmployeeS");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Employee theEmployee = new Employee(results.getInt("id"), results.getString("name"), results.getString("jobName"), results.getInt("salaryGrade_id"), results.getInt("department_id"));
            everyone.add(theEmployee);
        }
        return everyone;
    }

    public Employee getOne(int id) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Employee employee = null;
        if (results.next()) {
            employee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getInt("salaryGrade_id"),
                    results.getInt("department_id")

            );
            return employee;
        }
        return null;
    }

    public Employee add(Employee employee) throws SQLException {
        String SQL = "INSERT INTO Employees(name, jobName, salaryGrade_id, department_id) VALUES(?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setInt(3, employee.getSalaryGrade_id());
        statement.setInt(4, employee.getDepartment_id());
        int rowsAffected = statement.executeUpdate();
        int newId = 0;
        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getInt(1);
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

    public Employee update(int id, Employee employee) throws SQLException {
        String SQL = "UPDATE employees " +
                "SET name = ? ," +
                "jobName = ? ," +
                "salaryGrade_id = ? ," +
                "department_id = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setInt(3, employee.getSalaryGrade_id());
        statement.setInt(4, employee.getDepartment_id());
        statement.setLong(5, id);
        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if (rowsAffected > 0) {
            updatedEmployee = this.getOne(id);
        }
        return updatedEmployee;
    }

    public Employee delete(int id) throws SQLException {
        // Get the customer we're deleting before we delete them
        Employee deletedEmployee = this.getOne(id);

        String SQL = "DELETE FROM Employees WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the customer we're deleting if we didn't delete them
            deletedEmployee = null;
        }
        return deletedEmployee;
    }
}
