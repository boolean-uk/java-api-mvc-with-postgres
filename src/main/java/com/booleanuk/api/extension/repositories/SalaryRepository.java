package com.booleanuk.api.extension.repositories;

import com.booleanuk.api.database.Database;
import com.booleanuk.api.database.PostgresDatabase;
import com.booleanuk.api.extension.models.Salary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaryRepository {
    private final Database db = new PostgresDatabase();

    public List<Salary> getAll() {
        List<Salary> salaries = new ArrayList<>();

        try (Connection connection = db.connection()) {
            String selectAll = "SELECT * FROM salaries";
            PreparedStatement statement = connection.prepareStatement(selectAll);
            ResultSet results = statement.executeQuery();

            while(results.next()) {
                salaries.add(
                        new Salary(
                                results.getInt("id"),
                                results.getInt("min_salary"),
                                results.getInt("max_salary"),
                                results.getString("grade")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return salaries;
    }

    public Salary get(int id) {
        Salary salary = null;

        try (Connection connection = db.connection()) {
            String selectId = "SELECT * FROM salaries WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(selectId);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

            while(results.next()) {
                salary = new Salary(
                        results.getInt("id"),
                        results.getInt("min_salary"),
                        results.getInt("max_salary"),
                        results.getString("grade")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return salary;
    }

    public Salary update(int id, Salary salary) {
        Salary requestedSalary = null;

        try (Connection connection = db.connection()) {
            String updateId = "UPDATE salaries " +
                    "SET grade=?, min_salary=?, max_salary=? " +
                    "WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(updateId);
            statement.setString(1, salary.getGrade());
            statement.setInt(2, salary.getMinSalary());
            statement.setInt(3, salary.getMaxSalary());
            statement.setInt(4, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0)
                requestedSalary = get(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return requestedSalary;
    }

    public Salary add(Salary salary) {
        Salary newSalary = null;

        try (Connection connection = db.connection()) {
            String addId = "INSERT INTO salaries (grade, min_salary, max_salary) " +
                    "values (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(addId, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, salary.getGrade());
            statement.setInt(2, salary.getMinSalary());
            statement.setInt(3, salary.getMaxSalary());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet results = statement.getGeneratedKeys()) {
                    if (results.next()) {
                        int id = results.getInt(1);
                        newSalary = get(id);
                    }
                } catch (SQLException e) {
                    System.out.println("Error getting the generated key" + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return newSalary;
    }

    public Salary delete(int id) {
        Salary salary = get(id);

        try (Connection connection = db.connection()) {
            String deleteId = "DELETE FROM salaries WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(deleteId);
            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0)
                salary = null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return salary;
    }
}
