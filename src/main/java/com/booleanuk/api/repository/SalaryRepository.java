package com.booleanuk.api.repository;

import com.booleanuk.api.model.Salary;
import com.booleanuk.api.util.UtilClass;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SalaryRepository {

    private Connection connection;

    public SalaryRepository() throws SQLException {
        UtilClass util = new UtilClass();
        this.connection = util.getConnection();
    }



    public List<Salary> getAll() throws SQLException {
        List<Salary> salaries = new ArrayList<>();


        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Salary");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Salary salary = new Salary(resultSet.getString(1), resultSet.getInt(2), resultSet.getInt(3));
            salaries.add(salary);
        }

        return salaries;
    }

    public Salary get(String grade) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Salary WHERE grade = ?");
        statement.setString(1, grade);
        ResultSet resultSet = statement.executeQuery();
        Salary salary = null;
        if (resultSet.next()) {
            salary = new Salary(resultSet.getString(1), resultSet.getInt(2), resultSet.getInt(3));
        }
        return salary;
    }

    public Salary update(String grade, Salary salary) throws SQLException {
        String SQL = "UPDATE Salary " +
                "SET grade = ? ," +
                "minSalary = ? ," +
                "maxSalary = ? ," +
                "WHERE grade = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMinSalary());
        statement.setInt(3, salary.getMaxSalary());
        int rowsAffected = statement.executeUpdate();
        Salary updatedSalary = null;
        if (rowsAffected > 0) {
            updatedSalary = this.get(grade);
        }
        return updatedSalary;
    }

    public Salary delete(String grade) throws SQLException {
        String SQL = "DELETE FROM Salary WHERE grade = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        Salary deletedSalary = null;
        deletedSalary = this.get(grade);

        statement.setString(1, grade);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedSalary = null;
        }
        return deletedSalary;

    }

    public Salary add(Salary salary) throws SQLException {
        String SQL = "INSERT INTO Salary(grade, minSalary, maxSalary) VALUES(?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMinSalary());
        statement.setInt(3, salary.getMaxSalary());
        int rowsAffected = statement.executeUpdate();
        String newGrade = "";
        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    newGrade = rs.getString(1);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            salary.setGrade(newGrade);
        } else {
            salary = null;
        }
        return salary;
    }
}
