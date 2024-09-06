package com.booleanuk.api.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeDTO {
    private long id;
    private String name;
    private String jobName;
    private String salaryGrade;
    private String department;
}
