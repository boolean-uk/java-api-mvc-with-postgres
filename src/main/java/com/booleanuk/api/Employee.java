package com.booleanuk.api;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
    private long id;
    private String name;
    private String jobName;
    private String salaryGrade;
    private String department;


}
