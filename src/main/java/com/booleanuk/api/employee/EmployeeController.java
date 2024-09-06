package com.booleanuk.api.employee;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
        private EmployeeRepository employees;

        public EmployeeController() throws SQLException {
            this.employees = new EmployeeRepository();
        }

        @GetMapping
        public List<Employee> getAll() throws SQLException {
            return this.employees.getAll();
        }


    @GetMapping("/{id}")
    public Employee getOne(@PathVariable (name = "id") int id) throws SQLException {
        Employee employee = this.employees.get(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return employee;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) throws SQLException {
        Employee theEmployee = this.employees.add(employee);
        if (theEmployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Customer");
        }
        return theEmployee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable (name = "id") int id, @RequestBody Employee employee) throws SQLException {
        Employee toBeUpdated = this.employees.get(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.employees.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable (name = "id") int id) throws SQLException {
        Employee toBeDeleted = this.employees.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.employees.delete(id);
    }
}
