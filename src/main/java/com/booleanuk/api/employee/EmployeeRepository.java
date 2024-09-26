package com.booleanuk.api.employee;

import org.postgresql.ds.PGSimpleDataSource;

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

    public List<Employee> getAll() throws SQLException {
        List<Employee> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement(
                "SELECT * FROM Employees");
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Employee theEmployee = new Employee(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("job_name"),
                    rs.getInt("salary_id"),
                    rs.getInt("department_id"));
            everyone.add(theEmployee);
        }
        return everyone;
    }

    public Employee get(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement(
                "SELECT * FROM Employees WHERE id= ?");
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        Employee employee = null;
        if (rs.next()) {
            employee = new Employee(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("job_name"),
                    rs.getInt("salary_id"),
                    rs.getInt("department_id"));
        }
        return employee;
    }

    public Employee update(int id, Employee employee) throws SQLException {
        String SQL = "UPDATE Employees " +
                "SET name = ?, " +
                "job_name = ?, " +
                "salary_id = ?, " +
                "department_id = ? " +
                "WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setInt(3, employee.getSalaryID());
        statement.setInt(4, employee.getDepartmentID());
        statement.setInt(5, id);
        int rowAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if (rowAffected > 0) {
            updatedEmployee = this.get(id);
        }
        return updatedEmployee;
    }

    public Employee delete(int id) throws SQLException {
        String SQL = "DELETE FROM Employees WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Employee deletedEmployee = this.get(id);

        statement.setInt(1, id);
        int rowAffected = statement.executeUpdate();
        if (rowAffected == 0) {
            deletedEmployee = null;
        }
        return deletedEmployee;
    }

    public Employee add(Employee employee) throws SQLException {
        String SQL =
            "INSERT INTO Employees (name, job_name, salary_id, department_id) "
                    + "VALUES (?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setInt(3, employee.getSalaryID());
        statement.setInt(4, employee.getDepartmentID());
        int rowAffected = statement.executeUpdate();
        int newID = -1;
        if (rowAffected > 0) {
            try (ResultSet rs = statement.getResultSet()) {
                if (rs.next()) {
                    newID = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("Oh nos: " + e);
            }
            employee.setId(newID);
        } else {
            employee = null;
        }
        return employee;
    }

    //---------------------- Private section ----------------------//

    private void getDatabaseCredentials() {
        try (InputStream input = new FileInputStream(
                "src/main/resources/config.properties")) {
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
                + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

}
