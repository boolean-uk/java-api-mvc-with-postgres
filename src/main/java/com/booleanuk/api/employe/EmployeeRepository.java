package com.booleanuk.api.employe;

import com.booleanuk.api.ConnectToDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    ConnectToDatabase connectToDatabase;

    public EmployeeRepository() throws SQLException {
        connectToDatabase = new ConnectToDatabase();
    }


    public List<Employee> getAll () throws SQLException {
        List<Employee> everyOne = new ArrayList<>();

        String query = "SELECT * " +
                "FROM Employee e " +
                "JOIN Salaries s ON e.salaries_id = s.id " +
                "JOIN Departments d ON e.department_id = d.id";

        PreparedStatement statement = this.connectToDatabase.getConnection().prepareStatement(query);
        ResultSet results = statement.executeQuery();
        while(results.next()) {
            Employee employee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("grade"),
                    results.getString("department_name"));
            everyOne.add(employee);
        }
        return everyOne;
    }

    public Employee getOne (int id)  throws SQLException {
        String query = "SELECT *" +
                "FROM Employee e " +
                "JOIN Salaries s ON e.salaries_id = s.id " +
                "JOIN Departments d ON e.department_id = d.id " +
                "WHERE e.id= ?";
        PreparedStatement statement = this.connectToDatabase.getConnection().prepareStatement(query);
        statement.setInt(1, id);

        ResultSet results = statement.executeQuery();
        Employee employee = null;
        if(results.next()) {
            employee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("salaries_id"),
                    results.getString("department_id"));
            return employee;
        }
        return employee;
    }

    public Employee update(int id, Employee employee) throws SQLException {
        String SQL = "UPDATE Employee e " +
                "SET name = ? ," +
                "jobName = ? ," +
                "salaries_id = ? ," +
                "department_id = ? " +
                "WHERE id = ? ";
        PreparedStatement statement = this.connectToDatabase.getConnection().prepareStatement(SQL);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
        statement.setInt(5, id);

        // Update the Departments table

        String updateDepartmentSQL = "UPDATE Departments " +
                "SET department_name = ? " +
                "WHERE id = ?";
        PreparedStatement updateDepartmentStmt = this.connectToDatabase.getConnection().prepareStatement(updateDepartmentSQL);
        updateDepartmentStmt.setString(1, employee.getDepartment());
        updateDepartmentStmt.setInt(2,id);

        updateDepartmentStmt.executeUpdate();

        // Update the Salaries table
        String updateSalariesSQL = "UPDATE Salaries " +
                "SET grade = ? " +
                "WHERE id = ?";
        PreparedStatement updateSalariesStmt = this.connectToDatabase.getConnection().prepareStatement(updateSalariesSQL);
        updateSalariesStmt.setString(1, employee.getSalaryGrade());
        updateSalariesStmt.setInt(2,id);

        updateSalariesStmt.executeUpdate();

        int rowsAffected = statement.executeUpdate();
        Employee updatedEmployee = null;
        if(rowsAffected > 0) {
            updatedEmployee = this.getOne(id);
        }
        return updatedEmployee;
    }

    public Employee delete(int id) throws SQLException{
        String SQL = "DELETE FROM Employee WHERE id=?";
        PreparedStatement statement = this.connectToDatabase.getConnection().prepareStatement(SQL);
        statement.setInt(1,id);
        Employee deletedEmployee = null;
        deletedEmployee = this.getOne(id);
        int rowsAffected = statement.executeUpdate();
        if(rowsAffected == 0 ) {
            deletedEmployee = null;
        }
        return deletedEmployee;
    }

    public Employee add(Employee employee) throws SQLException {
        String SQL = "INSERT INTO Employee (name, jobName, salaries_id, department_id) VALUES (?,?,?,?)";
        PreparedStatement statement = this.connectToDatabase.getConnection().prepareStatement(SQL,
                Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getJobName());
        statement.setString(3, employee.getSalaryGrade());
        statement.setString(4, employee.getDepartment());
        int rowsAffected = statement.executeUpdate();
        int newID = 0;
        if(rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if(rs.next()) {
                    newID = rs.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            employee.setId(newID);
        } else {
            employee = null;
        }
        return employee;


    }
}
