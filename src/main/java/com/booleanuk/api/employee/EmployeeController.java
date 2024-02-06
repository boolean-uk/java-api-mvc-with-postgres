package com.booleanuk.api.employee;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping()
    public Iterable<Employee> findAllEmployees() {
        return this.employeeRepository.findAll();
    }
    @GetMapping("/{id}")
    public Optional<Employee> findOneEmployeeById(@PathVariable int id){
        Optional<Employee> temp = this.employeeRepository.findById(id);
        if(temp.isPresent()){
            return temp;
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with that ID was found");
        }

    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addOneEmployee(@RequestBody Employee employee) {
        try{
            return this.employeeRepository.save(employee);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }


    }
    @PutMapping("/{id}")
    public Employee updateOneEmployee(@PathVariable int id, @RequestBody Employee employee){
        Employee temp = this.employeeRepository.findById(id).orElse(null);
        if(employee.getDepartment() == null || employee.getName() == null || employee.getJobName() == null
                || employee.getSalaryGrade() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the employee," +
                    " please check all the required fields");
        }
        Optional<Employee> optionalEmployee = this.employeeRepository.findById(id);

        if(optionalEmployee.isPresent()){
            Employee emp = optionalEmployee.get();
            emp.setDepartment(employee.getDepartment());
            emp.setName(employee.getName());
            emp.setJobName(employee.getJobName());
            emp.setSalaryGrade(employee.getSalaryGrade());
            return this.employeeRepository.save(emp);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public Employee deleteOneEmployee(@PathVariable int id){
        Optional<Employee> temp = this.employeeRepository.findById(id);
        if(temp.isPresent()){
            this.employeeRepository.deleteById(id);
            return temp.get();
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with that ID was found");
        }
    }
}
