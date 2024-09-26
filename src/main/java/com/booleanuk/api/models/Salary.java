package com.booleanuk.api.models;

public class Salary {
    private int minSalary;
    private int maxSalary;
    private String grade;

    public Salary() {}

    public Salary(int minSalary, int maxSalary, String grade) {
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.grade = grade;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(int maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
