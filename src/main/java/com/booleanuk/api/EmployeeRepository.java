package com.booleanuk.api;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Employee> employeeRowMapper = (rs, rowNum) -> {
        return new Employee(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("job_name"),
                rs.getString("salary_grade"),
                rs.getString("department")
        );
    };

    public List<Employee> getAllEmployees() {
        String sql = "SELECT * FROM employees";
        return jdbcTemplate.query(sql, employeeRowMapper);
    }

    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO employees (name, job_name, salary_grade, department) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                employee.getName(),
                employee.getJobName(),
                employee.getSalaryGrade(),
                employee.getDepartment()
        );
    }
}
