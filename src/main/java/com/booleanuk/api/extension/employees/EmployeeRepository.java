package com.booleanuk.api.extension.employees;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeRepository {
    private DataSource dataSource;
    private String dbUser;
    private String dbURL;
    private String dbPassword;
    private String dbDatabase;
    private Connection connection;

    public EmployeeRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM employees");
        PreparedStatement gradeStatement =
                this.connection.prepareStatement("SELECT id FROM salaries WHERE grade= ?");
        PreparedStatement departmentStatement =
                this.connection.prepareStatement("SELECT id FROM departments WHERE grade= ?");

        ResultSet results = statement.executeQuery();
        gradeStatement.setInt(1, results.getInt("salaryGrade_id"));
        ResultSet grade = gradeStatement.executeQuery();
        departmentStatement.setInt(1, results.getInt("department_id"));
        ResultSet department = departmentStatement.executeQuery();

        while(results.next())   {
            Employee employee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    grade.getString("salaryGrade"),
                    department.getString("department")
            );
            employees.add(employee);
        }
        return employees;
    }

    public Employee getOne(int id) throws SQLException {
        String SQL = "SELECT * FROM employees WHERE id= ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setInt(1, id);

        ResultSet results = statement.executeQuery();

        Employee employee = null;
        if(results.next()){
            employee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getInt("salaryGrade_id"),
                    results.getInt("department_id")
            );
        }
        return employee;
    }

    public Employee add(Employee employee) throws SQLException{
        String SQL = "INSERT INTO employees " +
                "(name, jobName, salaryGrade_id, department_id) " +
                "VALUES (?, ?, ?, ?)";

        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setInt(3, employee.getSalaryGrade());
        statement.setInt(4, employee.getDepartment());

        int rowsAffected = statement.executeUpdate();
        int newId = -1;
        if(rowsAffected > 0)    {
            try(ResultSet rs = statement.getGeneratedKeys())   {
                if(rs.next()) {
                    newId = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println(":( " + e);
            }
            employee.setId(newId);
        }   else employee = null;
        return employee;
    }

    public Employee update(int id, Employee employee) throws SQLException {
        String SQL = "UPDATE employees " +
                "SET name= ?," +
                "jobName= ?," +
                "salaryGrade_id= ?," +
                "department_id= ?" +
                "WHERE id= ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setInt(3, employee.getSalaryGrade());
        statement.setInt(4, employee.getDepartment());
        statement.setInt(5, id);
        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if(rowsAffected > 0)    {
            updatedEmployee = this.getOne(id);
        }
        return updatedEmployee;
    }

    public Employee delete(int id) throws SQLException {
        String SQL = "DELETE FROM employees WHERE id= ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setInt(1, id);
        Employee deletedEmployee = this.getOne(id);
        int rowsAffected = statement.executeUpdate();
        if(rowsAffected == 0)   {
            deletedEmployee = null;
        }
        return deletedEmployee;
    }

    public void getDatabaseCredentials()    {
        try(InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            // Properties : Represents a persistent set of properties.
            //  Can be saved to a stream or loaded from a stream
            //  Each key and its value in the property list is a string
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.URL");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");
        } catch (Exception e)   {
            System.out.println("Failed to open file: " + e);
        }
    }

    public DataSource createDataSource()    {
        String url = "jdbc:postgresql://" + this.dbURL
                + ":5432/"     + this.dbDatabase
                + "?user="     + this.dbUser
                + "&password=" + this.dbPassword;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        return dataSource;
    }


}
