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

    public String toString() {
        String output = "";
        output += this.id + " - " + this.name + " - " + this.jobName + " - " + this.salaryGrade + " - " + this.department;
        return output;
    }
}
