package com.booleanuk.api.model;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import javax.sql.StatementEvent;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
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
        dataSource.setURL(url);
        return dataSource;
    }

    public List<Salary> getAll() throws SQLException  {
        List<Salary> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Salaries");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Salary theSalary = new Salary(
                    results.getInt("id"),
                    results.getString("grade"),
                    results.getInt("minSalary"),
                    results.getInt("maxSalary"));
            everyone.add(theSalary);
        }
        return everyone;
    }

    public Salary getOne(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Salaries WHERE id = ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Salary salary = null;
        if (results.next()) {
            salary = new Salary(
                    results.getInt("id"),
                    results.getString("grade"),
                    results.getInt("minSalary"),
                    results.getInt("maxSalary"));
        }
        return salary;
    }

    public Salary add(Salary salary) throws SQLException {
        if(!salary.getGrade().isBlank() && salary.getMinSalary() > 0 && salary.getMaxSalary() > 0) {
            String SQL = "INSERT INTO Salaries(grade, minSalary, maxSalary) VALUES(?, ?, ?)";
            PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, salary.getGrade());
            statement.setInt(2, salary.getMinSalary());
            statement.setInt(3, salary.getMaxSalary());
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
                salary.setId(newId);
            } else {
                salary = null;
            }
            return salary;
        } return null;
    }

    public Salary update(int id, Salary salary) throws SQLException {
        if (!salary.getGrade().isBlank() && salary.getMinSalary() > 0 && salary.getMaxSalary() > 0) {
            String SQL = "UPDATE Salaries " +
                    "SET grade = ? ," +
                    "minSalary = ? ," +
                    "maxSalary = ? " +
                    "WHERE id = ? ";
            PreparedStatement statement = this.connection.prepareStatement(SQL);
            statement.setString(1, salary.getGrade());
            statement.setInt(2, salary.getMinSalary());
            statement.setInt(3, salary.getMaxSalary());
            statement.setInt(4, id);
            int rowsAffected = statement.executeUpdate();
            Salary updatedSalary = null;
            if (rowsAffected > 0) {
                updatedSalary = this.getOne(id);
            }
            return updatedSalary;
        }
        return null;
    }

    public Salary delete(int id) throws SQLException {
        String SQL = "DELETE FROM Salaries WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Salary deletedSalary = null;
        deletedSalary = this.getOne(id);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedSalary = null;
        }
        return deletedSalary;
    }
}