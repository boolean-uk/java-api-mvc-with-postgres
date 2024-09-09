package com.booleanuk.api;

public class Employee {
    private long id;
    private String name;
    private String jobName;
    private String salaryGrade;
    private String department;

    public Employee(long id, String name, String jobName, String salaryGrade, String department) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salaryGrade = salaryGrade;
        this.department = department;
    }

    // String representation of the employee
    @Override
    public String toString() {
        return this.id + " - " + this.name + " - " + this.jobName + " - " + this.salaryGrade + " - " + this.department;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getJobName() {
        return jobName;
    }

    public String getSalaryGrade() {
        return salaryGrade;
    }

    public String getDepartment() {
        return department;
    }

    // Setter for ID
    public void setId(long id) {
        this.id = id;
    }
}
