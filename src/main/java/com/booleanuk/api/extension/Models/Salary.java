package com.booleanuk.api.extension.Models;

public class Salary {
    private int id;
    private String grade;
    private int maxSalary;
    private int minSalary;

    public Salary(int id, String grade, int maxSalary, int minSalary) {
        this.id = id;
        this.grade = grade;
        this.maxSalary = maxSalary;
        this.minSalary = minSalary;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(int maxSalary) {
        this.maxSalary = maxSalary;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
