package com.booleanuk.api.Department;

import com.booleanuk.api.Salary.Salary;
import com.booleanuk.api.Salary.SalaryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {
    private DepartmentRepository repository;

    public DepartmentController() throws SQLException {
        this.repository = new DepartmentRepository();
    }

    @GetMapping
    public List<Department> getAll() throws SQLException {
        return this.repository.getAll();
    }

    @GetMapping("{id}")
    public Department getOne(@PathVariable int id) throws SQLException {
        return this.repository.get(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Department addEmployee(@RequestBody Department department) {

        try {
            return this.repository.add(department);
        } catch (Exception e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Could not create the new department, please check all required fields are correct");
        }

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable int id, @RequestBody Department department) throws SQLException {
        return this.repository.update(id, department);
    }

    @DeleteMapping("{id}")
    public Department delete(@PathVariable int id) throws SQLException {
        return this.repository.delete(id);
    }

}
