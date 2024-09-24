package com.booleanuk.Employee;

import lombok.Getter;
import lombok.Setter;

public class Employee {
    @Setter
    @Getter
    private long id;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String jobName;
    @Setter
    @Getter
    private String salaryGrade;
    @Setter
    @Getter
    private String department;

    public Employee(long id, String name, String jobName, String salaryGrade, String department) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salaryGrade = salaryGrade;
        this.department = department;
    }

    @Override
    public String toString() {
        return id + " - " + name + " - " + jobName + " - " + salaryGrade + " - " + department;
    }
}
