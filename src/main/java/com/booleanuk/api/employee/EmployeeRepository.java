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

    private void getDatabaseCredentials(){
        try(InputStream input = new FileInputStream("src/main/resources/config.properties")){
            Properties prop = new Properties();
            prop.load(input);

            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");

        } catch(Exception e){
            System.out.println("Something went wrong: " + e);
        }
    }

    private DataSource createDataSource(){
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/"
                + this.dbDatabase + "?user=" + this.dbUser + "&password=" + this.dbPassword;

        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        return dataSource;
    }










    public List<Employee> getAll() throws SQLException{
        List<Employee> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees");
        ResultSet results = statement.executeQuery();

        while(results.next()){
            Employee theEmployee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getInt("salaryID"),
                    results.getInt("departmentID")
            );
            everyone.add(theEmployee);
        }
        return everyone;
    }

    public Employee get(int id) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Employee employee = null;

        if(results.next()){
            employee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getInt("salaryID"),
                    results.getInt("departmentID")
            );
        }
        return employee;
    }



    public Employee update(int id, Employee employee) throws SQLException {
        String SQL = "UPDATE employees " +
                    "SET name = ? , " +
                    "jobName = ? , " +
                    "salaryID = ? , " +
                    "departmentID = ? " +
                    "WHERE id = ? ";

        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setInt(3, employee.getSalaryID());
        statement.setInt(4, employee.getDepartmentID());
        statement.setLong(5, id);

        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;

        if (rowsAffected > 0) {
            updatedEmployee = this.get(id);
        }
        return updatedEmployee;
    }




    public Employee delete(int id) throws SQLException {
        String SQL = "DELETE FROM employees WHERE id = ?";
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

    public Employee add(Employee employee) throws SQLException {
        String SQL = "INSERT INTO employees(name, jobName, salaryID, departmentID) VALUES(?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setInt(3, employee.getSalaryID());
        statement.setInt(4, employee.getDepartmentID());
        int rowsAffected = statement.executeUpdate();
        int newId = 0;
        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("Something went wrong here: " + e);
            }
            employee.setId(newId);
        } else {
            employee = null;
        }
        return employee;
    }
}
