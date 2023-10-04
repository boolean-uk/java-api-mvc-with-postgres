package com.booleanuk.api.Employees;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeRepository {
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String dbDatabase;
    private Connection connection;

    public EmployeeRepository() {
        this.getDatabaseCredentials();
        DataSource dataSource = createDataSource();
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("Repository Creation: " + e);
        }
    }

    private void getDatabaseCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")){
            Properties properties = new Properties();
            properties.load(input);
            this.dbUrl = properties.getProperty("db.url");
            this.dbUser = properties.getProperty("db.user");
            this.dbPassword = properties.getProperty("db.password");
            this.dbDatabase = properties.getProperty("db.database");
        } catch (Exception e) {
            System.out.println("Database Credentials: " + e);
        }
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbUrl + ":5432/" + this.dbDatabase + "?user=" + this.dbUser +"&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    public List<Employee> getAll() throws SQLException{
        List<Employee> employees = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            employees.add(new Employee(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("jobName"),
                    resultSet.getString("salaryGrade"),
                    resultSet.getString("department")
            ));
        }
        return employees;
    }

    public Employee create(Employee employee) throws SQLException{
        String SQL = "INSERT INTO employees (name, jobName, salaryGrade, department) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Could not create employee, please check all required fields are correct.");
        }
        int newId = 0;
        try (ResultSet resultSet = statement.getGeneratedKeys()) {
            if (resultSet.next()) {
                newId = resultSet.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("Employee Creation: " + e);
        }
        employee.setId(newId);
        return employee;
    }

    public Employee get(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Employee employee = null;
        if (resultSet.next()) {
            employee = new Employee(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("jobName"),
                    resultSet.getString("salaryGrade"),
                    resultSet.getString("department")
            );
        }
        return employee;
    }

    public Employee update(int id, Employee employee) throws SQLException {
        String SQL = "UPDATE employees SET name = ?, jobName = ?, salaryGrade = ?, department = ? WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
        statement.setInt(5, id);
        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Could not update the employee, please check all required fields are correct.");
        }
        return this.get(id);
    }

    public Employee delete(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("DELETE FROM employees WHERE id = ?");
        statement.setInt(1, id);
        Employee theEmployee = this.get(id);
        if (theEmployee == null) {
            return null;
        }
        statement.executeUpdate();
        return theEmployee;
    }
}
