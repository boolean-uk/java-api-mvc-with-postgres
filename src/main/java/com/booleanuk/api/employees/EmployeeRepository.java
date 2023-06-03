package com.booleanuk.api.employees;

import com.booleanuk.api.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    private MyConnection connection;

    public EmployeeRepository() throws SQLException {
        this.connection = new MyConnection();
    }
    public List<Employee> getAll() throws SQLException{
        List<Employee> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.getConnection().prepareStatement("SELECT * FROM Employees");
        ResultSet result = statement.executeQuery();
        while(result.next()){
            everyone.add(new Employee(result.getInt("id"), result.getString("name"),result.getString("job_name"),result.getString("salary_grade"),result.getString("department") ));
        }
        return everyone;
    }
    public Employee getEmployee(int id) throws SQLException{
        PreparedStatement statement = this.connection.getConnection().prepareStatement("SELECT * FROM Employees WHERE id=?");
        statement.setInt(1,id);
        ResultSet result = statement.executeQuery();
        Employee employee = null;
        if(result.next()){
            employee = new Employee(result.getInt("id"), result.getString("name"),result.getString("job_name"),result.getString("salary_grade"),result.getString("department") );
        }
        return employee;
    }
    public Employee updateEmployee(int id, Employee employee) throws SQLException {
        String SQL = "UPDATE Employees " +
                "SET name = ? ," +
                "job_name = ? ," +
                "salary_grade = ? ," +
                "department = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.getConnection().prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
        statement.setLong(5, id);
        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if (rowsAffected > 0) {
            updatedEmployee = this.getEmployee(id);
        }
        return updatedEmployee;
    }
    public Employee deleteEmployee(int id) throws SQLException {
        String SQL = "DELETE FROM Employees WHERE id = ?";
        PreparedStatement statement = this.connection.getConnection().prepareStatement(SQL);
        Employee deletedEmployee = null;
        deletedEmployee = this.getEmployee(id);
        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the employee we're deleting if we didn't delete them
            deletedEmployee = null;
        }
        return deletedEmployee;
    }
    public Employee addEmployee(Employee employee) throws SQLException {
        String SQL = "INSERT INTO Employees(name, job_name, salary_grade, department) VALUES(?, ?, ?, ?)";
        PreparedStatement statement = this.connection.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
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
                System.out.println("Oops: " + e);
            }
            employee.setId(newId);
        } else {
            employee = null;
        }
        return employee;
    }
}
