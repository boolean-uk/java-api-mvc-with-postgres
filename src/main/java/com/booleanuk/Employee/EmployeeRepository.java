package com.booleanuk.Employee;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeRepository {
    private DataSource dataSource;
    private String dbUser;
    private String dbURL;
    private String dbPassword;
    private String dbDatabase;
    private Connection connection;

    public EmployeeRepository() throws SQLException {
        getDatabaseCredentials();
        this.dataSource = createDataSource();
        this.connection = dataSource.getConnection();
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
            System.out.println("Error loading database credentials: " + e);
        }
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbURL
                + ":5432/" + this.dbDatabase
                + "?user=" + this.dbUser
                + "&password=" + this.dbPassword
                + "&sslmode=require";
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        return dataSource;
    }

    public Employee add(Employee employee) throws SQLException {
        String SQL = "INSERT INTO Employees(name, jobname, salarygrade, department) VALUES(?, ?, ?, ?)";
        try (PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getJobName());
            statement.setString(3, employee.getSalaryGrade());
            statement.setString(4, employee.getDepartment());
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    employee.setId(rs.getLong(1));
                }
            }
        }
        return employee;
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String SQL = "SELECT * FROM Employees";
        try (PreparedStatement statement = this.connection.prepareStatement(SQL);
             ResultSet results = statement.executeQuery()) {
            while (results.next()) {
                Employee employee = new Employee(
                        results.getLong("id"),
                        results.getString("name"),
                        results.getString("jobname"),
                        results.getString("salarygrade"),
                        results.getString("department"));
                employees.add(employee);
            }
        }
        return employees;
    }

    public Employee get(long id) throws SQLException {
        String SQL = "SELECT * FROM Employees WHERE id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(SQL)) {
            statement.setLong(1, id);
            try (ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    return new Employee(
                            results.getLong("id"),
                            results.getString("name"),
                            results.getString("jobname"),
                            results.getString("salarygrade"),
                            results.getString("department"));
                }
            }
        }
        return null;
    }

    public Employee update(long id, Employee employee) throws SQLException {
        String SQL = "UPDATE Employees SET name = ?, jobname = ?, salarygrade = ?, department = ? WHERE id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(SQL)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getJobName());
            statement.setString(3, employee.getSalaryGrade());
            statement.setString(4, employee.getDepartment());
            statement.setLong(5, id);
            statement.executeUpdate();
        }
        return get(id);
    }

    public void delete(long id) throws SQLException {
        String SQL = "DELETE FROM Employees WHERE id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(SQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
