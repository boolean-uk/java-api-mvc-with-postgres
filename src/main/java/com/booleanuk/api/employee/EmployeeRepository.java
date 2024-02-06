package com.booleanuk.api.employee;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        this.getDatabaseCredentials();
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();
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
            System.out.println("Oops: " + e);
        }
    }

    private DataSource createDataSource() {
        String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase
                +  "?user=" + this.dbUser + "&password=" + this.dbPassword;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        return dataSource;
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> all_employees = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees");
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Employee employee = new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("job_name"),
                    rs.getString("salary_grade"),
                    rs.getString("department")
            );
            all_employees.add(employee);
        }
        return all_employees;
    }

    public Employee getOne(int id) throws SQLException {
        String _SQL = "SELECT * FROM employees WHERE id= ?";
        PreparedStatement statement = this.connection.prepareStatement(_SQL);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        Employee employee = null;
        if (rs.next()) {
            employee = new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("job_name"),
                    rs.getString("salary_grade"),
                    rs.getString("department"));
        }
        return employee;
    }

    public Employee deleteOne(int id) throws SQLException {
        String _SQL = "DELETE FROM employees WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(_SQL);
        Employee deletedEmployee = this.getOne(id);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedEmployee = null;
        }
        return deletedEmployee;
    }

    public Employee createOne(Employee employee) throws SQLException {
        String _SQL = "INSERT INTO employees (name, job_name, salary_grade, department) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(_SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJob_name());
        statement.setString(3, employee.getSalary_grade());
        statement.setString(4, employee.getDepartment());
        int rowsAffected = statement.executeUpdate();
        int newId = -1;
        if (rowsAffected > 0) {
            try(ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("Oh nos: " + e);
            }
            employee.setId(newId);
        } else {
            employee = null;
        }
        return employee;
    }

    public Employee updateOne(int id, Employee employee) throws SQLException {
        String _SQL = "UPDATE employees " +
                "SET name = ?, " +
                "job_name = ?, " +
                "salary_grade = ?, " +
                "department = ? " +
                "WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(_SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJob_name());
        statement.setString(3, employee.getSalary_grade());
        statement.setString(4, employee.getDepartment());
        statement.setInt(5, id);
        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if (rowsAffected > 0) {
            updatedEmployee = this.getOne(id);
        }
        return updatedEmployee;
    }
}
