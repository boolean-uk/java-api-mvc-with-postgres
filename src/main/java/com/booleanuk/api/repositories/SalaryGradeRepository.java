package com.booleanuk.api.repositories;

import com.booleanuk.api.models.SalaryGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SalaryGradeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SalaryGradeRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<SalaryGrade> getAllSalaryGrades() {
        String sql = "SELECT * FROM salary";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SalaryGrade.class));
    }

    public SalaryGrade getSalaryGrade(int grade) {
        String sql = "SELECT * FROM salary WHERE grade = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(SalaryGrade.class), grade);
    }

    public int addSalaryGrade(SalaryGrade salary) {
        String sql = "INSERT INTO salary (grade, min_salary, max_salary) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                salary.getGrade(),
                salary.getMinSalary(),
                salary.getMaxSalary()
        );

        return salary.getGrade();
    }

    public void updateSalaryGrade(int grade, SalaryGrade salary) {
        String sql = "UPDATE salary SET grade = ?, min_salary = ?, max_salary = ? WHERE grade = ?";
        jdbcTemplate.update(sql,
                salary.getGrade(),
                salary.getMinSalary(),
                salary.getMaxSalary(),
                grade
        );
    }

    public void deleteSalaryGrade(int grade) {
        String sql = "DELETE FROM salary WHERE grade = ?";
        jdbcTemplate.update(sql, grade);
    }
}
