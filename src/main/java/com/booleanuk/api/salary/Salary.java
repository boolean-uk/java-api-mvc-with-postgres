package com.booleanuk.api.salary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Salary {
    private int id;
    private String grade;
    private int minSalary;
    private int maxSalary;
}
