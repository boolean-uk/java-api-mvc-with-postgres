package com.booleanuk.api.employee;

public class Employee {
    private int id;
    private String name;
    private String jobName;
    private String salaryGrade;
    private String department;

    public Employee(int id, String name, String jobName, String salaryGrade, String department) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salaryGrade = salaryGrade;
        this.department = department;
    }

    public String toString() {
        String result = this.id + " - ";
        result += this.name + " - ";
        result += this.jobName + " - ";
        result += this.salaryGrade + " - ";
        result += this.department + " - ";
        return result;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getJobName() {
        return this.jobName;
    }

    public String getSalaryGrade() {
        return this.salaryGrade;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setId(int id) {
        this.id = id;
    }
}
