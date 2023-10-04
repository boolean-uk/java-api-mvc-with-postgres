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

    DataSource datasource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;
    Connection connection;

    public EmployeeRepo() throws SQLException {
        this.getDatabaseCredentials();
        this.datasource = this.createDataSource();
        this.connection = this.datasource.getConnection();
    }

    private void getDatabaseCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");
        } catch (Exception e) {
            System.out.println("Oh dear: " + e);
        }
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        return dataSource;
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
                    results.getString("salaryGrade"),
                    results.getString("department")
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
                            results.getString("salaryGrade"),
                            results.getString("department")
                    );
                }
            }
        }
        return theEmployee;
    }

    public Employee add(Employee employee) throws SQLException {
        String sql = "INSERT INTO EMPLOYEES (fullname, jobName, salaryGrade, department) VALUES (?, ?, ?, ?) RETURNING id";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, employee.getFullname());
            statement.setString(2, employee.getJobName());
            statement.setString(3, employee.getSalaryGrade());
            statement.setString(4, employee.getDepartment());
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
        String sql = "UPDATE employees SET fullname = ?, jobName = ?, salaryGrade = ?, department = ? WHERE id = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, employee.getFullname());
            statement.setString(2, employee.getJobName());
            statement.setString(3, employee.getSalaryGrade());
            statement.setString(4, employee.getDepartment());
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