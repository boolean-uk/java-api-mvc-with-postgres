package com.booleanuk.api.Repositories;

import com.booleanuk.api.Models.Department;
import com.booleanuk.api.Models.Employee;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Repository
public class DepartmentRepository {
    private DataSource datasource;
    private Connection connection;

    public DepartmentRepository() throws SQLException {
        this.datasource = this.createDataSource();
        this.connection = this.datasource.getConnection();
    }

    private DataSource createDataSource() throws SQLException {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String dbURL = properties.getProperty("db.url");
        String dbUser = properties.getProperty("db.user");
        String dbPassword = properties.getProperty("db.password");
        String dbDatabase = properties.getProperty("db.database");

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://" + dbURL + ":5432/" + dbDatabase);
        dataSource.setUser(dbUser);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Departments")) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Department department = new Department(id, name);
                departments.add(department);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve departments.", e);
        }
        return departments;
    }

    public Department getDepartmentById(long id) {
        try {
            String sql = "SELECT * FROM Departments WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String name = resultSet.getString("name");
                        return new Department(id, name);
                    } else {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department with ID " + id + " not found.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve department with ID " + id, e);
        }
    }

    public Department updateDepartment(long id, Department department) throws SQLException {
        String SQL = "UPDATE Departments SET name = ? WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, department.getName());
        statement.setLong(2, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return getDepartmentById(id);
        } else {
            return null; // or throw an exception if necessary
        }
    }

    public Department deleteDepartment(long id) throws SQLException {
        String SQL = "DELETE FROM Departments WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            Department deletedDepartment = null;
            deletedDepartment = this.getDepartmentById(id);

            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                deletedDepartment = null;
            }
            return deletedDepartment;
        }
    }

    public Department createDepartment(Department department) {
        try {
            String SQL = "INSERT INTO Departments(name) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, department.getName());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create department.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                department.setId(id);
                return department;
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve generated key for the created department.");
            }
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create department.", e);
        }
    }
}
