package com.booleanuk.api.employee;

import javax.print.DocFlavor;

public class Employee {
    private long id;
    private String name;
    private String jobName;
    private int salaryID;
    private int departmentID;

    public Employee(long id, String name, String jobName, int salaryID, int departmentID) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salaryID = salaryID;
        this.departmentID = departmentID;
    }
    public String toString() {
        String result = "";
        result += this.id + " - ";
        result += this.name + " - ";
        result += this.jobName + " - ";
        result += this.salaryID + " - ";
        result += this.departmentID;
        return result;
    }
    public long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public String getJobName() {
        return jobName;
    }

    public int getSalaryID() {
        return salaryID;
    }

    public int getDepartmentID() {
        return departmentID;
    }
}
