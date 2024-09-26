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
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;
    Connection connection;
    DataSource datasource;

    public SalaryRepository() throws SQLException {
        getDataBaseCredentials();
        this.datasource = this.createDataSource();
        this.connection = this.datasource.getConnection();
    }

    private void getDataBaseCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            dbUser = prop.getProperty("db.user");
            dbURL = prop.getProperty("db.url");
            dbPassword = prop.getProperty("db.password");
            dbDatabase = prop.getProperty("db.database");
        } catch(Exception e) {
            System.out.println("Error: " + e);
        }
    }

    private DataSource createDataSource() {

        final String url = "jdbc:postgresql://" + dbURL + ":5432/" + dbDatabase + "?user=" + dbUser +"&password=" + dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    public List<Salary> getAll() throws SQLException {
        List<Salary> allSalaries = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Salaries");
        ResultSet results = statement.executeQuery();

        while(results.next()) {
            Salary salary = new Salary(results.getInt("id"), results.getString("grade"), results.getInt("minSalary"), results.getInt("maxSalary"));
            allSalaries.add(salary);
        }
        return allSalaries;
    }

    public Salary get(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Salaries WHERE id = ?");
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Salary salary = null;
        if (results.next()) {
            salary = new Salary(results.getInt("id"), results.getString("grade"), results.getInt("minSalary"), results.getInt("maxSalary"));
        }
        return salary;
    }

    public Salary update(long id, Salary salary) throws SQLException {
        String SQL = "UPDATE Salaries " +
                "SET grade = ? ," +
                "minSalary = ? ," +
                "maxSalary = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMinSalary());
        statement.setInt(3, salary.getMaxSalary());
        statement.setLong(4, id);
        int rowsAffected = statement.executeUpdate();
        Salary updatedSalary = null;
        if (rowsAffected > 0) {
            updatedSalary = this.get(id);
        }
        return updatedSalary;
    }

    public Salary delete(long id) throws SQLException {
        String SQL = "DELETE FROM Salaries WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);

        Salary deletedSalary = this.get(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedSalary = null;
        }
        return deletedSalary;
    }

    public Salary add(Salary salary) throws SQLException {
        String SQL = "INSERT INTO Salaries(grade, minSalary, maxSalary) VALUES(?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMinSalary());
        statement.setInt(3, salary.getMaxSalary());
        int rowsAffected = statement.executeUpdate();
        long newId = 0;
        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
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
}
