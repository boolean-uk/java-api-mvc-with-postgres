package com.booleanuk.api.model;

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

    public Salary(String grade, int minSalary, int maxSalary) {
        this.grade = grade;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    public Salary() {

    }

    public long getId() {
        return this.id;
    }

    public String getGrade() {
        return this.grade;
    }

    public int getMinSalary() {
        return this.minSalary;
    }

    public int getMaxSalary() {
        return this.maxSalary;
    }

    public void setId(long newId) {
        this.id = newId;
    }

}
