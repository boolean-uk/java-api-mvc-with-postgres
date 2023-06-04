package com.booleanuk.api.extension.repositories;

import com.booleanuk.api.core.models.Employee;
import com.booleanuk.api.extension.models.ExtensionEmployee;
import com.booleanuk.api.database.Database;
import com.booleanuk.api.database.PostgresDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    private final Database db = new PostgresDatabase();

    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = db.connection()) {
            String selectAll = "SELECT e.id as id, e.name as name, e.job as job, s.grade as salary_grade, d.name as department " +
                    "FROM extension_employees e " +
                    "JOIN salaries s ON s.id = e.salary_id " +
                    "JOIN departments d ON d.id = e.department_id";
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
            String selectId = "SELECT e.id as id, e.name as name, e.job as job, s.grade as salary_grade, d.name as department " +
                    "FROM extension_employees e " +
                    "JOIN salaries s ON s.id = e.salary_id " +
                    "JOIN departments d ON d.id = e.department_id " +
                    "WHERE e.id = ?";
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

    public Employee update(int id, ExtensionEmployee employee) throws SQLException {
        Employee requestedEmployee = null;

        try (Connection connection = db.connection()) {
            String updateId = "UPDATE extension_employees " +
                    "SET name=?, job=?, salary_id=?, department_id=? " +
                    "WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(updateId);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getJob());
            statement.setInt(3, employee.getSalaryId());
            statement.setInt(4, employee.getDepartmentId());
            statement.setInt(5, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0)
                requestedEmployee = get(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Error on employees update method: " + e.getMessage());
        }

        return requestedEmployee;
    }

    public Employee add(ExtensionEmployee employee) throws SQLException {
        Employee newEmployee = null;

        try (Connection connection = db.connection()) {
            String addId = "INSERT INTO extension_employees (name, job, salary_id, department_id) " +
                    "values (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(addId, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getJob());
            statement.setInt(3, employee.getSalaryId());
            statement.setInt(4, employee.getDepartmentId());

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
            throw new SQLException("Error on employees add method: " + e.getMessage());
        }

        return newEmployee;
    }

    public Employee delete(int id) {
        Employee employee = get(id);

        try (Connection connection = db.connection()) {
            String deleteId = "DELETE FROM extension_employees WHERE id = ?";
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
