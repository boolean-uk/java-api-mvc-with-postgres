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
        String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase
                +  "?user=" + this.dbUser + "&password=" + this.dbPassword;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        return dataSource;
    }

    public List<Salary> getAll() throws SQLException {
        List<Salary> all_salaries = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaries");
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Salary salary = new Salary(
                    rs.getInt("id"),
                    rs.getString("salary_grade"),
                    rs.getInt("min_salary"),
                    rs.getInt("max_salary")
            );
            all_salaries.add(salary);
        }
        return all_salaries;
    }

    public Salary getOne(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaries WHERE id = ?");
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        Salary salary = null;
        if (rs.next()) {
            salary = new Salary(
                    rs.getInt("id"),
                    rs.getString("salary_grade"),
                    rs.getInt("min_salary"),
                    rs.getInt("max_salary")
            );
        }
        return salary;
    }

    public Salary deleteOne(int id) throws SQLException {
        String _SQL = "DELETE FROM salaries WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(_SQL);
        Salary deletedSalary = this.getOne(id);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedSalary = null;
        }
        return deletedSalary;
    }

    public Salary createOne(Salary salary) throws SQLException {
        String _SQL =
                "INSERT INTO salaries " +
                "(salary_grade, min_salary, max_salary) " +
                "VALUES " +
                "(?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(_SQL);
        statement.setString(1, salary.getSalary_grade());
        statement.setInt(2, salary.getMin_salary());
        statement.setInt(3, salary.getMax_salary());
        int rowsAffected = statement.executeUpdate();
        int newId = -1;
        if (rowsAffected > 0) {
            try(ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("Oh nos: " + e);
            }
            salary.setId(newId);
        } else {
            salary = null;
        }
        return salary;
    }

    public Salary updateOne(int id, Salary salary) throws SQLException {
        String _SQL = "UPDATE employees " +
                "SET salary_grade = ?, " +
                "min_salary = ?, " +
                "max_salary = ? " +
                "WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(_SQL);
        statement.setString(1, salary.getSalary_grade());
        statement.setInt(2, salary.getMin_salary());
        statement.setInt(3, salary.getMax_salary());
        statement.setInt(4, id);
        int rowsAffected = statement.executeUpdate();
        Salary updatedSalary = null;
        if (rowsAffected > 0) {
            updatedSalary = this.getOne(id);
        }
        return updatedSalary;
    }
}


