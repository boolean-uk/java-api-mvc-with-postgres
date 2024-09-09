package com.booleanuk.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Department {
    private long id;
    private String name;
    private String location;

    public Department(long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }
}
