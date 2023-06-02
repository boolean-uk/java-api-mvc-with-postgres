package com.booleanuk.api.salary;

import com.booleanuk.api.employee.Employee;
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


    public List<Salary> getAll() throws SQLException {
        List<Salary> allSalaries = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Salaries");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Salary currentSalary = new Salary(results.getString("grade"), results.getInt("minSalary"), results.getInt("maxSalary"));
            allSalaries.add(currentSalary);
        }

        return allSalaries;
    }


    public Salary get(String grade) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Salaries WHERE grade = ?");
        statement.setString(1, grade);
        ResultSet results = statement.executeQuery();

        Salary salary = null;
        if (results.next()) {
            salary = new Salary(results.getString("grade"), results.getInt("minSalary"), results.getInt("maxSalary"));
        }

        return salary;
    }

    public Salary update(String grade, Salary salary) throws SQLException {
        String SQL = "UPDATE Salaries " +
                "SET minSalary = ?, " +
                "maxSalary = ?, " +
                "WHERE grade = ?";

        PreparedStatement ps = this.connection.prepareStatement(SQL);
        ps.setInt(1,salary.getMinSalary());
        ps.setInt(2,salary.getMaxSalary());
        ps.setString(3, grade);
        int rowsAffected = ps.executeUpdate();

        Salary updatedSalary = null;
        if (rowsAffected > 0) {
            updatedSalary = this.get(grade);
        }
        return updatedSalary;
    }

    public Salary delete(String grade) throws SQLException {
        String SQL = "DELETE FROM Salaries WHERE grade = ?";
        PreparedStatement ps = this.connection.prepareStatement(SQL);

        Salary deletedSalary = null;
        deletedSalary = this.get(grade);

        ps.setString(1, grade);
        int rowsAffected = ps.executeUpdate();

        if (rowsAffected == 0) {
            deletedSalary = null;
        }
        return deletedSalary;
    }


    public Salary add(Salary salary) throws SQLException {
        String SQL = "INSERT INTO Salaries (grade, minSalary,maxSalary) VALUES (?,?,?)";
        PreparedStatement ps = this.connection.prepareStatement(SQL);
        ps.setString(1, salary.getGrade());
        ps.setInt(2, salary.getMinSalary());
        ps.setInt(3, salary.getMaxSalary());

        Salary newSalary = null;
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            newSalary = this.get(salary.getGrade());
        }

        return newSalary;
    }
}
