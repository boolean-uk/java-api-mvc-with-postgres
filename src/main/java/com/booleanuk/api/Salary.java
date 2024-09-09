package com.booleanuk.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Salary {
    private String grade;
    private int minSalary;
    private int maxSalary;

    public Salary(String grade, int minSalary, int maxSalary) {
        this.grade = grade;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }
}
