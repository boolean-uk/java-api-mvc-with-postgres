package com.booleanuk.api.department;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class Department {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String location;
}