package com.booleanuk.api;

public class Employee {
    private int id;
    private String name;
    private String jobName;
    private int salary_id;
    private int department_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepartmentId() {
        return department_id;
    }

    public void setDepartmentId(int departmentId) {
        this.department_id = departmentId;
    }

    public int getSalaryId() {
        return salary_id;
    }

    public void setSalaryId(int salaryId) {
        this.salary_id = salaryId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee (int id, String name, String jobName, int salaryId, int departmentId) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salary_id = salaryId;
        this.department_id = departmentId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", jobName='" + jobName + '\'' +
                ", salaryGrade='" + salary_id + '\'' +
                ", department='" + department_id + '\'' +
                '}';
    }
}
