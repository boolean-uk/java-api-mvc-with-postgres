package com.booleanuk.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestEmployee {
    @Test
    public void testEmployee(){
        Employee e = new Employee(
                1,
                "Karl",
                "Driver",
                "Something",
                "Not sure"
        );

        Assertions.assertEquals(e.getId(), 1);
        Assertions.assertEquals(e.getName(), "Karl");
        Assertions.assertEquals(e.getJobName(), "Driver");
        Assertions.assertEquals(e.getSalaryGrade(), "Something");
        Assertions.assertEquals(e.getDepartment(), "Not sure");

        e.setId(4);

        Assertions.assertEquals(e.getId(), 4);

        System.out.println(e);
    }
}
