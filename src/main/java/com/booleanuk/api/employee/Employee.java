package com.booleanuk.api.employee;

public class Employee {
    private int id;
    private String name;
    private String jobName;
    private int salary_id;
    private int department_id;

    public Employee(int id, String name, String jobName, int salary_id, int department_id){
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salary_id = salary_id;
        this.department_id = department_id;
    }

    public Employee(String name, String jobName, int salary_id, int department_id){
        this.name = name;
        this.jobName = jobName;
        this.salary_id = salary_id;
        this.department_id = department_id;
    }

    public Employee(int id){
        this.id = id;
    }

    public Employee(){

    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public void setSalary_id(int salary_id) {
        this.salary_id = salary_id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSalary_id() {
        return salary_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public String toString() {
        String result = "";
        result += this.id + " - ";
        result += this.name + " - ";
        result += this.jobName + " - ";
        result += this.salary_id + " - ";
        result += this.department_id;
        return result;
    }
}
