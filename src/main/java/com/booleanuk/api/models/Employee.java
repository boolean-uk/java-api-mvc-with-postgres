package com.booleanuk.api.models;

public class Employee {
    //region // FIELDS //
    private long id;
    private String name;
    private String jobName;
    private String salaryGrade;
    private String department;
    //endregion

    //region // PROPERTIES //
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getJobName() {
        return jobName;
    }

    public String getSalaryGrade() {
        return salaryGrade;
    }

    public String getDepartment() {
        return department;
    }
    //endregion

    public Employee(long id, String name, String jobName, String salaryGrade, String department) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.salaryGrade = salaryGrade;
        this.department = department;
    }

    public String toString() {
        String result = "";
        result += this.id + " - ";
        result += this.name + " - ";
        result += this.jobName;
        return result;
    }
}
