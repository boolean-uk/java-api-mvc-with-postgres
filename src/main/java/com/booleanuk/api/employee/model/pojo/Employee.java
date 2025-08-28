package com.booleanuk.api.employee.model.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private int id;
    private String name;
    private String jobName;
    private String salaryGrade;
    private String department;

    public Employee(ResultSet results) throws SQLException {
        setId(results.getInt("id"));
        setName(results.getString("name"));
        setJobName(results.getString("jobname"));
        setSalaryGrade(results.getString("salarygrade"));
        setDepartment(results.getString("department"));

    }

}


