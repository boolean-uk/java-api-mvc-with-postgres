package com.booleanuk.api.salaries;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Salary {
    private long id;
    private String grade;
    private int minSalary;
    private int maxSalary;

}

