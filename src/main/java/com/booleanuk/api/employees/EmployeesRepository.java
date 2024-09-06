package com.booleanuk.api.employees;

import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class EmployeesRepository {
    DataSource datasource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;
    Connection connection;

    public EmployeesRepository() throws SQLException {
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
        } catch (Exception e) {
            System.out.println("Oops: " + e);
        }
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    public void connectToDatabase() throws SQLException  {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            String id = "" + results.getLong("id");
            String name = results.getString("name");
            String jobName = results.getString("jobName");
            String salaryGrade = results.getString("salaryGrade");
            String department = results.getString("department");
            System.out.println(id + " - " + name + " - " + jobName + " - " + salaryGrade + " - " + department);
        }
    }


    public List<Employees> getAll() throws SQLException  {
        List<Employees> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM EMPLOYEES");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Employees theemployee = new Employees(results.getLong("id"), results.getString("name"), results.getString("jobName"), results.getString("salaryGrade"), results.getString("department"));
            everyone.add(theemployee);
        }
        return everyone;
    }

    public Employees get(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employees WHERE id = ?");
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Employees employees = null;
        if (results.next()) {
            employees = new Employees(results.getLong("id"), results.getString("name"), results.getString("jobName"), results.getString("salaryGrade"), results.getString("department"));
        }
        return employees;
    }

    public Employees update(long id, Employees employees) throws SQLException {
        String SQL = "UPDATE Employees " +
                "SET name = ?, " +
                "jobName = ?, " +
                "salaryGrade = ?, " +
                "department = ? " +
                "WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employees.getName());
        statement.setString(2, employees.getJobName());
        statement.setString(3, employees.getSalaryGrade());
        statement.setString(4, employees.getDepartment());
        statement.setLong(5, id);

        int rowsAffected = statement.executeUpdate();
        Employees updatedemployees = null;
        if (rowsAffected > 0) {
            updatedemployees = this.get(id);
        }
        return updatedemployees;
    }






    public Employees delete(long id) throws SQLException {
        String SQL = "DELETE FROM Employees WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Employees deletedEmployee = null;
        deletedEmployee = this.get(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedEmployee = null;
        }
        return deletedEmployee;
    }


    public Employees add(Employees employees) throws SQLException {
        String SQL = "INSERT INTO Employees(name, jobName, salaryGrade, department) VALUES(?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employees.getName());
        statement.setString(2, employees.getJobName());
        statement.setString(3, employees.getSalaryGrade());
        statement.setString(4, employees.getDepartment());

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
            employees.setId(newId);
        } else {
            employees = null;
        }
        return employees;
    }




}



