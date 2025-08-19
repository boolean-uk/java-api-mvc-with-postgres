package com.booleanuk.api.model;

public class Employee {
    private int id;
    private String name;
    private String jobName;
    private int salary_id;
    private int department_id;

    public Employee(int id, String name, String jobName, int salary_id, int department_id) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salary_id = salary_id;
        this.department_id = department_id;
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

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getSalary_id() {
        return salary_id;
    }

    public void setSalary_id(int salary_id) {
        this.salary_id = salary_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", jobName='" + jobName + '\'' +
                ", salary_id=" + salary_id +
                ", department_id=" + department_id +
                '}';
    }
}