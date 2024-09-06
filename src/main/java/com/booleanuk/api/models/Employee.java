package com.booleanuk.api.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {
    private long id;
    private String name;
    private String jobName;
    private int salary_id;
    private int department_id;

    public Employee(long id, String name, String jobName, int salary_id, int department_id) {
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
        result += this.jobName + " - ";
        return result;
    }

}
