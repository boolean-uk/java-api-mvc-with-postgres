package com.booleanuk.api.salarygrade;

public class SalaryGrade {
    private int id;
    private String grade;
    private int minSalary;
    private int maxSalary;

    public SalaryGrade(int id, String grade, int minSalary, int maxSalary) {
        this.id = id;
        this.grade = grade;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    public SalaryGrade(String grade, int minSalary, int maxSalary) {
        this.grade = grade;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    public SalaryGrade(int id) {
        this.id = id;
    }

    public SalaryGrade() {
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
}
