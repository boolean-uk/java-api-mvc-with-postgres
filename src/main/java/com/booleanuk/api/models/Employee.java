package com.booleanuk.api.models;

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
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public String toString(){
        String result = "";
        result += this.id + " : ";
        result+= this.name + " | ";
        result += this.department;
        return result;
    }
}
