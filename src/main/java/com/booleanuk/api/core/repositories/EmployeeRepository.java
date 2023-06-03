package com.booleanuk.api.core.repositories;

import com.booleanuk.api.core.models.Employee;
import com.booleanuk.api.database.Database;
import com.booleanuk.api.database.PostgresDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    public Database db = new PostgresDatabase();

    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = db.connection()) {
            String selectAll = "SELECT * FROM employees";
            PreparedStatement statement = connection.prepareStatement(selectAll);
            ResultSet results = statement.executeQuery();

            while(results.next()) {
                employees.add(
                        new Employee(
                                results.getInt("id"),
                                results.getString("name"),
                                results.getString("job"),
                                results.getString("salary_grade"),
                                results.getString("department")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return employees;
    }

    public Employee get(int id) {
        Employee employee = null;

        try (Connection connection = db.connection()) {
            String selectId = "SELECT * FROM Employees WHERE Employees.id = ?";
            PreparedStatement statement = connection.prepareStatement(selectId);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

            while(results.next()) {
                employee = new Employee(
                        results.getInt("id"),
                        results.getString("name"),
                        results.getString("job"),
                        results.getString("salary_grade"),
                        results.getString("department")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return employee;
    }

    public Employee update(int id, Employee employee) {
        Employee requestedEmployee = null;

        try (Connection connection = db.connection()) {
            String updateId = "UPDATE Employees " +
                    "SET name=?, job=?, salary_grade=?, department=? " +
                    "WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(updateId);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getJob());
            statement.setString(3, employee.getSalaryGrade());
            statement.setString(4, employee.getDepartment());
            statement.setInt(5, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0)
                requestedEmployee = get(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return requestedEmployee;
    }

    public Employee add(Employee employee) {
        Employee newEmployee = null;

        try (Connection connection = db.connection()) {
            String addId = "INSERT INTO Employees (name, job, salary_grade, department) " +
                    "values (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(addId, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getJob());
            statement.setString(3, employee.getSalaryGrade());
            statement.setString(4, employee.getDepartment());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet results = statement.getGeneratedKeys()) {
                    if (results.next()) {
                        int id = results.getInt(1);
                        newEmployee = get(id);
                    }
                } catch (SQLException e) {
                    System.out.println("Error getting the generated key" + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return newEmployee;
    }

    public Employee delete(int id) {
        Employee employee = get(id);

        try (Connection connection = db.connection()) {
            String deleteId = "DELETE FROM Employees WHERE Employees.id = ?";
            PreparedStatement statement = connection.prepareStatement(deleteId);
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0)
                employee = null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return employee;
    }
}
