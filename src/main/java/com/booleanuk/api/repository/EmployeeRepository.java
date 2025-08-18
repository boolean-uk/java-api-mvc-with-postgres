package com.booleanuk.api.repository;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import com.booleanuk.api.model.Employee;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.*;
public class EmployeeRepository {



    public DataSource dataSource;
    private Connection connection;

    private String dbUser;
    private String dbUrl;
    private String dbPassword;
    private String dbDatabase;

    public EmployeeRepository() throws SQLException {
        getDatabaseCredentials();
        this.dataSource = createDataSource();
        this.connection = this.dataSource.getConnection();
    }

    private void getDatabaseCredentials(){
        try(InputStream input = new FileInputStream("src/main/resources/config.properties")){
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbUrl = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");

        }catch (Exception e){

            System.err.println("Oops:  " + e);
        }
    }


    private DataSource createDataSource(){
        final String url = "jdbc:postgresql://" + this.dbUrl + ":5432/" + this.dbDatabase + "?user=" + this.dbUser +"&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }


    public void connectToDatabase()throws SQLException{


        PreparedStatement statements = this.connection.prepareStatement("SELECT * FROM Employees");

        ResultSet results = statements.executeQuery();

        while (results.next()){


            String jobName = "" + results.getString("jobName");
            String salaryGrade = "" + results.getString("salaryGrade");
            String department = "" + results.getString("department");
        }
    }

    public List<Employee> getAll() throws SQLException  {
        List<Employee> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Employee employee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department")
            );

            everyone.add(employee);
        }
        return everyone;
    }


    public Employee getEmployee(int id)throws SQLException {

        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM EMPLOYEES WHERE ID = ?");
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next() ? new Employee(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("jobName"),
                resultSet.getString("salaryGrade"),
                resultSet.getString("department")) : null;
    }

    public Employee add(Employee employee) throws SQLException {
        String SQL = "INSERT INTO Employees(name, jobname, salarygrade, department) VALUES(?, ?, ?, ?)";
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



    public Employee update(int id, Employee employee) throws SQLException {
        String SQL = "UPDATE Employees " +
                "SET name = ? ," +
                "jobName = ? ," +
                "salaryGrade = ? ," +
                "department = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
        statement.setLong(5, id);
        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if (rowsAffected > 0) {
            updatedEmployee = this.getEmployee(id);
        }
        return updatedEmployee;
    }




    public Employee delete(int id) throws SQLException {
        String SQL = "DELETE FROM Employees WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        // Get the Employee we're deleting before we delete them
        Employee deletedEmployee = null;
        deletedEmployee = this.getEmployee(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the employee we're deleting if we didn't delete them
            deletedEmployee = null;
        }
        return deletedEmployee;
    }



}
