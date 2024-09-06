package com.booleanuk.api.employees;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EmployeeDTO(
    @JsonProperty(required = true) String name,
    @JsonProperty(required = true) String jobName,
    @JsonProperty(required = true) String salaryGrade,
    @JsonProperty(required = true) String department) {
}
