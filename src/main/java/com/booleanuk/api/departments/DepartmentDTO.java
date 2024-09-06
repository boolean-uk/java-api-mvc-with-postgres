package com.booleanuk.api.departments;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DepartmentDTO(
    @JsonProperty(required = true) String name,
    @JsonProperty(required = true) String location) {
}
