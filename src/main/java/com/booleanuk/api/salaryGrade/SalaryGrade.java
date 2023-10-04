package com.booleanuk.api.salaryGrade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
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
}
