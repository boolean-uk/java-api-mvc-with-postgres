package com.booleanuk.api.departments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Department {
    private int id;
    private String name;
    private String location;
}
