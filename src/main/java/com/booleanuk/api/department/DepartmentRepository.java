package com.booleanuk.api.department;

import com.booleanuk.api.department.Department;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DepartmentRepository {
    DataSource dataSource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;
    Connection connection;
    DataSource datasource;

    public DepartmentRepository() throws SQLException {
        getDataBaseCredentials();
        this.datasource = this.createDataSource();
        this.connection = this.datasource.getConnection();
    }

    private void getDataBaseCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            dbUser = prop.getProperty("db.user");
            dbURL = prop.getProperty("db.url");
            dbPassword = prop.getProperty("db.password");
            dbDatabase = prop.getProperty("db.database");
        } catch(Exception e) {
            System.out.println("Error: " + e);
        }
    }

    private DataSource createDataSource() {

        final String url = "jdbc:postgresql://" + dbURL + ":5432/" + dbDatabase + "?user=" + dbUser +"&password=" + dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    public List<Department> getAll() throws SQLException {
        List<Department> allDepartments = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Departments");
        ResultSet results = statement.executeQuery();

        while(results.next()) {
            Department department = new Department(results.getInt("id"), results.getString("name"), results.getString("location"));
            allDepartments.add(department);
        }
        return allDepartments;
    }

    public Department get(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Departments WHERE id = ?");
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Department department = null;
        if (results.next()) {
            department =new Department(results.getInt("id"), results.getString("name"), results.getString("location"));
        }
        return department;
    }

    public Department update(long id, Department department) throws SQLException {
        String SQL = "UPDATE Departments " +
                "SET name = ? ," +
                "location = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        statement.setLong(3, id);
        int rowsAffected = statement.executeUpdate();
        Department updatedDepartment = null;
        if (rowsAffected > 0) {
            updatedDepartment = this.get(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the department, please check all required fields are correct.");
        }
        return updatedDepartment;
    }

    public Department delete(long id) throws SQLException {
        String SQL = "DELETE FROM Departments WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);

        Department deletedDepartment = null;
        deletedDepartment = this.get(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedDepartment = null;
        }
        return deletedDepartment;
    }

    public Department add(Department department) throws SQLException {
        String SQL = "INSERT INTO Departments(name, location) VALUES(?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        int rowsAffected = statement.executeUpdate();
        long newId = 0;
        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
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
}
