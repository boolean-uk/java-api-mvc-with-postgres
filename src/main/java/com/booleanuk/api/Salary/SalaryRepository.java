package com.booleanuk.api.Salary;

import com.booleanuk.api.Employee.Employee;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
    String dbUrl;
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
            this.dbUrl = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");

        } catch (Exception e) {
            System.out.println("oopsie: " + e);
        }

    }


    private DataSource createDataSource() {
        final String url = "jdbc:postgresql://" + this.dbUrl + ":5432/" + this.dbDatabase +
                "?user=" + this.dbUser + "&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }


    public List<Salary> getAll() throws SQLException {
        List<Salary> salaries = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Salaries");

        ResultSet  results = statement.executeQuery();

        while (results.next()) {
            Salary salary = new Salary(
                    results.getInt("id"),
                    results.getString("grade"),
                    results.getInt("minSalary"),
                    results.getInt("maxSalary")
            );
            salaries.add(salary);
        }
        return salaries;
    }

    public Salary get(int id)  {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement("SELECT * FROM Salaries WHERE id = ?");

        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Salary salary = null;
        if(results.next()) {
            salary = new Salary(
                    id,
                    results.getString("grade"),
                    results.getInt("minSalary"),
                    results.getInt("maxSalary")
            );
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grades matching that id were found");
        }
            return salary;
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salary grades matching that id were found");
        }

    }

    public Salary update(int id, Salary salary)  {
        String SQL = "UPDATE Salaries " +
                "SET grade = ? ," +
                "minSalary = ? ," +
                "maxSalary = ? " +
                "WHERE id = ? ";
        try {


            PreparedStatement statement = this.connection.prepareStatement(SQL);
            statement.setString(1, salary.getGrade());
            statement.setInt(2, salary.getMinSalary());
            statement.setInt(3, salary.getMaxSalary());
            statement.setInt(4, id);

            int rowsAffected = statement.executeUpdate();


        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "could not update the salary grade, please check all required fields are correct");

        }
        return this.get(id);

    }

    public Salary delete(int id) throws SQLException {
        String SQL = "DELETE FROM Salaries WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setInt(1, id);

        Salary deletedSalary = null;
        deletedSalary = this.get(id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            return this.get(id);
        }
        return deletedSalary;
    }

    public Salary add(Salary salary) throws SQLException {
        String SQL = "INSERT INTO Salaries (grade, minSalary, maxSalary) VALUES (?,?,?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMinSalary());
        statement.setInt(3, salary.getMaxSalary());
        int rowsAffected = statement.executeUpdate();
        int newId = 0;
        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if(rs.next()) {
                    newId = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("oops: " + e);
            }
            salary.setId(newId);
        } else {
            salary = null;
        }
        return salary;
    }
}
