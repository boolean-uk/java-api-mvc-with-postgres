package com.booleanuk.api.employee;

public class Employee {
    private int id;
    private String name;
    private String jobName;
    private int salaryID;
    private int departmentID;

    public Employee(int id, String name, String jobName, int salaryID, int departmentID) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salaryID = salaryID;
        this.departmentID = departmentID;
    }

    public String toString() {
        String result = this.id + " - ";
        result += this.name + " - ";
        result += this.jobName + " - ";
        result += this.salaryID + " - ";
        result += this.departmentID + " - ";
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

    public int getSalaryID() {
        return this.salaryID;
    }

    public int getDepartmentID() {
        return this.departmentID;
    }

    public void setId(int id) {
        this.id = id;
    }
}
