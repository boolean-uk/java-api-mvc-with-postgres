package com.booleanuk.api.department;

import com.booleanuk.api.salary.Salary;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DepartmentRepository {
    DataSource datasource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;
    Connection connection;

    public DepartmentRepository() throws SQLException {
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


    public List<Department> getAll() throws SQLException {
        List<Department> allDepartments = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Departments");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Department currentDepartment = new Department(results.getString("name"), results.getString("location"));
            allDepartments.add(currentDepartment);
        }

        return allDepartments;
    }


    public Department get(String name) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Departments WHERE name = ?");
        statement.setString(1, name);
        ResultSet results = statement.executeQuery();

        Department department = null;
        if (results.next()) {
            department = new Department(results.getString("name"), results.getString("location"));
        }

        return department;
    }

    public Department update(String name, Department department) throws SQLException {
        String SQL = "UPDATE Departments " +
                "SET name = ?, " +
                "location = ? " +
                "WHERE name = ?";

        PreparedStatement ps = this.connection.prepareStatement(SQL);
        ps.setString(1,department.getName());
        ps.setString(2,department.getName());
        int rowsAffected = ps.executeUpdate();

        Department updatedDepartment = null;
        if (rowsAffected > 0) {
            updatedDepartment = this.get(name);
        }
        return updatedDepartment;
    }

    public Department delete(String name) throws SQLException {
        String SQL = "DELETE FROM Departments WHERE name = ?";
        PreparedStatement ps = this.connection.prepareStatement(SQL);

        Department deletedDepartment = null;
        deletedDepartment = this.get(name);

        ps.setString(1, name);
        int rowsAffected = ps.executeUpdate();

        if (rowsAffected == 0) {
            deletedDepartment = null;
        }
        return deletedDepartment;
    }

    public Department add(Department department) throws SQLException {
        String SQL = "INSERT INTO Departments (name, location) VALUES (?,?)";
        PreparedStatement ps = this.connection.prepareStatement(SQL);
        ps.setString(1, department.getName());
        ps.setString(2, department.getLocation());

        Department newDepartment = null;
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            newDepartment = this.get(department.getName());
        }

        return newDepartment;
    }
}
