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
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;
    Connection connection;
    DataSource datasource;

    public EmployeeRepository() throws SQLException {
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

    public List<Employee> getAll() throws SQLException {
        List<Employee> allEmployees = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT Employees.id, Employees.name, Employees.jobName, Salaries.grade AS salaryGrade, Departments.name as department  \n" +
                "FROM Employees \n" +
                "INNER JOIN departments\n" +
                "on Employees.department_id=departments.id\n" +
                "INNER JOIN salaries\n" +
                "on Employees.salary_id=salaries.id");

        ResultSet results = statement.executeQuery();

        while(results.next()) {
            Employee employee = new Employee(results.getLong("id"), results.getString("name"), results.getString("jobName"), results.getString("salaryGrade"), results.getString("department"));
            allEmployees.add(employee);
        }
        return allEmployees;
    }

    public Employee get(long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT Employees.id, Employees.name, Employees.jobName, Salaries.grade AS salaryGrade, Departments.name as department  \n" +
                "FROM Employees \n" +
                "INNER JOIN departments\n" +
                "on Employees.department_id=departments.id\n" +
                "INNER JOIN salaries\n" +
                "on Employees.salary_id=salaries.id" +
                " WHERE Employees.id = ?");
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Employee employee = null;

        if (results.next()) {
            employee = new Employee(results.getLong("id"), results.getString("name"), results.getString("jobName"), results.getString("salaryGrade"), results.getString("department"));
        }
        return employee;
    }

    public Employee update(long id, Employee employee) throws SQLException {
        String SQL = "UPDATE Employees\n" +
                "SET \n" +
                "    name = ?,\n" +
                "    jobName = ?,\n" +
                "    salary_id = (SELECT id FROM Salaries WHERE grade=?),\n" +
                "    department_id = (SELECT id FROM Departments WHERE name=?)\n" +
                "WHERE\n" +
                "    id = ?; ";

        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
        statement.setLong(5, id);
        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if (rowsAffected > 0) {
            updatedEmployee = this.get(id);
        }
        return updatedEmployee;
    }

    public Employee delete(long id) throws SQLException {
        String SQL = "DELETE FROM Employees WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);

        Employee deletedEmployee = this.get(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedEmployee = null;
        }
        return deletedEmployee;
    }

    public Employee add(Employee employee) throws SQLException {
        String SQL = "INSERT INTO Employees(name, jobName, salary_id, department_id) VALUES(?, ?, (SELECT id FROM Salaries WHERE grade=?), (SELECT id FROM departments WHERE name=?))";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
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
            employee.setId(newId);
        } else {
            employee = null;
        }
        return employee;
    }
}
