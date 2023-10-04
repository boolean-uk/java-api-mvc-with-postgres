package com.booleanuk.api.salaryGrade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public class SalaryGrade {
    private Long id;
    @NotBlank
    private String grade;

    @NotNull
    @Positive
    private Integer minSalary;

    @NotNull
    @Positive
    private Integer maxSalary;

    public SalaryGrade(Long id,
                       String grade,
                       Integer minSalary,
                       Integer maxSalary) {
        this.id = id;
        this.grade = grade;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    public Long getId() {
        return id;
    }

    public String getGrade() {
        return grade;
    }

    public Integer getMinSalary() {
        return minSalary;
    }

    public Integer getMaxSalary() {
        return maxSalary;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
