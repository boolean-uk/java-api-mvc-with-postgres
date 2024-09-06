package com.booleanuk.api.departments;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Departments {

    private int id;
    private String name;
    private String location;

    public Departments(String name, String location) {
        this.name = name;
        this.location = location;
    }
}
