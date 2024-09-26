package com.booleanuk.api.departments;

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

public class DepartmentRepository {
    private DataSource dataSource;
    private String dbUser;
    private String dbURL;
    private String dbPassword;
    private String dbDatabase;
    private Connection connection;

    public DepartmentRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();
    }

    public List<Department> getAll() throws SQLException {
        List<Department> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement(
                "SELECT * FROM Departments");
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Department theDepartment = new Department(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("location"));
            everyone.add(theDepartment);
        }
        return everyone;
    }

    public Department get(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement(
                "SELECT * FROM Departments WHERE id= ?");
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        Department department = null;
        if (rs.next()) {
            department = new Department(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("location"));
        }
        return department;
    }

    public Department update(int id, Department department) throws SQLException {
        String SQL = "UPDATE Departments " +
                "SET name = ?, " +
                "location = ? " +
                "WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        statement.setInt(3, id);
        int rowAffected = statement.executeUpdate();
        Department updatedDepartment = null;
        if (rowAffected > 0) {
            updatedDepartment = this.get(id);
        }
        return updatedDepartment;
    }

    public Department delete(int id) throws SQLException {
        String SQL = "DELETE FROM Departments WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Department deletedDepartment = this.get(id);

        statement.setInt(1, id);
        int rowAffected = statement.executeUpdate();
        if (rowAffected == 0) {
            deletedDepartment = null;
        }
        return deletedDepartment;
    }

    public Department add(Department department) throws SQLException {
        String SQL =
                "INSERT INTO Departments (name, location) "
                        + "VALUES (?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
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
            department.setId(newID);
        } else {
            department = null;
        }
        return department;
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
