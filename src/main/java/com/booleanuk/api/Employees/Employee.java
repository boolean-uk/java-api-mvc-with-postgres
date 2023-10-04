package com.booleanuk.api.Employees;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee {
    private int id;
    private String name;
    private String jobName;
    private String salaryGrade;
    private String department;
}
