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

    public Employee addEmployee(Employee newEmployee){
        Employee employee = findEmployeeFromName(newEmployee.getName());

        if (employee != null){
            return null;
        }

        employees.add(employee);

        return newEmployee;
    }

    public Employee update(int id, Employee updatedEmployee){
        Employee employee = findEmployeeFromId(id);

        if (employee == null){
            return null;
        }

        employee.setName(updatedEmployee.getName());
        employee.setJobName(updatedEmployee.getJobName());
        employee.setSalaryGrade(updatedEmployee.getSalaryGrade());
        employee.setDepartment(updatedEmployee.getDepartment());

        return employee;
    }

    public Employee delete(int id){
        Employee employee = findEmployeeFromId(id);

        if (employee == null){
            return null;
        }

        int index = employees.indexOf(employee);

        return employees.remove(index);
    }

    public Employee findEmployeeFromId(int id){
        for (Employee employee : employees){
            if (employee.getId() == id){
                return employee;
            }
        }

        return null;
    }

    public Employee findEmployeeFromName(String name){
        for (Employee employee : employees){
            if (employee.getName().equals(name)){
                return employee;
            }
        }

        return null;
    }
}
