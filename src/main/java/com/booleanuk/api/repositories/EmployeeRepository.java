package com.booleanuk.api.repositories;

import com.booleanuk.api.models.Employee;
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
    //region // FIELDS //
    DataSource datasource;
    Connection connection;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;
    //endregion

    public EmployeeRepository() throws SQLException  {
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
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser +"&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    //region // METHODS //
    public long getSalaryIdByGrade(String grade) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("select * from salaries where grade = ?");
        statement.setString(1, grade);

        ResultSet results = statement.executeQuery();

        long result = -1;
        if (results.next()) {
            result = results.getLong("id");
        }

        return result;
    }

    public long getDepartmentIdByName(String name) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("select * from departments where name = ?");
        statement.setString(1, name);

        ResultSet results = statement.executeQuery();

        long result = -1;
        if (results.next()) {
            result = results.getLong("id");
        }

        return result;
    }
    //endregion

    // ---------------------------------------------- //
    // -------------------- CRUD -------------------- //
    // ---------------------------------------------- //

    //region // CREATE //
    public Employee add(Employee employee) throws SQLException {
        long salaryId = getSalaryIdByGrade(employee.getSalaryGrade());
        if(salaryId == -1) return null;

        long departmentId = getDepartmentIdByName(employee.getDepartment());
        if(departmentId == -1) return null;

        String SQL = "insert into employees (name, jobName, salary_id, department_id) values (?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setLong(3, salaryId);
        statement.setLong(4, departmentId);

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
    //endregion

    //region // GET //
    public List<Employee> getAll() throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement(
            "select employees.id, employees.jobName, employees.name, grade as salaryGrade, departments.name as department from employees\n" +
            "inner join salaries on employees.salary_id = salaries.id\n" +
            "inner join departments on employees.department_id = departments.id"
        );

        ResultSet results = statement.executeQuery();

        List<Employee> employees = new ArrayList<>();
        while (results.next()) {
            Employee employee = new Employee(results.getLong("id"), results.getString("name"), results.getString("jobName"), results.getString("salaryGrade"), results.getString("department"));
            employees.add(employee);
        }
        return employees;
    }

    public Employee get(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement(
            "select employees.id, employees.jobName, employees.name, grade as salaryGrade, departments.name as department from employees\n" +
            "inner join salaries on employees.salary_id = salaries.id\n" +
            "inner join departments on employees.department_id = departments.id\n" +
            "where employees.id = ?"
        );
        statement.setLong(1, id);

        ResultSet results = statement.executeQuery();

        Employee employee = null;
        if (results.next()) {
            employee = new Employee(results.getLong("id"), results.getString("name"), results.getString("jobName"), results.getString("salaryGrade"), results.getString("department"));
        }
        return employee;
    }
    //endregion

    //region // UPDATE //
    public Employee update(long id, Employee employee) throws SQLException {
        long salaryId = getSalaryIdByGrade(employee.getSalaryGrade());
        if(salaryId == -1) //return null;
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not update the employee, please check all required fields are correct."
            );

        long departmentId = getDepartmentIdByName(employee.getDepartment());
        if(departmentId == -1) //return null;
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not update the employee, please check all required fields are correct."
            );

        String SQL = "update employees " +
                "set name = ? ," +
                "jobName = ? ," +
                "salary_id = ? ," +
                "department_id = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setLong(3, salaryId);
        statement.setLong(4, departmentId);
        statement.setLong(5, id);

        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if (rowsAffected > 0) {
            updatedEmployee = this.get(id);
        }
        return updatedEmployee;
    }
    //endregion

    //region // DELETE //
    public Employee delete(long id) throws SQLException {
        String SQL = "delete from employees where id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setLong(1, id);

        Employee deletedEmployee = null;
        deletedEmployee = this.get(id);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedEmployee = null;
        }
        return deletedEmployee;
    }
    //endregion
}
