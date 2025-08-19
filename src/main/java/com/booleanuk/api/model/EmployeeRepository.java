package com.booleanuk.api.model;

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
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");

        } catch (Exception e) {
            System.out.println("Oops:  " + e);
        }
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Employee theEmployee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobname"),
                    results.getInt("salary_id"),
                    results.getInt("department_id"));
            everyone.add(theEmployee);
        }
        return everyone;
    }

    public Employee getOne(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees WHERE id = ?");
        // Choose set**** matching the datatype of the missing element
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Employee employee = null;
        if (results.next()) {
             employee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobname"),
                     results.getInt("salary_id"),
                     results.getInt("department_id"));
        }
        return employee;
    }

    public Employee add(Employee employee) throws SQLException {
        if (!employee.getName().isBlank() && employee.getSalary_id() > 0 && employee.getDepartment_id() > 0 && !employee.getJobName().isBlank()) {
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
        } return null;
    }

    public Employee update(int id, Employee employee) throws SQLException {
        if (!employee.getName().isBlank() && employee.getSalary_id() > 0 && employee.getDepartment_id() > 0 && !employee.getJobName().isBlank()) {
            String SQL = "UPDATE Employees " +
                    "SET name = ? ," +
                    "jobName = ? ," +
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
            Employee updatedEmployee = null;
            if (rowsAffected > 0) {
                updatedEmployee = this.getOne(id);
            }
            return updatedEmployee;
        } return null;
    }

    public Employee delete(int id) throws SQLException {

        Employee deletedEmployee = this.getOne(id);

        String SQL = "DELETE FROM Employees WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setInt(1, id);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedEmployee = null;
        }
        return deletedEmployee;
    }

    public boolean checkName(Employee employee, int id) throws SQLException {
        List<Employee> everyone = this.getAll();
        for (Employee employeeInList : everyone) {
            if (employeeInList.getName().equals(employee.getName())){
                return true;
            }
        } return false;
    }
}
