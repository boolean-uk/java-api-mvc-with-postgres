package com.booleanuk.api.repository;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.booleanuk.api.model.Employee;
import org.postgresql.ds.PGSimpleDataSource;

public class EmployeeRepository {

    private DataSource datasource;
    private String dbUser;
    private String dbURL;
    private String dbPassword;
    private String dbDatabase;
    private Connection connection;

    public EmployeeRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.datasource = this.createDataSource();
        this.connection = this.datasource.getConnection();
    }

    private void getDatabaseCredentials() {
        try (InputStream input = new FileInputStream(("src/main/resources/config.properties"))) {
            Properties properties = new Properties();
            properties.load(input);
            this.dbUser = properties.getProperty("db.user");
            this.dbURL = properties.getProperty("db.url");
            this.dbPassword = properties.getProperty("db.password");
            this.dbDatabase = properties.getProperty("db.database");
        } catch(Exception e) {
            System.out.println(e);
        }

    }

    private DataSource createDataSource() {

        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser +"&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;

    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();


        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employee");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Employee employee = new Employee(resultSet.getInt(1), resultSet.getString(2),resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
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
            employee = new Employee(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
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
        statement.setString(4, employee.getDepartment());
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
        statement.setString(4, employee.getDepartment());
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
