package com.booleanuk.api.employees;

import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
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
            this.dbDatabase = prop.getProperty("db.database");
            this.dbPassword = prop.getProperty("db.password");
            this.dbURL = prop.getProperty("db.url");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        return dataSource;
    }

    public void connectToDatabase() throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees;");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            String id = "" + results.getLong("id");
            String name = results.getString("name");
            String jobName = results.getString("jobName");
            String salaryGrade = results.getString("salaryGrade");
            String department = results.getString("department");
            System.out.printf("%s - %s - %s - %s - %s%n", id, name, jobName, salaryGrade, department);
        }
    }

    public Employee get(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Employee employee = null;
        if (results.next()) {
            employee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department")
            );
        }
        return employee;
    }

    public ArrayList<Employee> getAll() throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees");
        ResultSet results = statement.executeQuery();
        ArrayList<Employee> retList = new ArrayList<>();

        while (results.next()) {
            retList.add(new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department")
            ));
        }

        return retList;
    }

    public Employee delete(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("DELETE FROM employees WHERE id = ?");

        Employee employee = this.get(id);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected == 0) {
            employee = null;
        }

        return employee;
    }

    public Employee update(int id, Employee employee) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement(
                "UPDATE employees " +
                        "SET name = ?, " +
                        "jobName = ?, " +
                        "salaryGrade = ?, " +
                        "department = ? " +
                        "WHERE id = ?;"
        );
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
        statement.setInt(5, id);

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected == 0) {
            employee = null;
        } else {
            employee.setId(id);
        }

        return employee;
    }

    public Employee post(Employee employee) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO employees " +
                        "(name, jobName, salaryGrade, department) " +
                        "VALUES " +
                        "(?, ?, ?, ?);",
                PreparedStatement.RETURN_GENERATED_KEYS
        );
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());

        int rowsAffected = statement.executeUpdate();
        int newId = 0;
        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            employee.setId(newId);
        } else {
            employee = null;
        }

        return employee;
    }
}
