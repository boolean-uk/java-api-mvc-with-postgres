package com.booleanuk.api.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.booleanuk.api.config.DatabaseConfig;
import com.booleanuk.api.models.Salary;

public class SalaryRepository {
    private final Connection connection;

    public SalaryRepository() throws SQLException {
        DatabaseConfig dbConfig = new DatabaseConfig();
        this.connection = dbConfig.getConnection();
    }

    public List<Salary> getAll() throws SQLException {
        List<Salary> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Salaries");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Salary theSalary = new Salary(results.getLong("id"), results.getString("grade"), results.getInt("minSalary"), results.getInt("maxSalary"));
            everyone.add(theSalary);
        }
        return everyone;
    }

    public Salary getOne(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Salaries WHERE id = ?");
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        if (results.next()) {
            return new Salary(results.getLong("id"), results.getString("grade"), results.getInt("minSalary"), results.getInt("maxSalary"));
        }
        return null;
    }

    public Salary add(Salary salary) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("INSERT INTO Salaries (grade, minSalary, maxSalary) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMinSalary());
        statement.setInt(3, salary.getMaxSalary());
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return getOne(generatedKeys.getLong(1));
                }
            } catch (SQLException e) {
                System.out.println("Oops: " + e);
            }
        }
        return null;
    }

    public Salary update(long id, Salary salary) throws SQLException {
        if (salary.getGrade() == null || salary.getGrade().trim().isEmpty() ||
                salary.getMinSalary() == 0 || salary.getMaxSalary() == 0) {
            throw new SQLException("Could not update the salary, please check all required fields are correct.");
        }

        PreparedStatement statement = this.connection.prepareStatement("UPDATE Salaries SET grade = ?, minSalary = ?, maxSalary = ? WHERE id = ?");
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMinSalary());
        statement.setInt(3, salary.getMaxSalary());
        statement.setLong(4, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return getOne(id);
        }
        return null;
    }

    public Salary delete(long id) throws SQLException {
        Salary toBeDeleted = getOne(id);
        PreparedStatement statement = this.connection.prepareStatement("DELETE FROM Salaries WHERE id = ?");
        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return toBeDeleted;
        }
        return null;
    }

}
