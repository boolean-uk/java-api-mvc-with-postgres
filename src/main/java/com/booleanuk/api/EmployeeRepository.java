package com.booleanuk.api;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.postgresql.ds.PGSimpleDataSource;


public class EmployeeRepository {
    DataSource datasource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;
    Connection connection;

    public EmployeeRepository() throws SQLException{
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
        // The url specifies the address of our database along with username and password credentials
        // you should replace these with your own username and password
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser +"&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    public List<Employee> getAll() throws SQLException{
        List<Employee> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees");

        ResultSet results = statement.executeQuery();

        while (results.next()){
            Employee theEmployee = new Employee(results.getInt("id"), results.getString("name"), results.getString("jobName"), results.getString("salaryGrade"), results.getString("department"));
            everyone.add(theEmployee);
        }
        return everyone;
    }

    public Employee getOne(int id) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees WHERE id = ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Employee employee = null;
        if(results.next()){
            employee = new Employee(results.getInt("id"), results.getString("name"),results.getString("jobName"), results.getString("salaryGrade"), results.getString("department"));
        }
        return employee;
    }

    public Employee delete(int id) throws SQLException{
        String SQL_code = "DELETE FROM employees WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL_code);
        Employee deletedEmployee = null;
        deletedEmployee = this.getOne(id);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if(rowsAffected == 0){
            deletedEmployee = null;
        }
        return deletedEmployee;
    }

    public Employee add(Employee employee) throws SQLException{
        String SQL_code = "INSERT INTO Employees(name, jobName, salaryGrade, department) VALUES(?,?,?,?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL_code, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
        int rowsAffected = statement.executeUpdate();
        int newId = 0;
        if(rowsAffected > 0){
            try(ResultSet rs = statement.getGeneratedKeys()){
                if(rs.next()) {
                    newId = rs.getInt(1);
                }
            } catch(Exception e){
                System.out.println("Oops: " + e);
            }
            employee.setId(newId);
        } else{
            employee = null;
        }
        return employee;
    }

    public Employee update(int id, Employee employee) throws SQLException{
        String SQL_code = "UPDATE Employees " +
                "SET name = ? ," +
                "jobName = ? ," +
                "salaryGrade = ? ," +
                "department = ? " +
                "WHERE id = ? ";

        PreparedStatement statement = this.connection.prepareStatement(SQL_code);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
        statement.setInt(5, id);

        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if(rowsAffected > 0){
            updatedEmployee = this.getOne(id);
        }
        System.out.println(rowsAffected);
        return updatedEmployee;
    }
}
