package com.booleanuk.api.repositories;

import com.booleanuk.api.models.Salary;
import com.booleanuk.api.DatabaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SalaryRepository {
    private Connection connection;

    @Autowired
    public SalaryRepository(DatabaseManager databaseManager) {
        try {
            this.connection = databaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error getting database connection", e);
        }
    }

    public List<Salary> getAll() throws SQLException  {
        List<Salary> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Salaries");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Salary theSalary = new Salary(results.getInt("id"), results.getString("grade"), results.getInt("min_salary"), results.getInt("max_salary"));
            everyone.add(theSalary);
        }
        return everyone;
    }

    public Salary add(Salary salary) throws SQLException {
        String SQL = "INSERT INTO Salaries (grade, min_salary, max_salary) VALUES(?, ?, ?)";
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
    }

    public Salary get(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Salaries WHERE id = ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        Salary salary = null;
        if (results.next()) {
            salary = new Salary(results.getInt("id"), results.getString("grade"), results.getInt("min_salary"), results.getInt("max_salary"));
        }
        return salary;
    }

    public Salary update(int id, Salary salary) throws SQLException {
        String SQL = "UPDATE Salaries " +
                "SET grade = ? ," +
                "min_salary = ? ," +
                "max_salary = ? " +
                "WHERE id = ? ";
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
        String SQL = "DELETE FROM Salaries WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Salary deletedSalary = this.get(id);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedSalary = null;
        }
        return deletedSalary;
    }
}
