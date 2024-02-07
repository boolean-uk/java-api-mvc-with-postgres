package com.booleanuk.api.Repositories;

import com.booleanuk.api.Models.Department;
import com.booleanuk.api.Models.Salary;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Repository
public class SalaryRepository {
    private DataSource datasource;
    private Connection connection;

    public SalaryRepository() throws SQLException {
        this.datasource = this.createDataSource();
        this.connection = this.datasource.getConnection();
    }

    private DataSource createDataSource() throws SQLException {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String dbURL = properties.getProperty("db.url");
        String dbUser = properties.getProperty("db.user");
        String dbPassword = properties.getProperty("db.password");
        String dbDatabase = properties.getProperty("db.database");

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://" + dbURL + ":5432/" + dbDatabase);
        dataSource.setUser(dbUser);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    public List<Salary> getAllSalaries() {
        List<Salary> salaries = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Salaries")) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String grade = resultSet.getString("grade");
                int minSalary = resultSet.getInt("minSalary");
                int maxSalary = resultSet.getInt("maxSalary");
                Salary salary = new Salary(id, grade, minSalary, maxSalary);
                salaries.add(salary);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve salaries.", e);
        }
        return salaries;
    }

    public Salary getSalaryById(long id) {
        try {
            String sql = "SELECT * FROM Salaries WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String grade = resultSet.getString("grade");
                        int minSalary = resultSet.getInt("minSalary");
                        int maxSalary = resultSet.getInt("maxSalary");
                        return new Salary(id, grade, minSalary, maxSalary);
                    } else {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Salary with ID " + id + " not found.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve salary with ID " + id, e);
        }
    }

    public Salary updateSalary(long id, Salary salary) throws SQLException {
        String SQL = "UPDATE Salaries SET grade = ?, minSalary = ?, maxSalary = ? WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMinSalary());
        statement.setInt(3, salary.getMaxSalary());
        statement.setLong(4, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return getSalaryById(id);
        } else {
            return null; // or throw an exception if necessary
        }
    }

    public Salary deleteSalary(long id) throws SQLException {
        String SQL = "DELETE FROM Salaries WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            Salary deletedSalary = null;
            deletedSalary = this.getSalaryById(id);

            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                deletedSalary = null;
            }
            return deletedSalary;
        }
    }

    public Salary createSalary(Salary salary) {
        try {
            String SQL = "INSERT INTO Salaries(grade, minSalary, maxSalary) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, salary.getGrade());
            statement.setInt(2, salary.getMinSalary());
            statement.setInt(3, salary.getMaxSalary());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create salary.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                salary.setId(id);
                return salary;
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve generated key for the created salary.");
            }
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create salary.", e);
        }
    }
}
