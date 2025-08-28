package com.booleanuk.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeDto {
    private String name;
    private String jobName;
    private String salaryGrade;
    private String department;
}
