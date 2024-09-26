package com.booleanuk.api.salary;

public class Salary {
    private long id;
    private String grade;
    private int minSalary;
    private int maxSalary;

    public Salary(long id, String grade, int minSalary, int maxSalary) {
        this.id = id;
        this.grade = grade;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    public long getId() {
        return id;
    }

    public String getGrade() {
        return grade;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public void setId(long id) {
        this.id = id;
    }
}
