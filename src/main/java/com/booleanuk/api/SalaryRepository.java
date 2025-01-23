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
    DataSource datasource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;
    Connection connection;

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

    public Salary createSalary(Salary salary) throws SQLException {
        String sql = "INSERT INTO salaries (grade, min_salary, max_salary) VALUES (?,?,?)";
        PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,salary.getGrade());
        statement.setString(2,salary.getMinSalary());
        statement.setString(3,salary.getMaxSalary());
        int rowsAffected = statement.executeUpdate();
        long newId = 0;

        if(rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if(rs.next()) {
                    newId = rs.getLong(1);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            salary.setId(newId);
        } else {
            salary = null;
        }
        return salary;
    }

    public List<Salary> getAllSalaries() throws SQLException {
        List<Salary> salaries = new ArrayList<>();
        String sql = "SELECT * FROM salaries";
        PreparedStatement statement = this.connection.prepareStatement(sql);

        ResultSet results = statement.executeQuery();

        while(results.next()) {
            Salary salary = new Salary(results.getLong("id"), results.getString("grade"), results.getString("min_salary"), results.getString("max_salary"));
            salaries.add(salary);
        }
        return salaries;
    }

    public Salary getOneSalary(long id) throws SQLException {
        String sql = "SELECT * FROM salaries WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Salary salary = null;
        if (results.next()) {
            salary = new Salary(results.getLong("id"),
                    results.getString("grade"),
                    results.getString("min_salary"),
                    results.getString("max_salary"));
        }
        return salary;
    }

    public Salary updateSalary(long id, Salary salary) throws SQLException {
        String sql = "UPDATE salaries SET grade = ?, min_salary = ? , max_salary = ? WHERE id = ?";

        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setString(1,salary.getGrade());
        statement.setString(2,salary.getMinSalary());
        statement.setString(3,salary.getMaxSalary());
        statement.setLong(4, id);
        int rowsAffected = statement.executeUpdate();
        Salary updatedSalary = null;
        if (rowsAffected > 0) {
            updatedSalary = getOneSalary(id);
        }
        return updatedSalary;
    }

    public Salary deleteSalary(long id) throws SQLException {
        String sql = "DELETE FROM salaries WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(sql);
        Salary deleteSalary = null;
        deleteSalary = getOneSalary(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();

        if(rowsAffected == 0) {
            deleteSalary = null;
        }
        return deleteSalary;
    }
}
