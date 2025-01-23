package com.booleanuk.api.department;

import com.booleanuk.api.employee.Employee;
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

    public Department create(Department department) throws SQLException {
        String sql = "INSERT INTO departments (name, location) VALUES (?,?)";
        PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,department.getName());
        statement.setString(2,department.getLocation());
        int rowsAffected = statement.executeUpdate();
        long newId = 0;

        if(rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if(rs.next()) {
                    newId = rs.getLong(1);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            department.setId(newId);
        } else {
            department = null;
        }
        return department;
    }

    public List<Department> getAll() throws SQLException {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM departments";
        PreparedStatement statement = this.connection.prepareStatement(sql);

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Department department = new Department(results.getLong("id"),
                    results.getString("name"),
                    results.getString("location"));

            departments.add(department);
        }
        return departments;
    }

    public Department getOne(long id) throws SQLException {
        String sql = "SELECT * FROM departments WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Department department = null;
        if (results.next()) {
            department = new Department(results.getLong("id"),
                    results.getString("name"),
                    results.getString("location"));
        }
        return department;
    }

    public Department update(long id, Department department) throws SQLException {
        String sql = "UPDATE departments SET name = ?, location = ? WHERE id = ?";

        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        statement.setLong(3, id);
        int rowsAffected = statement.executeUpdate();
        Department updatedDepartment = null;
        if (rowsAffected > 0) {
            updatedDepartment = this.getOne(id);
        }
        return updatedDepartment;
    }

    public Department delete(long id) throws SQLException {
        String sql = "DELETE FROM departments WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(sql);
        Department deleteDepartment = null;
        deleteDepartment = this.getOne(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();

        if(rowsAffected == 0) {
            deleteDepartment = null;
        }
        return deleteDepartment;
    }
}
