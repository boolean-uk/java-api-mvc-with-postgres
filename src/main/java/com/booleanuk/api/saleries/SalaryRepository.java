package com.booleanuk.api.saleries;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SalaryRepository {
    private DataSource dataSource;
    private String dbUser;
    private String dbURL;
    private String dbPassword;
    private String dbDatabase;
    private Connection connection;

    public SalaryRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.dataSource = this.createDataSource();
        this.connection = this.dataSource.getConnection();
    }

    public List<Salary> getAll() throws SQLException {
        List<Salary> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement(
                "SELECT * FROM Salaries");
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Salary theSalary = new Salary(rs.getInt("id"),
                    rs.getString("grade"),
                    rs.getInt("min_salary"),
                    rs.getInt("max_salary"));
            everyone.add(theSalary);
        }
        return everyone;
    }

    public Salary get(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement(
                "SELECT * FROM Salaries WHERE id= ?");
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        Salary salary = null;
        if (rs.next()) {
            salary = new Salary(rs.getInt("id"),
                    rs.getString("grade"),
                    rs.getInt("min_salary"),
                    rs.getInt("max_salary"));
        }
        return salary;
    }

    public Salary update(int id, Salary salary) throws SQLException {
        String SQL = "UPDATE Salaries " +
                "SET grade = ?, " +
                "min_salary = ?, " +
                "max_salary = ? " +
                "WHERE id = ?";

        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMinSalary());
        statement.setInt(3, salary.getMaxSalary());
        statement.setInt(4, id);
        int rowAffected = statement.executeUpdate();
        Salary updatedSalary = null;
        if (rowAffected > 0) {
            updatedSalary = this.get(id);
        }
        return updatedSalary;
    }

    public Salary delete(int id) throws SQLException {
        String SQL = "DELETE FROM Salaries WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Salary deletedSalary = this.get(id);

        statement.setInt(1, id);
        int rowAffected = statement.executeUpdate();
        if (rowAffected == 0) {
            deletedSalary = null;
        }
        return deletedSalary;
    }

    public Salary add(Salary salary) throws SQLException {
        String SQL =
                "INSERT INTO Salaries (grade, min_salary, max_salary) "
                        + "VALUES (?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMinSalary());
        statement.setInt(3, salary.getMaxSalary());
        int rowAffected = statement.executeUpdate();
        int newID = -1;
        if (rowAffected > 0) {
            try (ResultSet rs = statement.getResultSet()) {
                if (rs.next()) {
                    newID = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("Oh nos: " + e);
            }
            salary.setId(newID);
        } else {
            salary = null;
        }
        return salary;
    }

    //---------------------- Private section ----------------------//

    private void getDatabaseCredentials() {
        try (InputStream input = new FileInputStream(
                "src/main/resources/config.properties")) {
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
        String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase
                + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }
}
