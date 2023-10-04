package com.booleanuk.api.salaryGrade;

import com.booleanuk.api.dbConfiguration.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SalaryGradeRepository {
    private final Connection connection;
    @Autowired
    public SalaryGradeRepository(DatabaseConnection dbConnection){
        this.connection = dbConnection.getConnection();
    }

    public List<SalaryGrade> getAll() throws SQLException  {
        List<SalaryGrade> allGrades = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaries");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            SalaryGrade theSalaryGrade = new SalaryGrade(results.getLong("id"),
                    results.getString("grade"), results.getInt("minSalary"),
                    results.getInt("maxSalary"));
            allGrades.add(theSalaryGrade);
        }
        return allGrades;
    }

    public SalaryGrade get(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaries WHERE id = ?");
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        SalaryGrade salaryGrade = null;
        if (results.next()) {
            salaryGrade = new SalaryGrade(results.getLong("id"),
                    results.getString("grade"), results.getInt("minSalary"),
                    results.getInt("maxSalary"));
        }
        return salaryGrade;
    }

    public SalaryGrade update(long id, SalaryGrade employee) throws SQLException {
        String SQL = "UPDATE salaries " +
                "SET grade = ? ," +
                "minSalary = ? ," +
                "maxSalary = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getGrade());
        statement.setInt(2, employee.getMinSalary());
        statement.setInt(3, employee.getMaxSalary());
        statement.setLong(4, id);
        int rowsAffected = statement.executeUpdate();
        SalaryGrade updatedSalaryGrade = null;
        if (rowsAffected > 0) {
            updatedSalaryGrade = this.get(id);
        }
        return updatedSalaryGrade;
    }

    public SalaryGrade delete(long id) throws SQLException {
        String SQL = "DELETE FROM salaries WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        SalaryGrade deletedSalaryGrade = null;
        deletedSalaryGrade = this.get(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedSalaryGrade = null;
        }
        return deletedSalaryGrade;
    }

    public SalaryGrade add(SalaryGrade salaryGrade) throws SQLException {
        String SQL = "INSERT INTO salaries(grade, minSalary, maxSalary) VALUES(?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, salaryGrade.getGrade());
        statement.setInt(2, salaryGrade.getMinSalary());
        statement.setInt(3, salaryGrade.getMaxSalary());
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
            salaryGrade.setId(newId);
        } else {
            salaryGrade = null;
        }
        return salaryGrade;
    }
}
