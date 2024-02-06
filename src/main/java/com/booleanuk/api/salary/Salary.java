package com.booleanuk.api.salary;

public class Salary {
    private int id;
    private String salary_grade;
    private int min_salary;
    private int max_salary;

    public Salary(int id, String salary_grade, int min_salary, int max_salary) {
        this.id = id;
        this.salary_grade = salary_grade;
        this.min_salary = min_salary;
        this.max_salary = max_salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSalary_grade() {
        return salary_grade;
    }

    public void setSalary_grade(String salary_grade) {
        this.salary_grade = salary_grade;
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
