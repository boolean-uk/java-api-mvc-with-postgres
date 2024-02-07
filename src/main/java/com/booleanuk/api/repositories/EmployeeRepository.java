package com.booleanuk.api.repositories;

import com.booleanuk.api.models.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Employee> getAllEmployees() {
        String sql = "SELECT * FROM employee";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employee.class));
    }

    public Employee getEmployee(int id) {
        String sql = "SELECT * FROM employee WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Employee.class), id);
    }

    public int addEmployee(Employee employee) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("employee")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", employee.getName());
        parameters.put("job_name", employee.getJobName());
        parameters.put("salary_grade", employee.getSalaryGrade());
        parameters.put("department_id", employee.getDepartmentId());

        return (int) simpleJdbcInsert.executeAndReturnKey(parameters);
    }

    public void updateEmployee(int id, Employee employee) {
        String sql = "UPDATE employee SET name = ?, job_name = ?, salary_grade = ?, department_id = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                employee.getName(),
                employee.getJobName(),
                employee.getSalaryGrade(),
                employee.getDepartmentId(),
                id
        );
    }

    public void deleteEmployee(int id) {
        String sql = "DELETE FROM employee WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
