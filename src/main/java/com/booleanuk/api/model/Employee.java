
package com.booleanuk.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@AllArgsConstructor
public class Employee {
    private int id;
    private String name;
    private String jobName;
    private String salaryGrade;
    private String department;

    public Employee(ResultSet resultset) throws SQLException {
        setId(resultset.getInt("id"));
        setName(resultset.getString("name"));
        setJobName(resultset.getString("jobName"));
        setSalaryGrade(resultset.getString("salaryGrade"));
        setDepartment(resultset.getString("department"));



    }
}
