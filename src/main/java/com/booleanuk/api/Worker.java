package com.booleanuk.api;

public class Worker {
    private long id;
    private String name;
    private String jobName;
    private String salaryGrade;
    private String department;

    public Worker(long id, String name, String jobname, String salaryGrade, String department) {
        this.id = id;
        this.name = name;
        this.jobName = jobname;
        this.salaryGrade = salaryGrade;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getDepartment() {
        return department;
    }

    public String getJobName() {
        return jobName;
    }

    public String getSalaryGrade() {
        return salaryGrade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setSalaryGrade(String salaryGrade) {
        this.salaryGrade = salaryGrade;
    }
}
