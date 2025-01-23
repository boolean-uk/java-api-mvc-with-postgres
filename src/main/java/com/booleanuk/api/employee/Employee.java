package com.booleanuk.api.employee;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {
    private long id;
    private String name;
    private String jobName;
    private String salaryGrade;
    private String department;
}
