package com.booleanuk.api.repositories;

import com.booleanuk.api.models.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DepartmentRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DepartmentRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Department> getAllDepartments() {
        String sql = "SELECT * FROM department";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Department.class));
    }

    public Department getDepartment(int id) {
        String sql = "SELECT * FROM department WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Department.class), id);
    }

    public int addDepartment(Department department) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("department")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("department_name", department.getDepartmentName());
        parameters.put("department_location", department.getDepartmentLocation());

        return (int) simpleJdbcInsert.executeAndReturnKey(parameters);
    }

    public void updateDepartment(int id, Department department) {
        String sql = "UPDATE department SET department_name = ?, department_location = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                department.getDepartmentName(),
                department.getDepartmentLocation(),
                id
        );
    }

    public void deleteDepartment(int id) {
        String sql = "DELETE FROM department WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
