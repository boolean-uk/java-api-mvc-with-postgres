package com.booleanuk.api.extension.employees;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
        PreparedStatement statement = this.connection.prepareStatement(
                "SELECT *, salaries.grade AS salaryGrade, departments.name AS department FROM employees "
                + "INNER JOIN salaries ON salaries.id = employees.salaryGrade_id "
                + "INNER JOIN departments ON departments.id = employees.department_id ");

        ResultSet results = statement.executeQuery();

        while(results.next())   {
            Employee employee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department")
            );
            employees.add(employee);
        }
        return employees;
    }

    public Employee getOne(int id) throws SQLException {
        String SQL = "SELECT * FROM employees "
                + "INNER JOIN salaries ON salaries.id = employees.salary_id \n"
                + "INNER JOIN departments ON departments.id = employees.department_id\n"
                + "WHERE id= ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setInt(1, id);

        ResultSet results = statement.executeQuery();

        Employee employee = null;
        if(results.next()){
            employee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department")
            );
        }
        return employee;
    }

    public Employee add(Employee employee) throws SQLException{
        String SQL = "INSERT INTO employees " +
                "(name, jobName, salaryGrade_id, department_id) " +
                "VALUES (?, ?, ?, ?)";

        PreparedStatement salary =this.connection.prepareStatement(
                "SELECT id AS salaryGrade_id "
                + "FROM salaries "
                + "WHERE grade= ?"
        );
        PreparedStatement department = this.connection.prepareStatement(
                "SELECT id AS department_id FROM departments "
                + "WHERE name= ?"
        );

        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

        salary.setString(1, employee.getSalaryGrade());
        department.setString(1, employee.getDepartment());

        ResultSet resultSalary = salary.executeQuery();
        if(resultSalary.next())
        {
            statement.setInt(3, resultSalary.getInt("salaryGrade_id"));
        }   else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Salary id does not exist");
        ResultSet resultDepartment = department.executeQuery();
        if(resultDepartment.next())
        {
            statement.setInt(4, resultDepartment.getInt("department_id"));
        }   else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department id does not exist");

        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());

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
        PreparedStatement salary =this.connection.prepareStatement(
                "SELECT id AS salaryGrade_id"
                        + "FROM salaries"
                        + "WHERE grade= ?"
        );
        PreparedStatement department = this.connection.prepareStatement(
                "SELECT id AS department_id FROM departments"
                        + "WHERE name= ?"
        );

        PreparedStatement statement = this.connection.prepareStatement(SQL);
        salary.setString(1, employee.getSalaryGrade());
        department.setString(1, employee.getDepartment());

        ResultSet resultSalary = salary.executeQuery();
        if(resultSalary.next())
        {
            statement.setInt(3, resultSalary.getInt("salaryGrade_id"));
        }   else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Salary id does not exist");
        ResultSet resultDepartment = department.executeQuery();
        if(resultDepartment.next())
        {
            statement.setInt(4, resultDepartment.getInt("department_id"));
        }   else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department id does not exist");

        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setInt(3, resultSalary.getInt("salaryGrade_id"));
        statement.setInt(4, resultDepartment.getInt("department_id"));
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
