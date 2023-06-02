package com.booleanuk.api.employee;

public class Employee {
    private long id;
    private String name;
    private String jobName;
    private String salarygrade;
    private String department;

    public Employee(long id, String name, String jobName, String salaryGrade, String department) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salarygrade = salaryGrade;
        this.department = department;
    }

    public String toString() {
        String result = "";
        result += this.id + " - ";
        result += this.name + " - ";
        result += this.jobName;
        result += this.salarygrade;
        result += this.department;
        return result;
    }
}
