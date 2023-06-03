package com.booleanuk.api.employees;

public class Employee {
    private int id, salaryId, departmentId;
    private String name,jobName;

    public Employee(int id, String name, String jobName, int salaryId, int departmentId) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salaryId = salaryId;
        this.departmentId = departmentId;
    }
    public Employee(){

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getJobName() {
        return jobName;
    }

    public int getSalaryId() {
        return salaryId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setId(int id) {
        this.id = id;
    }
}
