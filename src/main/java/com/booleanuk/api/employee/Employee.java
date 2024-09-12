package com.booleanuk.api.employee;

public class Employee {
    private int id;
    private String name;
    private String jobName;
    private int department_id;
    private int salary_id;

    public Employee(int id, String name, String jobName, int department_id, int salary_id) {
        this.id = id;
        this.name = name;
        this.jobName = jobName;
        this.department_id = department_id;
        this.salary_id = salary_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public int getSalary_id() {
        return salary_id;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getJobName() {
        return this.jobName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        String output = "";
        output += this.id + " - " + this.name + " - " + this.jobName + " - " + this.department_id + " - " + this.salary_id;
        return output;
    }
}
