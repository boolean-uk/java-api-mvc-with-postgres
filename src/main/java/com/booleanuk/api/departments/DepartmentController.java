package com.booleanuk.api.departments;

import com.booleanuk.api.employee.Employee;
import com.booleanuk.api.employee.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentRepo departmentRepo;

    @GetMapping
    public ResponseEntity<ArrayList<Department>> getAllDepartments() {
        try {
            ArrayList<Department> departments = departmentRepo.getAllData();
            return ResponseEntity.ok(departments);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getADepartment(@PathVariable int id) {
        try {
            Department department = departmentRepo.getOne(id);
            if (department != null) {
                return ResponseEntity.ok(department);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        try {
            Department department1 = departmentRepo.add(department);
            if (department1 != null) {
                return ResponseEntity.ok(department);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateADepartment(@PathVariable int id, @RequestBody Department department) {
        try {
            Department department1 = departmentRepo.update(id, department);
            if (department1 != null) {
                return ResponseEntity.ok(department1);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable int id) {
        try {
            Department department = departmentRepo.delete(id);
            if (department != null) {
                return ResponseEntity.ok(department);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
