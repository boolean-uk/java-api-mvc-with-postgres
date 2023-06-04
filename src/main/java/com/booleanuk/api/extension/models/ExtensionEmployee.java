package com.booleanuk.api.extension.models;

public class ExtensionEmployee {
    private int id;
    private String name;
    private String job;
    private int salaryId;
    private int departmentId;

    public ExtensionEmployee() {}

    public ExtensionEmployee(int id, String name, String job, int salaryId, int departmentId) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.salaryId = salaryId;
        this.departmentId = departmentId;
    }

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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
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