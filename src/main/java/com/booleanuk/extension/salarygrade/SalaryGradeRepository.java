package com.booleanuk.extension.salarygrade;

import com.booleanuk.extension.DatabaseManager;
import com.booleanuk.extension.salarygrade.SalaryGrade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaryGradeRepository {
    Connection connection;

    public SalaryGradeRepository() throws SQLException {
        this.connection = DatabaseManager.getInstance().getDataSource().getConnection();
    }

    public List<SalaryGrade> getAll() throws SQLException  {
        List<SalaryGrade> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM SalaryGradeS");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            SalaryGrade theSalaryGrade = new SalaryGrade(results.getInt("id"), results.getString("grade"), results.getInt("minSalary"), results.getInt("maxSalary"));
            everyone.add(theSalaryGrade);
        }
        return everyone;
    }

    public SalaryGrade getOne(int id) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaryGrades WHERE id = ?");
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        SalaryGrade salaryGrade = null;
        if (results.next()) {
            salaryGrade = new SalaryGrade(
                    results.getInt("id"),
                    results.getString("grade"),
                    results.getInt("minSalary"),
                    results.getInt("maxSalary")
            );
            return salaryGrade;
        }
        return null;
    }

    public SalaryGrade add(SalaryGrade salaryGrade) throws SQLException {
        String SQL = "INSERT INTO SalaryGrades(grade, minSalary, maxSalary) VALUES(?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, salaryGrade.getGrade());
        statement.setInt(2, salaryGrade.getMinSalary());
        statement.setInt(3, salaryGrade.getMaxSalary());
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
            salaryGrade.setId(newId);
        } else {
            salaryGrade = null;
        }
        return salaryGrade;
    }

    public SalaryGrade update(int id, SalaryGrade salaryGrade) throws SQLException {
        String SQL = "UPDATE salaryGrades " +
                "SET grade = ? ," +
                "minSalary = ? ," +
                "maxSalary = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salaryGrade.getGrade());
        statement.setInt(2, salaryGrade.getMinSalary());
        statement.setInt(3, salaryGrade.getMaxSalary());
        statement.setInt(4, id);
        int rowsAffected = statement.executeUpdate();
        SalaryGrade updatedSalaryGrade = null;
        if (rowsAffected > 0) {
            updatedSalaryGrade = this.getOne(id);
        }
        return updatedSalaryGrade;
    }

    public SalaryGrade delete(int id) throws SQLException {
        // Get the customer we're deleting before we delete them
        SalaryGrade deletedSalaryGrade = this.getOne(id);

        String SQL = "DELETE FROM SalaryGrades WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the customer we're deleting if we didn't delete them
            deletedSalaryGrade = null;
        }
        return deletedSalaryGrade;
    }
}
