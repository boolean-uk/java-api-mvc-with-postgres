package com.booleanuk.api.employee;

import com.booleanuk.api.setup.DatabaseConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

    private final Connection connection;


    @Autowired
    public EmployeeRepository(DatabaseConnector dbConnector){
        this.connection = dbConnector.getConnection();
    }

    public Employee add(Employee employee) throws SQLException {
        String SQL = "INSERT INTO Employee (name, job_name, salary_grade, department) VALUES (?,?,?,?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());

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
            employee.setId(newId);
        } else {
            employee = null;
        }

        return employee;
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();

        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employee");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            Employee current = new Employee(resultSet.getInt("id"),
                    resultSet.getString("name"), resultSet.getString("job_name"),
                    resultSet.getString("salary_grade"),
                    resultSet.getString("department")
            );

            employees.add(current);
        }
        return employees;
    }

    public Employee getById(int id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Employee WHERE id=?");
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();
        Employee employee = null;
        if(resultSet.next()){
            employee = new Employee(resultSet.getInt("id"),
                    resultSet.getString("name"), resultSet.getString("job_name"),
                    resultSet.getString("salary_grade"),
                    resultSet.getString("department")
            );
        }

        return employee;
    }

    public Employee update(int id, Employee employee) throws SQLException {
        String SQL = "UPDATE Employee " +
                "SET name = ? ," +
                "job_name = ? ," +
                "salary_grade = ? ," +
                "department = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
        statement.setLong(5, id);
        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if (rowsAffected > 0) {
            updatedEmployee = this.getById(id);
        }
        return updatedEmployee;
    }

    public Employee delete(int id) throws SQLException {
        String SQL = "DELETE FROM Employee WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);

        Employee deletedEmployee = null;
        deletedEmployee = this.getById(id);

        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            deletedEmployee = null;
        }
        return deletedEmployee;
    }
}
