package com.booleanuk.api.employee.model.repository;

import com.booleanuk.api.employee.model.pojo.Employee;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private final DataSource dataSource;

    public EmployeeRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                employees.add(new Employee(rs));
            }
        }
        return employees;
    }


    public Employee getEmployeeById(int id) throws SQLException {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Employee(rs);
            }
        }
        return null;
    }


    public Employee insertEmployee(Employee e) throws SQLException {
        String sql = "INSERT INTO employees(name, jobname, salarygrade, department) VALUES(?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getName());
            ps.setString(2, e.getJobName());
            ps.setString(3, e.getSalaryGrade());
            ps.setString(4, e.getDepartment());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) e.setId(keys.getInt(1));
                }
                return e;
            }
        }
        return null;
    }


    public Employee updateEmployee(int id, Employee e) throws SQLException {
        String sql = "UPDATE employees SET name = ?, jobname = ?, salarygrade = ?, department = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getName());
            ps.setString(2, e.getJobName());
            ps.setString(3, e.getSalaryGrade());
            ps.setString(4, e.getDepartment());
            ps.setInt(5, id);
            int rows = ps.executeUpdate();
            if (rows > 0) return getEmployeeById(id);
        }
        return null;
    }


    public Employee deleteEmployee(int id) throws SQLException {
        Employee existing = getEmployeeById(id);
        if (existing == null) return null;
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) return existing;
        }
        return null;
    }

    public void testConnection() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("✅ Connected to: " + conn.getMetaData().getURL());
            System.out.println("User: " + conn.getMetaData().getUserName());
        }
    }
}
