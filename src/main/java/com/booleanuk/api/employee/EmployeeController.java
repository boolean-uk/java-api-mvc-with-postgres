package com.booleanuk.api.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private EmployeeRepository employeeRepository;

    public EmployeeController() throws SQLException {
        this.employeeRepository = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return this.employeeRepository.getAll();
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable int id) throws SQLException {
        Employee employee = this.employeeRepository.get(id);

        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employee with that Id");
        }
        return employee;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) throws SQLException {
        if (employeeRepository.getAll().contains(employee)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not cool.");
        }
        employeeRepository.add(employee);
        return employee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable (name = "id") int id, @RequestBody Employee employee) throws SQLException {
        if (id < employeeRepository.getAll().size()) {
            employeeRepository.getAll().get(id).setName(employee.getName());
            employeeRepository.getAll().get(id).setJobName(employee.getJobName());
            employeeRepository.getAll().get(id).setSalaryGrade(employee.getSalaryGrade());
            employeeRepository.getAll().get(id).setDepartment(employee.getDepartment());
            return employeeRepository.getAll().get(id);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable int id) throws SQLException {
        Employee employee = this.employeeRepository.get(id);

        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.employeeRepository.delete(id);
    }
}
