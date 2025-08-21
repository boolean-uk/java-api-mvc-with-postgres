package com.booleanuk.api.salary;

public class Salary {
    int id;
    String salaryGrade;

    public Salary(int id, String salaryGrade){
        this.id = id;
        this.salaryGrade = salaryGrade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSalaryGrade() {
        return salaryGrade;
    }

    public void setSalaryGrade(String salaryGrade) {
        this.salaryGrade = salaryGrade;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "id=" + id +
                ", salaryGrade='" + salaryGrade + '\'' +
                '}';
    }
}
