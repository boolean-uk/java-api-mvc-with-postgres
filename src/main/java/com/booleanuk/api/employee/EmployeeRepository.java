package com.booleanuk.api.employee;
import com.booleanuk.api.database.DatabaseConnection;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EmployeeRepository {
    Connection connection;

    public EmployeeRepository() throws SQLException {
        this.connection = new DatabaseConnection().getConnection();
    }

    public List<EmployeeDTO> getAll() throws SQLException {
        List<EmployeeDTO> allEmployees = new ArrayList<>();

        try (PreparedStatement statement = this.connection.prepareStatement(
                "SELECT employees.id, employees.name, employees.job_name, salaries.grade AS salary_grade, departments.name AS department " +
                        "FROM employees " +
                        "JOIN salaries ON employees.salary_grade_id = salaries.id " +
                        "JOIN departments ON employees.department_id = departments.id " ))
        {
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                EmployeeDTO employee = new EmployeeDTO(results.getLong("id"),
                        results.getString("name"),
                        results.getString("job_name"),
                        results.getString("salary_grade"),
                        results.getString("department"));
                allEmployees.add(employee);
            }
            return allEmployees;
        } catch (SQLException e) {
            String exceptionMessage = "An error occurred when getting all employees: " + e.getMessage();
            throw new SQLException(exceptionMessage, e);
        }
    }

    public EmployeeDTO get(long id) throws SQLException, ResponseStatusException {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "SELECT employees.id, employees.name, employees.job_name, salaries.grade AS salary_grade, departments.name AS department " +
                        "FROM employees " +
                        "JOIN salaries ON employees.salary_grade_id = salaries.id " +
                        "JOIN departments ON employees.department_id = departments.id " +
                        "WHERE employees.id = ?")) {
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        EmployeeDTO employee = null;

        if (results.next()){
            employee = new EmployeeDTO(results.getLong("id"),
                    results.getString("name"),
                    results.getString("job_name"),
                    results.getString("salary_grade"),
                    results.getString("department"));
        }

        if (employee == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with that id were found");
        }
        return employee;
        } catch (SQLException e) {
            String exceptionMessage = "An error occurred when getting an employee by id: " + e.getMessage();
            throw new SQLException(exceptionMessage, e);
        }
    }

    public EmployeeDTO update(long id, Employee employee) throws SQLException {
        String sqlString = "UPDATE employees " +
                "SET name = ? ," +
                "job_name = ? ," +
                "salary_grade_id = ? ," +
                "department_id = ? " +
                "WHERE id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sqlString)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getJobName());
            statement.setInt(3, employee.getSalaryGradeID());
            statement.setInt(4, employee.getDepartmentID());
            statement.setLong(5, id);

            statement.executeUpdate();

            return this.get(id);
        } catch (SQLException e){
            String exceptionMessage = "An error occurred when updating an employee: " + e.getMessage();
            throw new SQLException(exceptionMessage, e);
        }
    }

    public EmployeeDTO delete(long id) throws SQLException {
        String sqlString = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sqlString)) {
            EmployeeDTO deletedEmployee = this.get(id);

            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                deletedEmployee = null;
            }
            return deletedEmployee;
        } catch (SQLException e) {
            String exceptionString = "An error occurred when deleting an employee: " + e.getMessage();
            throw new SQLException(exceptionString, e);
        }
    }

    public EmployeeDTO add(Employee employee) throws SQLException {
        String sqlStatement = "INSERT INTO employees(name, job_name, salary_grade_id, department_id) VALUES(?, ?, ?, ?)";

        try (PreparedStatement statement = this.connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getJobName());
            statement.setInt(3, employee.getSalaryGradeID());
            statement.setInt(4, employee.getDepartmentID());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                String exceptionMessage = "Employee creation failed, no rows affected.";
                throw new SQLException(exceptionMessage);
            }

            long newID = getGeneratedKeys(statement);
            employee.setId(newID);

            return get(newID);

        } catch (SQLException e){
            String exceptionMessage = "An error occurred while attempting to add a department to the database: " + e.getMessage();
            throw new SQLException(exceptionMessage, e);
        }
    }

    private int getGeneratedKeys(Statement statement) throws SQLException {
        try (ResultSet rs = statement.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                String exceptionMessage = "An error occurred while getting the generated key: ";
                throw new SQLException(exceptionMessage);
            }
        }
    }
}
