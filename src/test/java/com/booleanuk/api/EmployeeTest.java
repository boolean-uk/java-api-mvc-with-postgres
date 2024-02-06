package com.booleanuk.api;

import com.booleanuk.api.core.employees.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmployeeTest {
    @Test
    public void toStringIsEqualTo() {
        Employee employee = new Employee(
                1,
                "Fart Limpson",
                "Troublemaker",
                "10",
                "Street");

        String expected = "1 - Fart Limpson - Troublemaker - 10 - Street";
        String actual = employee.toString();

        Assertions.assertEquals(expected, actual);
    }
}
