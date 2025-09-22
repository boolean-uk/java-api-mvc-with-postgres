package com.booleanuk.employee;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Repository
public class EmployeeRepository {
    private DataSource dataSource;
    private String dbUser;
    private String dbURL;
    private String dbPassword;
    private String dbDatabase;
    private Connection connection;

    public EmployeeRepository(DataSource dataSource, String dbUser, String dbURL, String dbPassword, String dbDatabase) throws SQLException {
        this.getDatabaseCredentials();
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    private void getDatabaseCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");

        } catch (IOException e) {
            System.err.println("Error opening config.properties file " + e);
        }
    }

    public List<Employee> getAll() throws SQLException  {
        List<Employee> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM EMPLOYEES");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Employee theEmployee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobname"),
                    results.getString("salarygrade"),
                    results.getString("department"));
            everyone.add(theEmployee);
        }
        return everyone;
    }

    public Employee getOne(int id) throws SQLException  {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM EMPLOYEES WHERE id = ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Employee employee = null;
        if (results.next()) {
            employee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobname"),
                    results.getString("salarygrade"),
                    results.getString("department"));
        }
        return employee;
    }

    public Employee add(Employee employee) throws SQLException {
        String SQL = "INSERT INTO Employees(name, jobname, salarygrade, department) VALUES(?, ?, ?, ?)";
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
                System.out.println("Oops: " + e);
            }
            employee.setId(newId);
        } else {
            employee = null;
        }
        return employee;
    }

    public Employee delete(int id) throws SQLException {
        // Get the employee we're deleting before we delete them
        Employee deletedEmployee = null;
        deletedEmployee = this.getOne(id);

        String SQL = "DELETE FROM Emloyee WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the employee we're deleting if we didn't delete them
            deletedEmployee = null;
        }
        return deletedEmployee;
    }

    public Employee update(int id, Employee employee) throws SQLException {
        String SQL = "UPDATE Employees " +
                "SET name = ? ," +
                "jobname = ? ," +
                "salarygrade = ? ," +
                "department = ? " +
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
            updatedEmployee = this.getOne(id);
        }
        return updatedEmployee;
    }
}
