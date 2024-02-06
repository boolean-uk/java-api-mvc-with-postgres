package com.booleanuk.api;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    private List<Employee> employees;

    public EmployeeRepository(){
        employees = new ArrayList<>();
    }

    public List<Employee> getEmployees(){
        return employees;
    }

    public Employee getEmployee(int id){
        Employee employee = findEmployeeFromId(id);

        if (employee == null){
            return null;
        }
        int index = employees.indexOf(employee);

        return employees.get(id);
    }

    public boolean addEmployee()

    private Employee findEmployeeFromId(int id){
        for (Employee employee : employees){
            if (employee.getId() == id){
                return employee;
            }
        }

        return null;
    }
}
