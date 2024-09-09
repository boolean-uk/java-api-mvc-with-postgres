package com.booleanuk.api;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SalaryRepository {
    private DataSource datasource;
    private String dbUser;
    private String dbURL;
    private String dbPassword;
    private String dbDatabase;
    private Connection connection;

    public SalaryRepository() throws SQLException {
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
            System.out.println("Ops: " + e);
        }
    }
    private DataSource createDataSource() {
        // The url specifies the address of the database along with username and password credentials
        final String url = "jdbc:postgresql://" + this.dbURL
                + ":5432/" + this.dbDatabase
                + "?user=" + this.dbUser
                +"&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    public List<Salary> getAll() throws SQLException {
        List<Salary> allSalaries = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaries");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Salary salary = new Salary(results.getString("grade"), results.getInt("minSalary"), results.getInt("maxSalary"));
            allSalaries.add(salary);
        }
        return allSalaries;
    }

    public Salary get(String grade) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaries WHERE grade = ?");
        // Choose set**** matching the datatype of the missing element
        statement.setString(1, grade);
        ResultSet results = statement.executeQuery();
        Salary salary = null;
        if (results.next()) {
            salary = new Salary(results.getString("grade"), results.getInt("minSalary"), results.getInt("maxSalary"));
        }
        return salary;
    }

    public Salary add(Salary salary) throws SQLException {
        String SQL = "INSERT INTO salaries(grade, minSalary, maxSalary) VALUES(?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMinSalary());
        statement.setInt(3, salary.getMaxSalary());
        int rowsAffected = statement.executeUpdate();
        String newGrade = "";
        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    newGrade = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println("Oops: " + e);
            }
            salary.setGrade(newGrade);
        } else {
            salary = null;
        }
        return salary;
    }

    public Salary update(String grade, Salary Salary) throws SQLException {
        String SQL = "UPDATE salaries " +
                "SET minSalary = ? ," +
                "maxSalary = ? " +
                "WHERE grade = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setInt(1, Salary.getMinSalary());
        statement.setInt(2, Salary.getMaxSalary());
        statement.setString(3, grade);
        int rowsAffected = statement.executeUpdate();
        Salary updatedSalary = null;
        if (rowsAffected > 0) {
            updatedSalary = this.get(grade);
        }
        return updatedSalary;
    }

    public Salary delete(String grade) throws SQLException {
        String SQL = "DELETE FROM salaries WHERE grade = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        // Get the Salary we're deleting before we delete them
        Salary deletedSalary = null;
        deletedSalary = this.get(grade);

        statement.setString(1, grade);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the Salary we're deleting if we didn't delete them
            deletedSalary = null;
        }
        return deletedSalary;
    }

}
