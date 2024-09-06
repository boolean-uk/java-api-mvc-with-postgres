package com.booleanuk.api.salaries;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Salary(
    @JsonProperty(required = true) String grade,
    @JsonProperty(required = true) int minSalary,
    @JsonProperty(required = true) int maxSalary) {
}
