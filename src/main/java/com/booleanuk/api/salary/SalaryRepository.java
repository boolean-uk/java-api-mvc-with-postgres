package com.booleanuk.api.salary;

import com.booleanuk.api.employee.Employee;
import com.booleanuk.api.setup.DatabaseConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SalaryRepository {
    private final Connection connection;

    @Autowired
    public SalaryRepository(DatabaseConnector dbConnector){
        connection = dbConnector.getConnection();
    }

    public Salary add(Salary salary) throws SQLException {
        String SQL = "INSERT INTO Salary (grade, min_salary, max_salary) VALUES (?,?,?)";
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
                System.out.println("Something went wrong: " + e);
            }
            salary.setId(newId);
        } else {
            salary = null;
        }

        return salary;
    }

    public List<Salary> getAll() throws SQLException {
        List<Salary> salaries = new ArrayList<>();

        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Salary");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            Salary current = new Salary(
                    resultSet.getString("grade"),
                    resultSet.getInt("min_salary"),
                    resultSet.getInt("max_salary")
            );

            salaries.add(current);
        }
        return salaries;
    }

    public Salary getById(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Salary WHERE id=?");
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();
        Salary salary = null;
        if(resultSet.next()){
            salary = new Salary(resultSet.getString("grade"),
                    resultSet.getInt("min_salary"),
                    resultSet.getInt("max_salary")
            );
        }

        return salary;
    }

    public Salary update(int id, Salary salary) throws SQLException {
        String SQL = "UPDATE Salary " +
                "SET grade = ? ," +
                "min_salary = ? ," +
                "max_salary = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, salary.getGrade());
        statement.setInt(2, salary.getMinSalary());
        statement.setInt(3, salary.getMaxSalary());
        statement.setInt(5, id);
        int rowsAffected = statement.executeUpdate();
        Salary updatedSalary = null;
        if (rowsAffected > 0) {
            updatedSalary = this.getById(id);
        }
        return updatedSalary;
    }

    public Salary delete(int id) throws SQLException {
        String SQL = "DELETE FROM Salary WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);

        Salary deletedSalary = null;
        deletedSalary = this.getById(id);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedSalary = null;
        }
        return deletedSalary;
    }
}
