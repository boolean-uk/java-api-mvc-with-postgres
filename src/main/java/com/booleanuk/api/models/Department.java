package com.booleanuk.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    private int id;
    private String departmentName;
    private String departmentLocation;
}
