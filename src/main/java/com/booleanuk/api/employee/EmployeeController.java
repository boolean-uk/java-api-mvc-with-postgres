package com.booleanuk.api.employee;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    EmployeeRepository employeeRepository;

    EmployeeController () {
        try {
            this.employeeRepository = new EmployeeRepository();
        }catch (Exception e) {
            System.out.println("Connection Error: " + e);
        }
    }


    @GetMapping
    public ArrayList<Employee> getAll() {
        try{
            return employeeRepository.getAll();
        }catch (Exception e) {
            System.out.println("Error GetAll: " + e);
        }
        return null;
    }

    @GetMapping("{id}")
    public Employee getById(@PathVariable long id) {
        try {
            return employeeRepository.get(id);
        } catch (SQLException e) {
            System.out.println("Error get by id: " + e);
        }
        return null;
    }

    @DeleteMapping("{id}")
    public Employee deleteById(@PathVariable long id){
        try {
            return employeeRepository.delete(id);
        } catch (SQLException e) {
            System.out.println("Error delete by id: " + e);
        }
        return null;
    }

    @PutMapping("{id}")
    public Employee updateEmployee(@PathVariable long id, @RequestBody Employee employee) {
        try {
            return employeeRepository.update(id, employee);
        }catch (Exception e) {
            System.out.println("Error updating by id: " + e);
        }
        return null;
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        try {
            return employeeRepository.add(employee);
        } catch (SQLException e) {
            System.out.println("Error adding employee: " + e);
        }
        return null;
    }
}
