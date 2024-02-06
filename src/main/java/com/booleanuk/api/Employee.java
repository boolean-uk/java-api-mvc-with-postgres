package com.booleanuk.api;

public class Employee {
    private long id;
    private String name;
    private String jobName;
    private String salaryGrade;
    private String department;

    public Employee(long id, String name, String address, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.jobName = address;
        this.salaryGrade = email;
        this.department = phoneNumber;
    }

    public String toString() {
        String result = "";
        result += this.id + " - ";
        result += this.name + " - ";
        result += this.jobName;
        return result;
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
