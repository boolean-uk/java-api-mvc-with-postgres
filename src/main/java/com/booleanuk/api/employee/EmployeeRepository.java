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

//    public void connectToDatabase() throws SQLException {
//        PreparedStatement statement = this.connection.prepareStatement(
//                "SELECT * FROM employees");
//        ResultSet results = statement.executeQuery();
//
//        while (results.next()) {
//            String id = Integer.toString(results.getInt("id"));
//            String name = results.getString("name");
//            String jobName = results.getString("job_name");
//            int salaryGradeId = results.getInt("salary_grade_id");
//            int departmentId = results.getInt("department_id");
//            System.out.printf("%s - %s - %s - %s - %s%n", id, name, jobName, salaryGradeId, departmentId);
//        }
//    }

    private void getDatabaseCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");
        } catch (Exception e) {
            System.out.println("Problem getDatabaseCredentials: " + e);
        }
    }

    private DataSource createDataSource() {
//        String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase
//                + "?user=" + this.dbUser + "&password=" + this.dbPassword;
//        PGSimpleDataSource dataSource = new PGSimpleDataSource();
//        dataSource.setUrl(url);
//        return dataSource;
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        System.out.println(url);
        dataSource.setUrl(url);
        return dataSource;
    }

    public Employee add(Employee employee) throws SQLException {
        String sql = """
                INSERT INTO employees\
                (name, job_name, salary_grade_id, department_id)\
                VALUES\
                (?, ?, ?, ?)
                """;
        PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, employee.getName());
        ps.setString(2, employee.getJobName());
        ps.setInt(3, employee.getSalaryGradeId());
        ps.setInt(4, employee.getDepartmentId());
        int nRowsAffected = ps.executeUpdate();
        int newId = 0;
        if (nRowsAffected > 0) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("Oops in add: " + e);
            }
            employee.setId(newId);
            return employee;
        }
        return null;
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        PreparedStatement ps = this.connection.prepareStatement(
                "SELECT * FROM employees;");

        ResultSet results = ps.executeQuery();

        while (results.next()) {
            Employee e = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("job_name"),
                    results.getInt("salary_grade_id"),
                    results.getInt("department_id"));
            employees.add(e);
        }

        return employees;
    }

    public Employee getOne(int id) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement(
                "SELECT * FROM employees WHERE id = ?;");
        ps.setInt(1, id);

        ResultSet results = ps.executeQuery();
        if (results.next()) {
            return new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("job_name"),
                    results.getInt("salary_grade_id"),
                    results.getInt("department_id"));
        }
        return null;
    }

    public Employee updateOne(int id, Employee employee) throws SQLException {
        String sql = """
                UPDATE employees
                SET name = ?,
                job_name = ?,
                salary_grade_id = ?,
                department_id = ?
                WHERE id = ?;
                """;

        PreparedStatement ps = this.connection.prepareStatement(sql);
        ps.setString(1, employee.getName());
        ps.setString(2, employee.getJobName());
        ps.setInt(3, employee.getSalaryGradeId());
        ps.setInt(4, employee.getDepartmentId());
        ps.setInt(5, id);

        int nRowsAffected = ps.executeUpdate();
        if (nRowsAffected > 0) {
            return this.getOne(id);
        }
        return null;
    }

    public Employee deleteOne(int id) throws SQLException {
        String sql = """
                DELETE FROM employees
                WHERE id = ?;
                """;
        PreparedStatement ps = this.connection.prepareStatement(sql);
        ps.setInt(1, id);

        Employee employeeToDelete = this.getOne(id);

        int nRowsAffected = ps.executeUpdate();
        if (nRowsAffected > 0) {
            return employeeToDelete;
        }
        return null;
    }
}
