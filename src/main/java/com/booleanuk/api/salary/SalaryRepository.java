package com.booleanuk.api.salary;

import com.booleanuk.api.employee.Employee;
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
    DataSource dataSource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;
    Connection connection;

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
            System.out.println("Database Credentials Failed To Load" + e);
        }
    }

    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource1 = new PGSimpleDataSource();
        dataSource1.setURL(url);
        return dataSource1;
    }

    public List<Salary> getAllSalaries() throws SQLException {
        List<Salary> salariesList = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaries");
        ResultSet results = statement.executeQuery();
        while (results.next()){
            Salary salary = new Salary(
            results.getInt("id"),
            results.getString("grade"),
            results.getInt("min_salary"),
            results.getInt("max_salary")
            );
            salariesList.add(salary);
        }
        return salariesList;
    }

    public Salary getSalary(int id) throws SQLException {
        String SQL = "SELECT * FROM salaries WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setInt(1,id);
        ResultSet result = statement.executeQuery();
        Salary salary = null;
        if (result.next()) {
            salary = new Salary(
                    result.getInt("id"),
                    result.getString("grade"),
                    result.getInt("min_salary"),
                    result.getInt("max_salary")
            );
        }
        return salary;
    }

    public Salary addSalary(Salary salary) throws SQLException {
        String SQL = "INSERT INTO salaries (grade,min_salary,max_salary) VALUES (?,?,?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1,salary.getGrade());
        statement.setInt(2,salary.getMinSalary());
        statement.setInt(3,salary.getMaxSalary());
        int rowsAffected = statement.executeUpdate();
        int newId = -1;
        if (rowsAffected > 0) {
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()){
                    newId = resultSet.getInt(1);
                }
            }catch (Exception e) {
                System.out.println("Error during Id assignment" + e);
            }
            salary.setId(newId);
        }else {
            salary = null;
        }
        return salary;
    }

    public Salary update(int id, Salary salary) throws SQLException {
        String SQL = "UPDATE salaries " +
                "SET grade = ?," +
                "min_salary = ?," +
                "max_salary = ?," +
                "WHERE id = ?;";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1,salary.getGrade());
        statement.setInt(2,salary.getMinSalary());
        statement.setInt(3,salary.getMaxSalary());
        statement.setInt(4,id);
        int rowsAffected = statement.executeUpdate();
        Salary updatedSalary = null;
        if (rowsAffected > 0) {
            updatedSalary = this.getSalary(id);
        }
        return updatedSalary
    }

    public Salary delete(int id) throws SQLException {
        String SQL = "DELETE FROM salaries WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Salary deletedSalary = null;
        deletedSalary = this.getSalary(id);
        statement.setInt(1,id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0 ) {
            deletedSalary = null;
        }
        return deletedSalary;
    }
}
