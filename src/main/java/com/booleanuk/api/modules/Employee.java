package com.booleanuk.api.modules;

public class Employee {
    private int id;
    public String name;
    public String jobName;
    public String salaryGrade;
    public String department;

    public Employee() {}

    public Employee(final String name, final String jobName, final String salaryGrade, final String department) {
        this.name = name;
        this.jobName = jobName;
        this.salaryGrade = salaryGrade;
        this.department = department;
    }

    public Employee(final int id, final String name, final String jobName, final String salaryGrade, final String department) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salaryGrade = salaryGrade;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int newId) {
        id = newId;
    }
}
