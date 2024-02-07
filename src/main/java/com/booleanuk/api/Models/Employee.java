package com.booleanuk.api.Models;

public class Employee {
    private long id;
    private String name;
    private String jobName;
    private String salary_id;
    private String department_id;

    public Employee(long id, String name, String jobName, String salary_id, String department_id) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salary_id = salary_id;
        this.department_id = department_id;
    }

    public String toString() {
        String result = "";
        result += this.id + " - ";
        result += this.name + " - ";
        result += this.jobName;
        return result;
    }


    public String getName() {
        return name;
    }

    public String getJobName() {
        return jobName;
    }

    public String getSalaryGrade() {
        return salary_id;
    }

    public String getDepartment() {
        return department_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
