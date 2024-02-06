package com.booleanuk.api.salary;

import com.booleanuk.api.employee.Employee;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "salaries")
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String grade;

    private int minSalary;

    private int maxSalary;

    private Salary(){}
    public Salary(String grade, int minSalary, int maxSalary) {
        this.grade = grade;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    public Integer getId() {
        return id;
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
