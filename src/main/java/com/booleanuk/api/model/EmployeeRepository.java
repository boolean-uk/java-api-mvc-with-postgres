package com.booleanuk.api.model;


import com.booleanuk.api.config.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    private DBConnection connection;

    public EmployeeRepository() {
        try {
            this.connection = new DBConnection();
        } catch (SQLException sqlException) {
            System.out.println("Failed to connect to database: " + sqlException);
        }
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> allEmployees = new ArrayList<>();

        PreparedStatement statement = query("SELECT * FROM EMPLOYEE");
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Employee employee = new Employee(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("jobName"),
                    results.getString("salaryGrade"),
                    results.getString("department")
            );
            allEmployees.add(employee);
        }
        return allEmployees;
    }

    private PreparedStatement query(String sqlQuery) {
        PreparedStatement statement = null;
        try {
            statement = this.connection.getDbConnection().prepareStatement(sqlQuery);
            return statement;
        }
        catch (SQLException sqlException) {
            System.out.println("Failed to execute sql query: " + sqlException);
        }
        return statement;
    }
}
