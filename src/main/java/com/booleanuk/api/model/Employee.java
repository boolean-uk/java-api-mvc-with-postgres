package com.booleanuk.api.model;

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

    public Employee(String name, String jobName, String salaryGrade, String department) {
        this.name = name;
        this.jobName = jobName;
        this.salaryGrade = salaryGrade;
        this.department = department;
    }

    public Employee() {

    }

    public long getId() {
        return id;
    }

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

    public void setId(long id) {
        this.id = id;
    }

}
