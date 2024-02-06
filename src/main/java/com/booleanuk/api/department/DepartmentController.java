package com.booleanuk.api.department;

import com.booleanuk.api.department.DepartmentRepository;
import com.booleanuk.api.employee.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @GetMapping()
    public Iterable<Department> findAllEmployees() {
        return this.departmentRepository.findAll();
    }
    @GetMapping("/{id}")
    public Optional<Department> findOneEmployeeById(@PathVariable int id){
        Optional<Department> temp = this.departmentRepository.findById(id);
        if(temp.isPresent()){
            return temp;
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with that ID was found");
        }

    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Department addOneEmployee(@RequestBody Department department) {
        try{
            return this.departmentRepository.save(department);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }


    }

    @PutMapping("/{id}")
    public Department updateOneEmployee(@PathVariable int id, @RequestBody Department department){
        Department temp = this.departmentRepository.findById(id).orElse(null);
        if(department.getName() == null || department.getLocation() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the department," +
                    " please check all the required fields");
        }
        Optional<Department> optionalDepartment = this.departmentRepository.findById(id);

        if(optionalDepartment.isPresent()){
            Department dep = optionalDepartment.get();
            dep.setName(department.getName());
            dep.setLocation(department.getLocation());
            return this.departmentRepository.save(dep);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public Department deleteOneEmployee(@PathVariable int id){
        Optional<Department> temp = this.departmentRepository.findById(id);
        if(temp.isPresent()){
            this.departmentRepository.deleteById(id);
            return temp.get();
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments with that ID was found");
        }
    }
}
