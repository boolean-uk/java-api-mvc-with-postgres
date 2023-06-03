package com.booleanuk.api.repositories;

import com.booleanuk.api.data.DbAccess;
import com.booleanuk.api.models.Salary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SalaryRepository {
    @Autowired
    private DbAccess dbAccess;

    public SalaryRepository(DbAccess dbAccess) throws SQLException {
        this.dbAccess = dbAccess;
    }

    public List<Salary> getAll() throws SQLException {
        List<Salary> everyone = new ArrayList<>();
        PreparedStatement statement = this.dbAccess.getAccess().prepareStatement(
                "SELECT * FROM salary ");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Salary theSalary = new Salary(
                    results.getLong("id"),
                    results.getString("grade"),
                    results.getInt("minSalary"),
                    results.getInt("maxSalary"));
            everyone.add(theSalary);
        }
        return everyone;
    }

    public Salary get(long id) throws SQLException {
        PreparedStatement statement = this.dbAccess.getAccess().prepareStatement(
                "SELECT * FROM salary WHERE id = ?");
        // Choose set**** matching the datatype of the missing element
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Salary salary = null;
        if (results.next()) {
            salary = new Salary(
                    results.getLong("id"),
                    results.getString("grade"),
                    results.getInt("minSalary"),
                    results.getInt("maxSalary"));
        }
        return salary;
    }
    //
    public Salary update(long id, Salary salary) throws SQLException {
        String SQL = "UPDATE Salary " +
                "SET grade = ? ," +
                "minSalary = ? ," +
                "maxSalary = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.dbAccess.getAccess().prepareStatement(SQL);
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
        PreparedStatement statement = this.dbAccess.getAccess().prepareStatement(SQL);
        // Get the salary we're deleting before we delete them
        Salary deletedSalary = null;
        deletedSalary = this.get(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the salary we're deleting if we didn't delete them
            deletedSalary = null;
        }
        return deletedSalary;
    }

    public Salary add(Salary salary) throws SQLException {
        String SQL = "INSERT INTO Salary(grade, minSalary,maxSalary)" +
                "VALUES (?, ?, ?)";
        PreparedStatement statement = this.dbAccess.getAccess().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
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
