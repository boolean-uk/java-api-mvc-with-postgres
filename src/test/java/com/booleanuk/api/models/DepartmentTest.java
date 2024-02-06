package com.booleanuk.api.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    @Test
    void testDepartmentConstructor() {
        Department department = new Department(1, "Test", "Testing Grounds");
        Assertions.assertEquals(1, department.getId());
        Assertions.assertEquals("Test", department.getName());
        Assertions.assertEquals("Testing Grounds", department.getLocation());
    }
}