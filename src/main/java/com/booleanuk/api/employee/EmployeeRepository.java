package com.booleanuk.api.employee;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.booleanuk.api.employee.Employee;
import org.postgresql.ds.PGSimpleDataSource;

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
            // Properties used to read value from properties file. Build in java method
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

    public List<Employee> getAll() throws SQLException {
        List<Employee> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("select * from employees");

        ResultSet results = statement.executeQuery();

        while (results.next()){
            Employee theEmployee = new Employee(results.getLong("id"),
                    results.getString("name"),
                    results.getString("job_name"),
                    results.getInt("salary_id"), results.getInt("department_id"));
            everyone.add(theEmployee);
        }
        return everyone;
    }

    //the sql statement retrieves all columns from the employees table where the 'id' column matches a parameter (id)
    public Employee get(long id) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("select * from employees where id = ?");
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Employee employee = null;
        if(results.next()){
            employee = new Employee(results.getLong("id"),
                    results.getString("name"),
                    results.getString("job_name"),
                    results.getInt("salary_id"),
                    results.getInt("department_id"));

        }
        return employee;

    }

    public Employee update(long id, Employee employee) throws SQLException{
        String SQL = "update employees "+
                "set name = ? ,"+
                "job_name = ? ," +
                "salary_id = ? ," +
                "department_id = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setInt(3, employee.getSalaryID());
        statement.setInt(4, employee.getDepartmentID());
        statement.setLong(5, id);
        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if(rowsAffected > 0){
            updatedEmployee = this.get(id);
        }
        return updatedEmployee;
    }
    public Employee delete(long id) throws SQLException {
        String SQL = "delete from employees where id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        // Get the customer we're deleting before we delete them
        Employee deletedEmployee = null;
        deletedEmployee = this.get(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the customer we're deleting if we didn't delete them
            deletedEmployee = null;
        }
        return deletedEmployee;
    }
    public Employee add(Employee employee) throws SQLException {
        String SQL = "insert into employees ( name, job_name, salary_id, department_id) values( ?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setInt(3, employee.getSalaryID());
        statement.setInt(4, employee.getDepartmentID());
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
    }
}
