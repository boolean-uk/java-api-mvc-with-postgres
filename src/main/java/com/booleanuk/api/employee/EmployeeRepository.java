package com.booleanuk.api.employee;

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

    public EmployeeRepository() throws SQLException  {
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
            System.out.println("Oops: " + e);
        }
    }

    private DataSource createDataSource() {
        // The url specifies the address of our database along with username and password credentials
        // you should replace these with your own username and password
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser +"&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    public List<Employee> getAll() throws SQLException  {
        List<Employee> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Employee theEmployee = new Employee(results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobname"),
                    results.getInt("salary_id"),
                    results.getInt("department_id"));
            everyone.add(theEmployee);
        }
        return everyone;
    }

    public Employee get(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees WHERE id = ?");
        // Choose set**** matching the datatype of the missing element
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Employee employee = null;
        if (results.next()) {
            employee = new Employee(results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobname"),
                    results.getInt("salary_id"),
                    results.getInt("department_id"));
        }
        return employee;
    }

    public Employee update(int id, Employee employee) throws SQLException {
        String SQL = "UPDATE Employees " +
                "SET name = ? ," +
                "jobname = ? ," +
                "salary_id = ? ," +
                "department_id = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setInt(3, employee.getSalary_id());
        statement.setInt(4, employee.getDepartment_id());
        statement.setInt(5, id);
        int rowsAffected = statement.executeUpdate();
        Employee updatedemployee = null;
        if (rowsAffected > 0) {
            updatedemployee = this.get(id);
        }
        return updatedemployee;
    }

    public Employee delete(int id) throws SQLException {
        String SQL = "DELETE FROM Employees WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        // Get the customer we're deleting before we delete them
        Employee deletedEmployee = null;
        deletedEmployee = this.get(id);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the customer we're deleting if we didn't delete them
            deletedEmployee = null;
        }
        return deletedEmployee;
    }

    public Employee add(Employee employee) throws SQLException {
        String SQL = "INSERT INTO Employees(name, jobname, salary_id, department_id) VALUES(?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setInt(3, employee.getSalary_id());
        statement.setInt(4, employee.getDepartment_id());
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
}
