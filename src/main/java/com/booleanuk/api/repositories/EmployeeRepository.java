package com.booleanuk.api.repositories;

import com.booleanuk.api.modules.Employee;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeRepository {
    String dbURL;
    String dbUser;
    String dbPassword;
    String dbDatabase;
    Connection connection;

    public EmployeeRepository() throws SQLException {
        getDatabaseCredentials();
        connection = DriverManager.getConnection(dbURL + dbDatabase + "?user=" + dbUser + "&password=" + dbPassword);
    }

    private void getDatabaseCredentials() {
        try (InputStream inputStream = new FileInputStream("src/main/resources/config.properties")) {
            Properties _properties = new Properties();

            _properties.load(inputStream);

            dbURL = _properties.getProperty("db.url");
            dbUser = _properties.getProperty("db.user");
            dbPassword = _properties.getProperty("db.password");
            dbDatabase = _properties.getProperty("db.database");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Employee create(Employee employee) throws SQLException {
        PreparedStatement _preparedStatement = connection.prepareStatement("INSERT INTO Employee (name, job_name, salary_grade, department) VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        _preparedStatement.setString(1, employee.name);
        _preparedStatement.setString(2, employee.jobName);
        _preparedStatement.setString(3, employee.salaryGrade);
        _preparedStatement.setString(4, employee.department);

        final int _rowsAffected = _preparedStatement.executeUpdate();
        int _newId = 0;

        if (_rowsAffected > 0) {
            try (ResultSet resultSet = _preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) _newId = resultSet.getInt(1);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

            employee.setId(_newId);
        }
        else
            employee = null;

        return employee;
    }

    public List<Employee> getAll() throws SQLException {
        final List<Employee> _allEmployees = new ArrayList<>();
        ResultSet _resultSet = connection.prepareStatement("SELECT * FROM Employee").executeQuery();

        while (_resultSet.next())
            _allEmployees.add(new Employee(_resultSet.getInt("id"), _resultSet.getString("name"), _resultSet.getString("job_name"), _resultSet.getString("salary_grade"), _resultSet.getString("department")));

        return _allEmployees;
    }

    public Employee get(int id) throws SQLException {
        PreparedStatement _preparedStatement = connection.prepareStatement("SELECT * FROM Employee WHERE id = ?");
        _preparedStatement.setLong(1, id);

        ResultSet _resultSet = _preparedStatement.executeQuery();

        return _resultSet.next() ? new Employee(_resultSet.getInt("id"), _resultSet.getString("name"), _resultSet.getString("job_name"), _resultSet.getString("salary_grade"), _resultSet.getString("department")) : null;
    }

    public Employee update(int id, Employee employee) throws SQLException {
        PreparedStatement _preparedStatement = connection.prepareStatement("UPDATE Employee SET name = ?, job_name = ?, salary_grade = ?, department = ? WHERE id = ?");
        _preparedStatement.setString(1, employee.name);
        _preparedStatement.setString(2, employee.jobName);
        _preparedStatement.setString(3, employee.salaryGrade);
        _preparedStatement.setString(4, employee.department);
        _preparedStatement.setInt(5, id);

        return _preparedStatement.executeUpdate() > 0 ? get(id) : null;
    }

    public Employee delete(int id) throws SQLException{
        PreparedStatement _preparedStatement = connection.prepareStatement("DELETE FROM Employee WHERE id = ?");
        Employee _employee;
        _employee = get(id);

        _preparedStatement.setLong(1, id);
        return _preparedStatement.executeUpdate() != 0 ? _employee : null;
    }
}
