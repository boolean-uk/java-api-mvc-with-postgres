package com.booleanuk.api.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SalaryTest {

    @Test
    void testSalaryConstructor() {
        Salary salary = new Salary(1, "8", 10, 20);
        Assertions.assertEquals(1, salary.getId());
        Assertions.assertEquals("8",salary.getGrade());
        Assertions.assertEquals(10,salary.getMinSalary());
        Assertions.assertEquals(20,salary.getMaxSalary());
    }
}