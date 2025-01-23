package com.booleanuk.api.employee;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Employee {
    private int id;
    private String name;
    private String jobName;
    private String salaryGrade;
    private String department;

    public Employee(String name, String jobName, String salaryGrade, String department){
        this.name = name;
        this.jobName = jobName;
        this.salaryGrade = salaryGrade;
        this.department = department;
    }

}
