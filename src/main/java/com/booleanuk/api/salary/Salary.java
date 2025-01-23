package com.booleanuk.api.salary;

public class Salary {
    private int id;
    private String grade;
    private int minSalary;
    private int maxSalary;

    public Salary(int id, String grade, int minSalary, int maxSalary){
        this.id = id;
        this.grade = grade;
            this.minSalary = minSalary;
        this.maxSalary = maxSalary;

    }

    public Salary(String grade, int minSalary, int maxSalary){
        this.grade = grade;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;

    }

    public Salary(int id){
        this.id = id;
    }

    public Salary(){

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
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


    public String toString() {
        String result = "";
        result += this.id + " - ";
        result += this.grade + " - ";
        result += this.minSalary + " - ";
        result += this.maxSalary;
        return result;
    }
}
