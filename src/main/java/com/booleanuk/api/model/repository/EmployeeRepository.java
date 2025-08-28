package com.booleanuk.api.model.repository;

import com.booleanuk.api.model.pojo.Employee;
import org.postgresql.ds.PGSimpleDataSource;

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
    private static String url;
    private static String user;
    private static String pws;

    private Connection connection;

    static {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            url = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            pws = prop.getProperty("db.password");

        } catch (Exception e) {
            System.out.println("Oops: " + e);
        }
    }

    public EmployeeRepository() throws SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL(url + "?user=" + user + "&password=" + pws);

        connection = ds.getConnection();

    }

    public void testConnection() throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM Customer"
        );
        ResultSet results = ps.executeQuery();

        while (results.next()) {
            System.out.println("Customer: " + results.getString("name"));
        }
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM employee"
        );

        ResultSet results = ps.executeQuery();

        while (results.next()) {
            employees.add(new Employee(results));
        }

        return employees;
    }

    public Employee getById(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM employee WHERE id = ?"
        );
        ps.setInt(1, id);

        ResultSet results = ps.executeQuery();

        if (results.next()) {
            return new Employee(results);
        }

        return null;
    }

    public Employee update(int id, Employee employee) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "UPDATE employee SET name = ?, job_name = ?, salary_grade = ?, department = ? WHERE id = ?"
        );
        ps.setString(1, employee.getName());
        ps.setString(2, employee.getJobName());
        ps.setString(3, employee.getSalaryGrade());
        ps.setString(4, employee.getDepartment());
        ps.setInt(5, id);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            return getById(id);
        }
        return null;
    }

    public Employee add(Employee employee) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO employee (name, job_name, salary_grade, department) VALUES (?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS
        );
        ps.setString(1, employee.getName());
        ps.setString(2, employee.getJobName());
        ps.setString(3, employee.getSalaryGrade());
        ps.setString(4, employee.getDepartment());

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee.setId(generatedKeys.getLong(1));
                }
                return employee;
            }
        }
        return null;
    }

    public Employee delete(int id) throws SQLException {
        Employee employee = getById(id);
        if (employee == null) {
            return null;
        }

        PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM employee WHERE id = ?"
        );
        ps.setInt(1, id);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            return employee;
        }
        return null;
    }
}
