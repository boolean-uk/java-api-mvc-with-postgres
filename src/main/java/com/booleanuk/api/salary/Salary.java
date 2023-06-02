package com.booleanuk.api.salary;

public class Salary {
    String grade;
    String minSalary;
    String maxSalary;

    public Salary(String grade, String minSalary, String maxSalary) {
        this.grade = grade;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(String minSalary) {
        this.minSalary = minSalary;
    }

    public String getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(String maxSalary) {
        this.maxSalary = maxSalary;
    }
}
