package com.booleanuk.api.Repositories;

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
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser +"&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement(
                "SELECT Employees.id, Employees.name, Employees.jobName, Salaries.grade AS salaryGrade, Departments.name AS department " +
                        "FROM Employees " +
                        "JOIN Salaries ON Employees.salary_id = Salaries.id " +
                        "JOIN Departments ON Employees.department_id = Departments.id"
        );

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Employee theEmployee = new Employee(
                    results.getLong("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department")
            );
            everyone.add(theEmployee);
        }
        return everyone;
    }

    public Employee get(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement(
                "SELECT Employees.id, Employees.name, Employees.jobName, Salaries.grade AS salaryGrade, Departments.name AS department " +
                        "FROM Employees " +
                        "JOIN Salaries ON Employees.salary_id = Salaries.id " +
                        "JOIN Departments ON Employees.department_id = Departments.id " +
                        "WHERE Employees.id = ?"
        );
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Employee employee = null;
        if (results.next()) {
            employee = new Employee(
                    results.getLong("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department")
            );
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with ID " + id + " not found.");
        }
        return employee;
    }

    public Employee update(long id, Employee employee) throws SQLException {
        Employee existingEmployee = this.get(id);
        if (existingEmployee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with ID " + id + " not found.");
        }

        try {
            String SQL = "UPDATE Employees " +
                    "SET name = ? ," +
                    "jobName = ? ," +
                    "salary_id = ? ," +
                    "department_id = ? " +
                    "WHERE id = ? ";
            PreparedStatement statement = this.connection.prepareStatement(SQL);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getJobName());
            statement.setInt(3, Integer.parseInt(employee.getSalaryGrade()));
            statement.setInt(4, Integer.parseInt(employee.getDepartment()));
            statement.setLong(5, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return this.get(id);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to update employee with ID " + id);
            }
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid salary grade or department.");
        }
    }

    public Employee delete(long id) throws SQLException {
        Employee employeeToDelete = this.get(id);
        if (employeeToDelete == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with ID " + id + " not found.");
        }

        String SQL = "DELETE FROM Employees WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Employee deletedEmployee = null;
        deletedEmployee = this.get(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedEmployee = null;
        }
        return deletedEmployee;
    }

    public Employee add(Employee employee) {
        try {
            String SQL = "INSERT INTO Employees(name, jobName, salary_id, department_id) VALUES(?, ?, ?, ?)";
            PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getJobName());
            statement.setInt(3, Integer.parseInt(employee.getSalaryGrade()));
            statement.setInt(4, Integer.parseInt(employee.getDepartment()));
            int rowsAffected = statement.executeUpdate();
            long newId = 0;
            if (rowsAffected > 0) {
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        newId = rs.getLong(1);
                    }
                } catch (Exception e) {
                    System.out.println("Oops: " + e);
                }
                employee.setId(newId);
            } else {
                employee = null;
            }
            return employee;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23503")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid salary_id. Salary grade does not exist.");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
            }
        }
    }
}
