package com.booleanuk.api.departments;

import com.booleanuk.api.database.DatabaseConnection;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DepartmentRepository {
    Connection connection;

    private static final Logger logger = Logger.getLogger(DepartmentRepository.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("src/main/java/com/booleanuk/api/departments/department.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            String exceptionMessage = "Failed to initialize logger handler" + e.getMessage();
            logger.severe(exceptionMessage);
        }
    }

    public DepartmentRepository() throws SQLException {
        this.connection = new DatabaseConnection().getConnection();
    }

    public List<Department> getAll() throws SQLException {
        List<Department> allDepartments = new ArrayList<>();

        logger.info("Attempting to get all departments.");

        try (PreparedStatement statement = this.connection.prepareStatement("SELECT id, name, location FROM departments")){
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                Department department = new Department(
                        results.getInt("id"),
                        results.getString("name"),
                        results.getString("location"));
                allDepartments.add(department);
            }
            logger.info("Departments successfully fetched.");
            return allDepartments;
        } catch (SQLException e) {
            String exceptionMessage = "An error occurred while getting all departments: " + e.getMessage();
            logger.warning(exceptionMessage);
            throw new SQLException(exceptionMessage, e);
        }
    }

    public Department get(int id) throws SQLException {

        try (PreparedStatement statement = this.connection.prepareStatement("SELECT id, name, location FROM departments WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            Department department;

            if (results.next()) {
                department = new Department(
                        results.getInt("id"),
                        results.getString("name"),
                        results.getString("location"));
            }

            else {
                logger.info("No departments with that id were found.");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments with that id were found.");
            }
            logger.info("Department successfully fetched by id.");
            return department;

        } catch (SQLException e) {
            String exceptionMessage = "An error occurred while getting a department by id: " + e.getMessage();
            logger.info(exceptionMessage);
            throw new SQLException(exceptionMessage, e);
        }
    }

    public Department add(Department department) throws SQLException {
        String sqlString = "INSERT INTO departments(name, location) VALUES (?, ?)";

        try (PreparedStatement statement = this.connection.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, department.getName());
            statement.setString(2, department.getLocation());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                String exceptionMessage = "Department creation failed, no rows affected";
                logger.warning(exceptionMessage);
                throw new SQLException(exceptionMessage);
            }

            int newID = getGeneratedKeys(statement);
            department.setId(newID);

            logger.info("Department successfully added");

            return department;

        } catch (SQLException e) {
            String exceptionMessage = "An error occurred while attempting to add a department to the database: " + e.getMessage();
            logger.severe(exceptionMessage);
            throw new SQLException(exceptionMessage, e);
        }
    }

    private int getGeneratedKeys(Statement statement) throws SQLException {
        try (ResultSet rs = statement.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                String exceptionMessage = "An error occurred while getting the generated key: ";
                logger.warning(exceptionMessage);
                throw new SQLException(exceptionMessage);
            }
        }
    }

    public Department update(int id, Department department) throws SQLException {
        String sqlString = "UPDATE departments " +
                "SET name = ? ," +
                "location = ? " +
                "WHERE id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sqlString)) {
            statement.setString(1, department.getName());
            statement.setString(2, department.getLocation());
            statement.setInt(3, id);

            String oldName = this.get(id).getName();
            statement.executeUpdate();

            Department updatedDepartment = this.get(id);

            logger.log(Level.INFO, "Department with name \"{0}\" successfully changed name to \"{1}\"", new String[]{oldName, updatedDepartment.getName()});

            return this.get(id);

        } catch (SQLException e) {
            String exceptionMessage = "An error occurred while attempting to update a department: " + e.getMessage();
            logger.warning(exceptionMessage);
            throw new SQLException(exceptionMessage, e);
        }
    }

    public Department delete(int id) throws SQLException {
        String sqlString = "DELETE FROM departments WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlString)) {
            statement.setInt(1, id);

            Department deletedDepartment = this.get(id);

            statement.executeUpdate();

            logger.log(Level.INFO, "Department with name: \"{0}\" deleted.", deletedDepartment.getName());
            return deletedDepartment;
        } catch (SQLException e) {
            String exceptionMessage = "An error occurred when attempting to delete a department: " + e;
            logger.warning(exceptionMessage);
            throw new SQLException(exceptionMessage, e);
        }
    }
}
