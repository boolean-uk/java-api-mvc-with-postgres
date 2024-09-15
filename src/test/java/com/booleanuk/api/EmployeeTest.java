package com.booleanuk.api;


import com.booleanuk.api.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;

public class EmployeeTest {

    @Test
    public void testInit() {
        Employee employee = new Employee(1,"name", "jobname", "2", 1);


        Assertions.assertThrows(InputMismatchException.class, () -> employee.setSalaryGrade(String.valueOf(101)));

    }
}
