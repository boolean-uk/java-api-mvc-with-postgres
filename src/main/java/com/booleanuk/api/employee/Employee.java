package com.booleanuk.api.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Employee {
    private long id;
    private String name;
    private String jobName;
    private int salaryGradeID;
    private int departmentID;
}
