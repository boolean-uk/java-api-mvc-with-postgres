package com.booleanuk.api;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Employee> getAllEmployees() {
        String sql = "SELECT * FROM employees";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Employee(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("job_name"),
                rs.getString("salary_grade"),
                rs.getString("department")
        ));
    }

    public Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                    new Employee(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("job_name"),
                            rs.getString("salary_grade"),
                            rs.getString("department")
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO employees (name, job_name, salary_grade, department) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                employee.getName(),
                employee.getJobName(),
                employee.getSalaryGrade(),
                employee.getDepartment());
    }

    public void updateEmployee(int id, Employee employee) {
        String sql = "UPDATE employees SET name = ?, job_name = ?, salary_grade = ?, department = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                employee.getName(),
                employee.getJobName(),
                employee.getSalaryGrade(),
                employee.getDepartment(),
                id);
    }

    public void deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
