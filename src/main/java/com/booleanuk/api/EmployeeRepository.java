package com.booleanuk.api;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.postgresql.ds.PGSimpleDataSource;

public class EmployeeRepository {
    private DataSource dataSource;
    private String dbUser;
    private String dbURL;
    private String dbPassword;
    private String bdDatabase;
    private Connection connection;

    public EmployeeRepository() throws SQLException {
        getDatabaseCredentials();
        dataSource = createDataSource();
        connection = dataSource.getConnection();
    }

    public List<Employee> getAll() throws SQLException{
        List<Employee> employees = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM employees;");
        ResultSet results = statement.executeQuery();

        while (results.next()){
            Employee theEmployee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("job_name"),
                    results.getString("salary_grade"),
                    results.getString("department")
            );
            employees.add(theEmployee);
        }

        return employees;
    }

    public Employee getOne(int id) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM employees WHERE id = ?;");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Employee employee = null;

        if (results.next()){
            employee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("job_name"),
                    results.getString("salary_grade"),
                    results.getString("department")
            );
        }

        return employee;
    }

    public Employee update(int id, Employee employee) throws SQLException{
        String sql = "UPDATE employees " +
                "SET name = ?, " +
                "job_name = ?, " +
                "salary_grade = ?, " +
                "department = ? " +
                "WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
        statement.setInt(5, id);
        statement.executeUpdate();

        return getOne(id);
    }

    public Employee delete(int id) throws SQLException{
        String sql = "DELETE FROM employees WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        Employee deleted = getOne(id);

        statement.setInt(1, id);
        statement.executeUpdate();

        return deleted;
    }

    public Employee add(Employee employee) throws SQLException{
        String SQL = "INSERT INTO employees " +
                "(name, job_name, salary_grade, department) " +
                "VALUES (?, ?, ?, ?);";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

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
                System.out.println("Oops: " + e);
            }
            employee.setId(newId);
        } else {
            employee = null;
        }
        return employee;
    }

    public Employee findEmployeeFromName(String name) throws SQLException{
        for (Employee employee : getAll()){
            if (employee.getName().equals(name)){
                return employee;
            }
        }

        return null;
    }

    private void getDatabaseCredentials(){
        try {
            InputStream input = new FileInputStream("src/main/resources/config.properties");
            Properties prop = new Properties();

            prop.load(input);
            dbUser = prop.getProperty("db.user");
            dbURL = prop.getProperty("db.url");
            dbPassword = prop.getProperty("db.password");
            bdDatabase = prop.getProperty("db.database");

        } catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private DataSource createDataSource(){
        String url = "jdbc:postgresql://" + dbURL +
                ":5432/" + bdDatabase +
                "?user=" + dbUser +
                "&password=" + dbPassword;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);

        return dataSource;
    }
}
