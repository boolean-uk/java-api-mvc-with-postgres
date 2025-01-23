package com.booleanuk.api.employee;


public class Employee {
    private Integer id;
    String name;
    String jobName;
    int salaryID;
    int departmentID;

    public Employee(int id, String name, String jobName, int salaryID, int departmentID){
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salaryID = salaryID;
        this.departmentID = departmentID;
    }
    public Employee(String name, String jobName, int salaryID, int departmentID){
        this.name = name;
        this.jobName = jobName;
        this.salaryID = salaryID;
        this.departmentID = departmentID;
    }

    public Employee(){}

    public Employee(int id){
        this.id = id;
    }

    public String toString(){
        String result = "";
        result += this.id + " - ";
        result += this.name  + " - ";
        result += this.jobName  + " - ";
        result += this.salaryID  + " - ";
        result += this.departmentID;

        return result;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
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

    public Integer getSalaryID() {
        return salaryID;
    }

    public void setSalaryID(int salaryID) {
        this.salaryID = salaryID;
    }

    public Integer getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }
}
