package com.booleanuk.api.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private long id;
    private String name;
    private String jobName;
    private String salaryGrade;  // Matches the salaryGrade field in your table
    private String department;    // Maps to department_id in your table
}
