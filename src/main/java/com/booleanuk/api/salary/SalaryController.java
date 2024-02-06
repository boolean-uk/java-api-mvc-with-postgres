package com.booleanuk.api.salary;


import com.booleanuk.api.salary.Salary;
import com.booleanuk.api.salary.SalaryRepository;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/salaries")
public class SalaryController {

    private final SalaryRepository salaryRepository;

    public SalaryController(SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    @GetMapping()
    public Iterable<Salary> findAllEmployees() {
        return this.salaryRepository.findAll();
    }
    @GetMapping("/{id}")
    public Optional<Salary> findOneEmployeeById(@PathVariable int id){
        Optional<Salary> temp = this.salaryRepository.findById(id);
        if(temp.isPresent()){
            return temp;
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with that ID was found");
        }

    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Salary addOneEmployee(@RequestBody Salary salary) {
        try{
            return this.salaryRepository.save(salary);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }


    }

    @PutMapping("/{id}")
    public Salary updateOneEmployee(@PathVariable int id, @RequestBody Salary salary){
        Salary temp = this.salaryRepository.findById(id).orElse(null);
        if(salary.getMaxSalary() == 0 || salary.getMinSalary() == 0 ||salary.getGrade() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the salary," +
                    " please check all the required fields");
        }
        Optional<Salary> optionalEmployee = this.salaryRepository.findById(id);

        if(optionalEmployee.isPresent()){
            Salary sal = optionalEmployee.get();
            sal.setGrade(salary.getGrade());
            sal.setMinSalary(salary.getMinSalary());
            sal.setMaxSalary(salary.getMaxSalary());
            return this.salaryRepository.save(sal);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public Salary deleteOneEmployee(@PathVariable int id){
        Optional<Salary> temp = this.salaryRepository.findById(id);
        if(temp.isPresent()){
            this.salaryRepository.deleteById(id);
            return temp.get();
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No salaries with that ID was found");
        }
    }
}
