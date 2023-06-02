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


    public List<Employee> getAll() throws SQLException {
        List<Employee> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Employee currentEmployee = new Employee(results.getLong("id"), results.getString("name"), results.getString("jobName"), results.getString("salaryGrade"), results.getString("department"));
            everyone.add(currentEmployee);
        }

        return everyone;
    }


    public Employee get(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees WHERE id = ?");
        statement.setLong(1,id);
        ResultSet results = statement.executeQuery();

        Employee employee = null;
        if (results.next()) {
            employee = new Employee(results.getLong("id"), results.getString("name"), results.getString("jobName"), results.getString("salaryGrade"), results.getString("department"));
        }

        return employee;
    }

    public Employee delete(int id) throws SQLException {
        String SQL = "DELETE FROM Employees WHERE id = ?";
        PreparedStatement ps = this.connection.prepareStatement(SQL);

        Employee deletedEmployee = null;
        deletedEmployee = this.get(id);

        ps.setInt(1, id);
        int rowsAffected = ps.executeUpdate();

        if (rowsAffected == 0) {
            //Reset the customer we're deleting if we didn't delete them
            deletedEmployee = null;
        }
        return deletedEmployee;
    }

    public Employee add(Employee employee) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("INSERT INTO Employees (name,jobName,salaryGrade,department VALUES (?,?,?,?)");
        ps.setString(1, employee.getName());
        ps.setString(2, employee.getJobName());
        ps.setString(3, employee.getSalarygrade());
        ps.setString(4, employee.getDepartment());

        int rowsAffected = ps.executeUpdate();

        int newId = 0;
        if (rowsAffected > 0) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
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
