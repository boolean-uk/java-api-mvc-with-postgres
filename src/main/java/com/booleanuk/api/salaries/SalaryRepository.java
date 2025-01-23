package com.booleanuk.api.salaries;

import com.booleanuk.api.employees.Employee;
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
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser +"&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    public List<Salary> getAll() throws SQLException {
        List<Salary> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaries");
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Salary theSalary = new Salary(results.getLong("id"), results.getString("grade"), results.getInt("minSalary"), results.getInt("maxSalary"));
            everyone.add(theSalary);
        }
        return everyone;
    }


    public Salary get(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaries WHERE id = ?");
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Salary salary = null;

        if (results.next()) {

            salary = new Salary(results.getLong("id"),results.getString("grade"), results.getInt("minSalary"), results.getInt("maxSalary"));
        }
        return salary;
    }

    public Salary update(long id, Salary salary) throws SQLException {
        String SQL = "UPDATE salaries " +
                "SET grade = ? ," +
                "minSalary = ? ," +
                "maxSalary = ? ," +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salary.getGrade());
        statement.setString(2, String.valueOf(salary.getMinSalary()));
        statement.setString(3, String.valueOf(salary.getMaxSalary()));
        statement.setLong(4, id);
        int rowsAffected = statement.executeUpdate();
        Salary updatedSalary = null;

        if (rowsAffected > 0) {
            updatedSalary = this.get(id);
        }
        return updatedSalary;
    }

    public Salary delete(long id) throws SQLException {
        String SQL = "DELETE FROM salaries WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Salary deletedSalary = null;
        deletedSalary = this.get(id);
        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected == 0) {
            deletedSalary = null;
        }
        return deletedSalary;
    }

    public Salary add(Salary salary) throws SQLException {
        String SQL = "INSERT INTO salaries(grade, minSalary, maxSalary) VALUES(?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, salary.getGrade());
        statement.setString(2, String.valueOf(salary.getMinSalary()));
        statement.setString(3, String.valueOf(salary.getMaxSalary()));
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
            salary.setId(newId);
        } else {
            statement = null;
        }
        return salary;
    }

}
