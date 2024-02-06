package com.booleanuk.api.requests;

import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeRepository {
    DataSource datasource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;
    Connection connection;

    public EmployeeRepository() throws SQLException {
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
        } catch(Exception e) {
            System.out.println("Unable to read config.properties file: " + e);
        }
    }

    private DataSource createDataSource() {
        // The url specifies the address of our database along with username and password credentials
        // you should replace these with your own username and password
        final String url =
                "jdbc:postgresql://" + this.dbURL
                        + ":5432/" + this.dbDatabase
                        + "?user=" + this.dbUser
                        +"&password=" + this.dbPassword;

        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    public Employee createEmployee(Employee employee) throws SQLException {
        String insertSQL = "INSERT INTO Employees(name, jobName, salaryGrade, department) VALUES(?, ?, ?, ?)";

        PreparedStatement statement = this.connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());

        int rowsAffected = statement.executeUpdate();

        long newId = 0;

        if (rowsAffected > 0) {

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getLong(1);  //Ligger i column 1 i databasen. Hämtas eftersom det skapas automatiskt med SERIAL
                }
            } catch (Exception e) {
                System.out.println("Newly created employee's SERIAL id could not be retrieved from database: " + e);
            }

            employee.setId(newId);

        }
        else {
            employee = null;
        }

        return employee;
    }

    public List<Employee> getEmployees() throws SQLException  {
        List<Employee> allEmployees = new ArrayList<>();

        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Employee employee = new Employee(
                    results.getLong("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department"));
            allEmployees.add(employee);
        }

        return allEmployees;
    }

    public Employee getSpecificEmployee(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees WHERE id = ?");

        // Choose set**** matching the datatype of the missing element
        statement.setLong(1, id);

        ResultSet results = statement.executeQuery();

        Employee employee = null;

        if (results.next()) {
            employee = new Employee(
                    results.getLong("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department"));
        }

        return employee;
    }

    public Employee updateEmployee(long id, Employee employee) throws SQLException {
        String SQL = "UPDATE Employees " +
                "SET name = ? ," +
                "jobName = ? ," +
                "salaryGrade = ? ," +
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
            updatedEmployee = this.getSpecificEmployee(id);
        }

        return updatedEmployee;
    }

    public Employee deleteEmployee(long id) throws SQLException {
        String SQL = "DELETE FROM Employees WHERE id = ?";

        PreparedStatement statement = this.connection.prepareStatement(SQL);

        // Get the employee before we delete them
        Employee deletedEmployee = null;

        deletedEmployee = this.getSpecificEmployee(id);

        statement.setLong(1, id);

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected == 0) {
            //Reset the employee we're deleting if we didn't delete them
            deletedEmployee = null;
        }

        return deletedEmployee;
    }
}
