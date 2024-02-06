package com.booleanuk.api.saleries;

public class Salary {
    private int id;
    private String grade;
    private int minSalary;
    private int maxSalary;

    public Salary(int id, String grade, int minSalary, int maxSalary) {
        this.id = id;
        this.grade = grade;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    public String toString() {
        String result = this.id + " - ";
        result += this.grade + " - ";
        result += this.minSalary + " - ";
        result += this.maxSalary + " - ";
        return result;
    }

    public int getId() {
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

    public void setId(int id) {
        this.id = id;
    }
}
