package com.booleanuk.api;

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
        this.getDatabaseCredentials();
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();
    }

    private void getDatabaseCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")){
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");

        } catch (Exception e) {
            System.out.println("This is made by Dave "+ e);
        }
    }

    private DataSource createDataSource(){
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    public void connectToDatabase() throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            String id = "" + results.getInt("id");
            String name = results.getString("name");
            String jobName = results.getString("jobName");
            System.out.println(id + " - " + name + " - " + jobName);
        }
    }
    public List<Employee> getAll() throws SQLException  {
        List<Employee> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM EMPLOYEES");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Employee theEmployee = new Employee(results.getInt("id"), results.getString("name"), results.getString("jobName"), results.getInt("salaryGrade_id"), results.getInt("department_id"));
            everyone.add(theEmployee);
        }
        return everyone;
    }

    public Employee get(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees WHERE id = ?");
        // Choose set**** matching the datatype of the missing element
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Employee customer = null;
        if (results.next()) {
            customer = new Employee(results.getInt("id"), results.getString("name"), results.getString("jobName"), results.getInt("salaryGrade_id"), results.getInt("department_id"));
        }
        return customer;
    }

    public Employee update(int id, Employee customer) throws SQLException {
        String SQL = "UPDATE Employees " +
                "SET name = ? ," +
                "jobName = ? ," +
                "salaryGrade_id = ? ," +
                "department_id = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, customer.getName());
        statement.setString(2, customer.getJobName());
        statement.setInt(3, customer.getSalaryGrade_id());
        statement.setInt(4, customer.getDepartment_id());
        statement.setInt(5, id);
        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if (rowsAffected > 0) {
            updatedEmployee = this.get(id);
        }
        return updatedEmployee;
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

    public Employee add(Employee customer) throws SQLException {
        String SQL = "INSERT INTO Employees(name, jobName, salaryGrade_id, department_id) VALUES(?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, customer.getName());
        statement.setString(2, customer.getJobName());
        statement.setInt(3, customer.getSalaryGrade_id());
        statement.setInt(4, customer.getDepartment_id());
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
            customer.setId(newId);
        } else {
            customer = null;
        }
        return customer;
    }

}

