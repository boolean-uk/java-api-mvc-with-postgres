package com.booleanuk.api.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@AllArgsConstructor
public class Employee {
    private long id;
    private String name;
    private String jobName;
    private String salaryGrade;
    private String department;

    public Employee(ResultSet rs) throws SQLException {
        setId(rs.getLong("id"));
        setName(rs.getString("name"));
        setJobName(rs.getString("job_name"));
        setSalaryGrade(rs.getString("salary_grade"));
        setDepartment(rs.getString("department"));
    }

    public Employee(String name, String jobName, String salaryGrade, String department) {
        setName(name);
        setJobName(jobName);
        setSalaryGrade(salaryGrade);
        setDepartment(department);
    }

}
