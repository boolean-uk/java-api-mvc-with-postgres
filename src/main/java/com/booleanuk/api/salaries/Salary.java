package com.booleanuk.api.salaries;

public class Salary {
    private int id, minSalary, maxSalary;
    private String grade;

    public Salary(int id, int minSalary, int maxSalary, String grade) {
        this.id = id;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.grade = grade;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public String getGrade() {
        return grade;
    }
    public String toString(){
        return this.getId()+ " - "+this.getGrade()+" - Salary range: "+this.getMinSalary()+" / "+this.getMaxSalary();
    }
}
