package com.booleanuk.api.employee;

public class Employee {
    private int id;
    private String name;
    private String jobName;
    private int salaryGradeId;
    private int departmentId;

    public Employee(int id, String name, String jobName, int salaryGradeId, int departmentId) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salaryGradeId = salaryGradeId;
        this.departmentId = departmentId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setSalaryGradeId(int salaryGradeId) {
        this.salaryGradeId = salaryGradeId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
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

    public int getSalaryGradeId() {
        return salaryGradeId;
    }

    public int getDepartmentId() {
        return departmentId;
    }
}
