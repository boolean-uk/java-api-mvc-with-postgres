package com.booleanuk.api.salaryGrade;

public class SalaryGrade {
    private int id;
    private String grade;

    public SalaryGrade(int id, String grade) {
        this.id = id;
        this.grade = grade;
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
}
