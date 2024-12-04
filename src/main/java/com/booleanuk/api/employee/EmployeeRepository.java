package com.booleanuk.api.employee;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeRepository {
    DataSource dataSource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;
    Connection connection;

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
            System.out.println("Database Credentials Failed To Load" + e);
        }
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource1 = new PGSimpleDataSource();
        dataSource1.setURL(url);
        return dataSource1;
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> employeeList = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees");
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            Employee employee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department")
            );
            employeeList.add(employee);
        }
        return employeeList;
    }

    public Employee add(Employee employee) throws SQLException {
        String SQL = "INSERT INTO employees (name,jobName,salaryGrade,department) VALUES (?,?,?,?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,employee.getName());
        statement.setString(2,employee.getJobName());
        statement.setString(3,employee.getSalaryGrade());
        statement.setString(4,employee.getDepartment());
        int rowsAffected = statement.executeUpdate();
        int newId = -1;
        if (rowsAffected > 0) {
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    newId = resultSet.getInt(1);
                }
            }catch (Exception e) {
                System.out.println("Error during Id assignment" + e);
            }
            employee.setId(newId);
        }else {
            employee = null;
        }
        return employee;
    }

    public Employee getOne(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
        statement.setInt(1,id);
        ResultSet result = statement.executeQuery();
        Employee employee = null;
        if (result.next()) {
            employee = new Employee(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("jobName"),
                    result.getString("salaryGrade"),
                    result.getString("department")
            );
        }
        return employee;
    }

    public Employee update(int id, Employee employee) throws SQLException {
        String SQL = "UPDATE employees " +
                "SET name = ?," +
                "jobName = ?," +
                "salaryGrade = ?," +
                "department = ?" +
                "WHERE id = ?;";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1,employee.getName());
        statement.setString(2,employee.getJobName());
        statement.setString(3,employee.getSalaryGrade());
        statement.setString(4,employee.getDepartment());
        statement.setInt(5,id);
        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if (rowsAffected > 0) {
            updatedEmployee = this.getOne(id);
        }
        return updatedEmployee;
    }

    public Employee delete(int id) throws SQLException {
        String SQL = "DELETE FROM employees WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Employee deletedEmployee = null;
        deletedEmployee = this.getOne(id);
        statement.setInt(1,id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0 ) {
            deletedEmployee = null;
        }
        return deletedEmployee;
    }
}
