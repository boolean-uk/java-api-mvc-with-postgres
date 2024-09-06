package com.booleanuk.api.salaries;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Salaries {

    private int id;
    private String grade;
    private String minSalary;
    private String maxSalary;

    public Salaries(String grade, String minSalary, String maxSalary) {
        this.grade = grade;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

}
