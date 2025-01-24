package com.booleanuk.api.employee;

import lombok.Getter;
import lombok.Setter;

public class Employee {
    @Setter
    @Getter
    private long id;
    @Getter
    private String name;
    @Getter
    private String jobName;
    @Getter
    private String salaryGrade;
    @Getter
    private String department;
    @Getter
    private long department_id;

    public Employee(long id, String name, String jobName, String salaryGrade, String department) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salaryGrade = salaryGrade;
        this.department = department;
    }

    public String toString() {
        String result = "";
        result += this.id + " - ";
        result += this.name + " - ";
        result += this.jobName;
        return result;
    }
}
