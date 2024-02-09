package com.booleanuk.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {
    private String name;
    private String jobName;
    private String salaryGrade;
    private String department;
    public Employee(String name, String jobName, String salaryGrade, String department) {
        this.name = name;
        this.jobName = jobName;
        this.salaryGrade = salaryGrade;
        this.department = department;
    }
}
