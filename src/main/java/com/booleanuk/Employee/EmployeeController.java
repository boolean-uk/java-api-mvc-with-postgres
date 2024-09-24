package com.booleanuk.Employee;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("employees")
public class EmployeeController {


    private EmployeeRepository employeeRepository;

    public EmployeeController () throws SQLException {
        this.employeeRepository = new EmployeeRepository();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Employee create(@RequestBody Employee newEmployee) {
        try {
            return this.employeeRepository.add(newEmployee);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Employee> getAll() {
        try {
            return this.employeeRepository.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public Employee getById(@PathVariable("id") Long id) {
        try {
            Optional<Employee> employeeOptional = Optional.ofNullable(this.employeeRepository.get(id));
            return employeeOptional.orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with ID " + id + " not found.")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{id}")
    public Employee update(@PathVariable("id") Long id, @RequestBody Employee updatedEmployee) {
        try {
            Optional<Employee> existingEmployeeOptional = Optional.ofNullable(this.employeeRepository.get(id));
            if (existingEmployeeOptional.isPresent()) {
                Employee existingEmployee = existingEmployeeOptional.get();
                existingEmployee.setName(updatedEmployee.getName());
                existingEmployee.setJobName(updatedEmployee.getJobName());
                existingEmployee.setSalaryGrade(updatedEmployee.getSalaryGrade());
                existingEmployee.setDepartment(updatedEmployee.getDepartment());
                return this.employeeRepository.update(id, existingEmployee);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        try {
            Optional<Employee> employeeOptional = Optional.ofNullable(this.employeeRepository.get(id));
            if (employeeOptional.isPresent()) {
                this.employeeRepository.delete(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
