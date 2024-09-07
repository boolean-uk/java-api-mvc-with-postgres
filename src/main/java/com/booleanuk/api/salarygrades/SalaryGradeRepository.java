package com.booleanuk.api.salarygrades;

import com.booleanuk.api.DatabaseResource;
import com.booleanuk.api.departments.Department;
import com.booleanuk.api.exceptions.InvalidRequest;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaryGradeRepository {
    DataSource datasource;
    Connection connection;

    public SalaryGradeRepository() throws SQLException {
        this.datasource = DatabaseResource.getInstance().datasource;
        this.connection = this.datasource.getConnection();
    }

    public SalaryGrade create(SalaryGrade newSalaryGrade) throws SQLException {

        // Check if name already exist
        String sqlCheck = "SELECT * FROM salaries WHERE grade = ?";
        PreparedStatement stm = this.connection.prepareStatement(sqlCheck, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, newSalaryGrade.getGrade());
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            throw new InvalidRequest("Could not create new salary grade, already exist");
        }

        String sql = "INSERT INTO salaries(grade, minSalary, maxSalary) VALUES(?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, newSalaryGrade.getGrade());
        statement.setInt(2, newSalaryGrade.getMinSalary());
        statement.setInt(3, newSalaryGrade.getMaxSalary());

        if (statement.executeUpdate() == 0) {
            throw new InvalidRequest("Could not create new department, please check that all required fields are correct");
        } else {
            long id = 0;
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    id = resultSet.getLong(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            newSalaryGrade.setId(id);
        }
        return newSalaryGrade;
    }

    public List<SalaryGrade> getAll() throws SQLException {
        List<SalaryGrade> salaries = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaries");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            SalaryGrade employee = new SalaryGrade(
                    resultSet.getLong("id"),
                    resultSet.getString("grade"),
                    resultSet.getInt("minsalary"),
                    resultSet.getInt("maxsalary")
            );
            salaries.add(employee);
        }
        return salaries;
    }

    public SalaryGrade getSpecific(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaries WHERE id = ?");
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        SalaryGrade employee = null;
        if (resultSet.next()) {
            employee = new SalaryGrade(
                    resultSet.getLong("id"),
                    resultSet.getString("grade"),
                    resultSet.getInt("minsalary"),
                    resultSet.getInt("maxsalary")
            );
        }
        return employee;
    }

    public SalaryGrade update(long id, SalaryGrade salaryGrade) throws SQLException {
        String SQL = "UPDATE salaries " +
                "SET grade = ? ," +
                "minSalary = ? ," +
                "maxSalary = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salaryGrade.getGrade());
        statement.setInt(2, salaryGrade.getMinSalary());
        statement.setInt(3, salaryGrade.getMaxSalary());
        statement.setLong(4, id);
        SalaryGrade updatedSalaryGrade = null;
        if (statement.executeUpdate() > 0) {
            updatedSalaryGrade = this.getSpecific(id);
        }
        return updatedSalaryGrade;
    }

    public SalaryGrade delete(long id) throws SQLException {
        String SQL = "DELETE FROM salaries WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        SalaryGrade deletedSalaryGrade = null;
        deletedSalaryGrade = this.getSpecific(id);
        statement.setLong(1, id);
        if (statement.executeUpdate() == 0) {
            deletedSalaryGrade = null;
        }
        return deletedSalaryGrade;
    }
}
