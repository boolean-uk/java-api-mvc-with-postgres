package com.booleanuk.api.repository;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.booleanuk.api.model.Employee;
import com.booleanuk.api.util.UtilClass;
import org.postgresql.ds.PGSimpleDataSource;

public class EmployeeRepository {

    private Connection connection;

    public EmployeeRepository() throws SQLException {
        UtilClass util = new UtilClass();
        this.connection = util.getConnection();
    }



    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();


        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employee");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Employee employee = new Employee(resultSet.getInt(1), resultSet.getString(2),resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5));
            employees.add(employee);
        }

        return  employees;
    }

    public Employee get(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employee WHERE employeeId = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Employee employee = null;
        if (resultSet.next()) {
            employee = new Employee(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5));
        }
        return employee;
    }

    public Employee update(int id, Employee employee) throws SQLException {
        String SQL = "UPDATE Employee " +
                "SET name = ? ," +
                "jobName = ? ," +
                "salaryGrade = ? ," +
                "department = ? " +
                "WHERE employeeId = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setInt(4, employee.getDepartmentId());
        statement.setInt(5, id);
        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if (rowsAffected > 0) {
            updatedEmployee = this.get(id);
        }
        return updatedEmployee;
    }

    public Employee delete(int id) throws SQLException {
        String SQL = "DELETE FROM Employee WHERE employeeId = ?";
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
        String SQL = "INSERT INTO Employee(name, jobName, salaryGrade, department) VALUES(?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setInt(4, employee.getDepartmentId());
        int rowsAffected = statement.executeUpdate();
        int newId = 0;
        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            employee.setId(newId);
        } else {
            employee = null;
        }
        return employee;
    }


}
