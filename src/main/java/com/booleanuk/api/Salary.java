package com.booleanuk.api;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Salary {
    private long id;
    private String grade;
    private String minSalary;
    private String maxSalary;
}
