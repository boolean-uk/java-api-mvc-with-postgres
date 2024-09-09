package com.booleanuk.api.salaries;

import com.booleanuk.api.employee.Employee;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("salaries")
public class SalariesController {
    SalariesRepository repo;

    SalariesController() {
        try {
            this.repo = new SalariesRepository();
        } catch (SQLException e) {
            System.out.println("Connection Error: " + e);
        }
    }


    @GetMapping
    public ArrayList<Salary> getAll() {
        try{
            return repo.getAll();
        }catch (Exception e) {
            System.out.println("Error GetAll: " + e);
        }
        return null;
    }

    @GetMapping("{id}")
    public Salary getById(@PathVariable long id) {
        try {
            return repo.get(id);
        } catch (SQLException e) {
            System.out.println("Error get by id: " + e);
        }
        return null;
    }

    @DeleteMapping("{id}")
    public Salary deleteById(@PathVariable long id){
        try {
            return repo.delete(id);
        } catch (SQLException e) {
            System.out.println("Error delete by id: " + e);
        }
        return null;
    }

    @PutMapping("{id}")
    public Salary updateEmployee(@PathVariable long id, @RequestBody Salary salary) {
        try {
            return repo.update(id, salary);
        }catch (Exception e) {
            System.out.println("Error updating by id: " + e);
        }
        return null;
    }

    @PostMapping
    public Salary addEmployee(@RequestBody Salary salary) {
        try {
            return repo.add(salary);
        } catch (SQLException e) {
            System.out.println("Error adding employee: " + e);
        }
        return null;
    }
}
