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
        result += this.jobName + " - ";
        result += this.salarygrade + " - ";
        result += this.department + " - ";
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getSalarygrade() {
        return salarygrade;
    }

    public void setSalarygrade(String salarygrade) {
        this.salarygrade = salarygrade;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
