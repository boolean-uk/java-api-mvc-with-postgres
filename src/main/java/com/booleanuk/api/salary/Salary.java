package com.booleanuk.api.salary;

public class Salary {
    private int id;
    private String salary_grade;

    public Salary(int id, String salary_grade) {
        this.id = id;
        this.salary_grade = salary_grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSalary_grade() {
        return salary_grade;
    }

    public void setSalary_grade(String salary_grade) {
        this.salary_grade = salary_grade;
    }
}
