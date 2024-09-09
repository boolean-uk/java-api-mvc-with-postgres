package com.booleanuk.api.repository;

import com.booleanuk.api.db.DatabaseConnection;
import com.booleanuk.api.model.Employee;
import com.booleanuk.api.model.Salary;
import com.booleanuk.api.model.Salary;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class SalaryRepository {

    public SalaryRepository() throws SQLException {
    }

    public List<Salary> getAll() throws SQLException {
        List<Salary> toReturn = new ArrayList<>();
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM salaries");

        ResultSet res = statement.executeQuery();

        while (res.next()) {
            Salary s = new Salary(
                    res.getLong("id"),
                    res.getString("grade"),
                    res.getInt("minSalary"),
                    res.getInt("maxSalary"));
            toReturn.add(s);
        }

        return toReturn;
    }

    public Salary getOne(long id) throws SQLException {
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM salaries WHERE id = ?");
        statement.setLong(1, id);

        ResultSet res = statement.executeQuery();
        Salary s = null;
        if (res.next()) {
            s = new Salary(
                    res.getLong("id"),
                    res.getString("grade"),
                    res.getInt("minSalary"),
                    res.getInt("maxSalary")
            );
        }
        return s;
    }

    public Salary add(Salary salary) throws SQLException {
        String query = "INSERT INTO salaries (grade, minSalary, maxSalary) VALUES (?, ?, ?)";
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMinSalary());
        statement.setInt(3, salary.getMaxSalary());
        final int rowsAffected = statement.executeUpdate();
        long newId = 0;
        if (rowsAffected > 0) {
            ResultSet res;
            try {
                res = statement.getGeneratedKeys();
                if (res.next()) {
                    newId = res.getLong(1);
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

    public Salary delete(long id) throws SQLException {
        String query = "DELETE FROM salaries WHERE id = ?";
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(query);
        Salary toDelete = null;
        toDelete = this.getOne(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            toDelete = null;
        }
        return toDelete;
    }

    public Salary update(long id, Salary newData) throws SQLException {
        String SQL = "UPDATE salaries " +
                "SET grade = ? ," +
                "minSalary = ? ," +
                "maxSalary = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(SQL);
        statement.setString(1, newData.getGrade());
        statement.setInt(2, newData.getMinSalary());
        statement.setInt(3, newData.getMaxSalary());
        statement.setLong(4, id);
        int rowsAffected = statement.executeUpdate();
        Salary updatedSalary = null;
        if (rowsAffected > 0) {
            updatedSalary = this.getOne(id);
        }
        return updatedSalary;
    }


}
