package com.booleanuk.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class TestEmployeeRepository {
    @Test
    public void testGetOneAndGetAll(){
        try {
            EmployeeRepository e = new EmployeeRepository();

            Assertions.assertEquals(4, e.getAll().size());

            for (Employee employee : e.getAll()){
                System.out.println(employee);
            }

            Assertions.assertEquals("Billy Rae Cyrus", e.getOne(3).getName());
            Assertions.assertEquals("50", e.getOne(1).getSalaryGrade());
            Assertions.assertEquals("Office", e.getOne(4).getJobName());

        } catch (SQLException ex) {
            Assertions.fail();
        }
    }

    @Test
    public void testAddAndDelete(){
        try {
            EmployeeRepository e = new EmployeeRepository();

            Employee newE = e.add(new Employee(
                    5,
                    "Jason",
                    "Singer",
                    "50",
                    "None"
            ));

            Assertions.assertEquals("Jason", e.getOne(5).getName());
            Assertions.assertEquals(5, e.getAll().size());

            Employee deletedE = e.delete(5);

            Assertions.assertEquals("50", deletedE.getSalaryGrade());
            Assertions.assertEquals(4, e.getAll().size());

        } catch (SQLException ex) {
            Assertions.fail();
        }
    }

    @Test
    public void testUpdate(){
        try {
            EmployeeRepository e = new EmployeeRepository();
            e.update(2, new Employee(10, "Coolio", "Poolio", "High", "Toolio"));

            Assertions.assertEquals(e.getOne(2).getName(), "Coolio");
            Assertions.assertEquals(e.getOne(2).getId(), 2);
            Assertions.assertEquals(4, e.getAll().size());

        } catch (SQLException ex) {
            Assertions.fail();
        }
    }

}
