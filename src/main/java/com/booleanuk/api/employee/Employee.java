package com.booleanuk.api.employee;

public class Employee {
    private int id;
    private String name;
    private String jobName;
    private int salaryId;
    private int departmentId;

    public Employee(int id, String name, String jobName, int salaryId, int departmentId) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salaryId = salaryId;
        this.departmentId = departmentId;
    }

    public Employee(String name, String jobName, int salaryId, int departmentId) {
        this(0, name, jobName, salaryId, departmentId);
    }

    public Employee(){}



    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(int salaryId) {
        this.salaryId = salaryId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
