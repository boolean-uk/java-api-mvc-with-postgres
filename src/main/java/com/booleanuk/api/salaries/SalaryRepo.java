package com.booleanuk.api.salaries;

import com.booleanuk.api.employee.Employee;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class SalaryRepo {
    private final Connection connection;

    public SalaryRepo(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    public ArrayList<Salary> getAllData() throws SQLException {
        ArrayList<Salary> salaries = new ArrayList<>();
        try (
                PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM salaries");
                ResultSet results = statement.executeQuery();
        ) {
            while (results.next()) {
                Salary salary = new Salary(
                        results.getInt("id"),
                        results.getString("grade"),
                        results.getInt("minSalary"),
                        results.getInt("maxSalary")
                );
                salaries.add(salary);
            }
        }
        return salaries;
    }

    public Salary getOne(int id) throws SQLException {
        Salary salary = null;
        String sql = "SELECT * FROM salaries WHERE id = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    salary = new Salary(
                            results.getInt("id"),
                            results.getString("grade"),
                            results.getInt("minSalary"),
                            results.getInt("maxSalary")
                    );
                }
            }
        }
        return salary;
    }

    public Salary add(Salary salary) throws SQLException {
        String sql = "INSERT INTO salaries (grade, minSalary, masSalary) VALUES (?, ?) RETURNING id";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, salary.getGrade());
            statement.setInt(2, salary.getMinSalary());
            statement.setInt(3, salary.getMaxSalary());
            try (ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    int generateId = results.getInt(1);
                    salary.setId(generateId);
                }
            }
        }
        return salary;
    }

    public Salary update(int id, Salary salary) throws SQLException {
        String sql = "UPDATE salaries SET grade = ?, minSalary = ?, masSalary = ? WHERE id = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, salary.getGrade());
            statement.setInt(2, salary.getMinSalary());
            statement.setInt(3, salary.getMaxSalary());
            statement.setInt(4, id);
            statement.executeUpdate();

            return getOne(salary.getId());
        }
    }

    public Salary delete (int id) throws SQLException {
        Salary salary = getOne(id);

        if (salary == null) {
            return null;
        }
        String sql = "DELETE FROM employees WHERE id = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();

            return (affectedRows > 0) ? salary : null;
        }
    }

}
