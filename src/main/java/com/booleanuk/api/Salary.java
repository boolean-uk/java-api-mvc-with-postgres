package com.booleanuk.api;

public class Salary {
    private int id;
    private String grade;
    private int minSalary;
    private int maxSalary;

    public Salary(int id, String grade, int min, int max) {
        this.id = id;
        this.grade = grade;
        this.minSalary = min;
        this.maxSalary = max;
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

    @Override
    public String toString() {
        return "Salary{" +
                "id=" + id +
                ", grade='" + grade + '\'' +
                ", minSalary=" + minSalary +
                ", maxSalary=" + maxSalary +
                '}';
    }
}

