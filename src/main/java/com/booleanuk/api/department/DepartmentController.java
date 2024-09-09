package com.booleanuk.api.department;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("departments")
public class DepartmentController {
    DepartmentRepository departmentRepository;

    DepartmentController () {
        try {
            this.departmentRepository = new DepartmentRepository();
        }catch (Exception e) {
            System.out.println("Connection Error: " + e);
        }
    }

    @GetMapping
    public ArrayList<Department> getAll() {
        try{
            return departmentRepository.getAll();
        }catch (Exception e) {
            System.out.println("Error GetAll: " + e);
        }
        return null;
    }

    @GetMapping("{id}")
    public Department getById(@PathVariable long id) {
        try {
            return departmentRepository.get(id);
        } catch (SQLException e) {
            System.out.println("Error get by id: " + e);
        }
        return null;
    }

    @DeleteMapping("{id}")
    public Department deleteById(@PathVariable long id){
        try {
            return departmentRepository.delete(id);
        } catch (SQLException e) {
            System.out.println("Error delete by id: " + e);
        }
        return null;
    }

    @PutMapping("{id}")
    public Department updateDepartment(@PathVariable long id, @RequestBody Department Department) {
        try {
            return departmentRepository.update(id, Department);
        }catch (Exception e) {
            System.out.println("Error updating by id: " + e);
        }
        return null;
    }

    @PostMapping
    public Department addDepartment(@RequestBody Department Department) {
        try {
            return departmentRepository.add(Department);
        } catch (SQLException e) {
            System.out.println("Error adding Department: " + e);
        }
        return null;
    }
}
