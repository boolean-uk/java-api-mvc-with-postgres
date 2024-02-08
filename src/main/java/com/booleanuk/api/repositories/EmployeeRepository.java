package com.booleanuk.api.repositories;


import com.booleanuk.api.models.Employee;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeRepository {
    private DataSource dataSource;
    private String dbUser;
    private String dbUrl;
    private String dbPassword;
    private String dbDatabase;
    private Connection connection;


    public EmployeeRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees");
        ResultSet results = statement.executeQuery();
        System.out.println(results);

        while (results.next()){
            Employee theEmployee = new Employee(results.getInt("id"),
                    results.getString("name"),results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department"));
            everyone.add(theEmployee);
        }
        return everyone;
    }


    public Employee get(int id) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees WHERE id= ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Employee employee = null;
        if (results.next()){
            employee = new Employee(results.getInt("id"),
                    results.getString("name"),results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department"));
        }
        return employee;
    }

    public Employee update(int id, Employee employee) throws SQLException{
        String SQL = "UPDATE employees " +
                "SET name = ?, "+
                "jobName = ?, "+
                "salaryGrade = ?, "+
                "department = ? " +
                "WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
        statement.setInt(5, id);

        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if (rowsAffected > 0){
            updatedEmployee = this.get(id);
        }
        return updatedEmployee;
    }

    public Employee delete(int id) throws SQLException{
        String SQL = "DELETE FROM employees WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Employee deletedEmployee =  this.get(id);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0){
            deletedEmployee = null;
        }
        return deletedEmployee;
    }

    public Employee add(Employee employee) throws SQLException{
        String SQL = "INSERT INTO employees (name,jobName,salaryGrade,department) VALUES (?,?,?,?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
        int rowsAffected = statement.executeUpdate();
        int newId = -1;
        if (rowsAffected > 0){
            try (ResultSet resultSet = statement.getGeneratedKeys()){
                if (resultSet.next()){
                    newId = resultSet.getInt(1);
                }
            }
            catch (Exception e){
                System.out.println("Can't add employee: " +e);
            }
            employee.setId(newId);
        }else {
            employee = null;
        }
        return employee;
    }

    private void getDatabaseCredentials(){
        try(InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            this.dbUser = properties.getProperty("db.user");
            this.dbUrl = properties.getProperty("db.url");
            this.dbPassword = properties.getProperty("db.password");
            this.dbDatabase = properties.getProperty("db.database");

        } catch (Exception e) {
            System.out.println("Oops: " + e);
        }
    }

    private DataSource createDataSource(){
        String url = "jdbc:postgresql://" +this.dbUrl+ ":5432/" +this.dbDatabase+
                "?user=" +this.dbUser+ "&password=" +this.dbPassword;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        return dataSource;
    }
}
