package com.booleanuk.api.employee;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeRepository {

    DataSource dataSource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDataBase;
    Connection connection;

    public EmployeeRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();


    }

    private void getDatabaseCredentials() {
        try (InputStream inputStream = new FileInputStream("src/main/resources/config.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            this.dbUser = properties.getProperty("db.user");
            this.dbURL = properties.getProperty("db.url");
            this.dbPassword = properties.getProperty("db.password");
            this.dbDataBase = properties.getProperty("db.database");
        } catch (Exception e) {
            System.out.println("Oh no: " + e);
        }
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDataBase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> employeesList = new ArrayList<>();
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM employee");

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Employee employee = new Employee(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("jobName"),
                    resultSet.getString("salaryGrade"),
                    resultSet.getString("department"));
            employeesList.add(employee);
        }
        return employeesList;
    }

    public Employee getOne(int id) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM employee WHERE id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Employee employee = null;
        if (resultSet.next()) {
            employee = new Employee(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("jobName"),
                    resultSet.getString("salaryGrade"),
                    resultSet.getString("department"));
        }
        return employee;
    }

    public Employee add(Employee employee) throws SQLException {
        String SQL = "INSERT INTO employee(name, jobName, salaryGrade, department) VALUES(?, ?, ? , ?)";
        PreparedStatement preparedStatement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, employee.getName());
        preparedStatement.setString(2, employee.getJobName());
        preparedStatement.setString(3, employee.getSalaryGrade());
        preparedStatement.setString(4, employee.getDepartment());
        int affectedRows = preparedStatement.executeUpdate();
        int id = 0;
        if (affectedRows > 0) {
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("Oh no: " + e);
            }
            employee.setId(id);
        } else {
            employee = null;
        }
        return employee;
    }

    public Employee update(int id, Employee employee) throws SQLException {
        String SQL = "UPDATE employee " + "SET name = ?, " + "jobName = ?, " + "salaryGrade = ?, " + "department = ? " + "WHERE id = ?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
        preparedStatement.setString(1, employee.getName());
        preparedStatement.setString(2, employee.getJobName());
        preparedStatement.setString(3, employee.getSalaryGrade());
        preparedStatement.setString(4, employee.getDepartment());
        preparedStatement.setInt(5, id);
        int affectedRows = preparedStatement.executeUpdate();
        Employee updateEmployee = null;
        if (affectedRows > 0) {
            updateEmployee = this.getOne(id);
        }
        return updateEmployee;
    }

    public Employee delete(int id) throws SQLException {
        String SQL = "DELETE FROM employee WHERE id=?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
        Employee deleteEmployee = null;
        deleteEmployee = this.getOne(id);
        preparedStatement.setInt(1, id);
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows == 0) {
            deleteEmployee = null;
        }
        return deleteEmployee;
    }
}
