package com.booleanuk.api.repositories;

import com.booleanuk.api.models.Salary;
import com.booleanuk.api.sqlUtils.SQLConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SalaryRepo {

    private final SQLConnection db;

    public SalaryRepo() throws SQLException {
        db = SQLConnection.getInstance();
    }

    public List<Salary> getAll() throws SQLException {
        List<Salary> salaries = new ArrayList<>();

        PreparedStatement statement = this.db.getConnection().prepareStatement("SELECT * FROM Salary");
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Salary salary = new Salary(results.getLong("id"),
                    results.getString("grade"), results.getInt("minSalary"),
                    results.getInt("maxSalary"));
            salaries.add(salary);
        }
        return salaries;
    }

    public Salary get(long id) throws SQLException {
        PreparedStatement statement = this.db.getConnection().prepareStatement("SELECT * FROM Salary WHERE id = ?");
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Salary salary = null;
        if (results.next()) {
            salary = new Salary(results.getLong("id"),
                    results.getString("grade"), results.getInt("minSalary"),
                    results.getInt("maxSalary"));
        }
        return salary;
    }

    public Salary update(long id, Salary salary) throws SQLException {
        String SQL = "UPDATE Salary " +
                "SET grade = ? ," +
                "minSalary = ? ," +
                "maxSalary = ? ," +
                "WHERE id = ? ";
        PreparedStatement statement = this.db.getConnection().prepareStatement(SQL);
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
        String SQL = "DELETE FROM Salary WHERE id = ?";
        PreparedStatement statement = this.db.getConnection().prepareStatement(SQL);
        statement.setLong(1, id);

        Salary deletedSalary = this.get(id);
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected == 0) {
            deletedSalary = null;
        }
        return deletedSalary;
    }

    public Salary add(Salary salary) throws SQLException {
        String SQL = "INSERT INTO Salary(grade, minSalary, maxSalary) VALUES(?, ?, ?)";
        PreparedStatement statement = this.db.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
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
                System.out.println("Oops: " + e);
            }
            salary.setId(newId);
        } else {
            salary = null;
        }
        return salary;
    }

}
