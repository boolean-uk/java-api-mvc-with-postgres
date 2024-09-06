package com.booleanuk.api.employe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Employee {
    private int id;
    private String name;
    private String jobName;
    private String salaryGrade;
    private String department;

    public Employee(String name, String jobName, String salaries_id, String department_id) {
        this.name = name;
        this.jobName = jobName;
        this.salaryGrade = salaries_id;
        this.department = department_id;
    }

}
