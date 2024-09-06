package com.booleanuk.api.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Salary {
    private long id;
    private String grade;
    private int minSalary;
    private int maxSalary;

    public Salary(long id, String grade, int minSalary, int maxSalary) {
        this.id = id;
        this.grade = grade;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    public String toString() {
        String result = "";
        result += this.id + " - ";
        result += this.grade + " - ";
        return result;
    }

}
