package com.booleanuk.api.salary;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
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

    public List<Salary> getAll() throws SQLException{
        List <Salary> salaries = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaries");
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Salary theSalary = new Salary(results.getInt("id"), results.getString("grade"),
                    results.getInt("minSalary"), results.getInt("maxSalary"));
            salaries.add(theSalary);
        }
        return salaries;
    }

    public Salary get(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaries WHERE id= ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Salary salary = null;
        if (results.next()) {
            salary = new Salary(results.getInt("id"), results.getString("grade"),
                    results.getInt("minSalary"), results.getInt("maxSalary"));
        }
        return salary;
    }

    public Salary update(int id, Salary salary) throws SQLException {
        String SQL = "UPDATE salaries " +
                "SET grade = ?, " +
                "minSalary = ?, " +
                "maxSalary = ? " +
                "WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMinSalary());
        statement.setInt(3, salary.getMaxSalary());
        statement.setInt(4, id);
        int rowsAffected = statement.executeUpdate();
        Salary updatedSalary = null;
        if (rowsAffected > 0) {
            updatedSalary = this.get(id);
        }
        return updatedSalary;
    }

    public Salary delete(int id) throws SQLException {
        String SQL = "DELETE FROM salaries WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Salary deletedSalary = this.get(id);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedSalary = null;
        }
        return deletedSalary;
    }

    public Salary add(Salary salary) throws SQLException {
        String SQL = "INSERT INTO salaries (grade, minSalary, maxSalary) VALUES (?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMinSalary());
        statement.setInt(3, salary.getMaxSalary());
        int roesAffected = statement.executeUpdate();
        int newId = -1;
        if (roesAffected > 0) {
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()){
                    newId = resultSet.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("Empty resultSet: " + e);
            }
            salary.setId(newId);
        } else {
            salary = null;
        }
        return salary;
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
            System.out.println("Could not get databaseCredentials:  " + e);
        }
    }

    private DataSource createDataSource() {
        String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase
                + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        return dataSource;
    }
}
