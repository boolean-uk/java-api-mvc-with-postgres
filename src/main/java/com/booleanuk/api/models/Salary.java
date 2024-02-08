package com.booleanuk.api.models;

public class Salary {
    private int id;
    private String grade;
    private int min_salary;
    private int max_salary;

    public Salary(int id, String grade, int min_salary, int max_salary) {
        this.id = id;
        this.grade = grade;
        this.min_salary = min_salary;
        this.max_salary = max_salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getMin_salary() {
        return min_salary;
    }

    public void setMin_salary(int min_salary) {
        this.min_salary = min_salary;
    }

    public int getMax_salary() {
        return max_salary;
    }

    public void setMax_salary(int max_salary) {
        this.max_salary = max_salary;
    }
}
