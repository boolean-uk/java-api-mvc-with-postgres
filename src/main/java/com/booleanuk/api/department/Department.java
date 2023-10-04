package com.booleanuk.api.department;

import jakarta.validation.constraints.NotBlank;

public class Department {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    public Department(Long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }
    // TODO: Add Lombok..
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setId(Long id) {
        this.id = id;
    }
}