package com.booleanuk.api.department;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("departments")
public class DepartmentController {

    private DepartmentRepository departmentRepository;

    public DepartmentController() throws SQLException {
        this.departmentRepository = new DepartmentRepository();
    }


    @GetMapping
    public List<Department> getAll() throws SQLException{
        return this.departmentRepository.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> get(@PathVariable int id) throws SQLException{
        return Optional.ofNullable(this.departmentRepository.get(id)).map(ResponseEntity::ok).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Didn't find id"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> update(@PathVariable int id, @RequestBody Department department) throws SQLException{
        Department update = this.departmentRepository.get(id);
        if(update == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No id found");
        }
        return Optional.ofNullable(this.departmentRepository.updated(id, department)).map(ResponseEntity::ok).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update department"));
    }

    @PostMapping
    public ResponseEntity<Department> create(@RequestBody Department department) throws SQLException{
        return Optional.ofNullable(this.departmentRepository.add(department)).map(ResponseEntity::ok).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Department> delete(@PathVariable int id) throws SQLException{
        Department delete = this.departmentRepository.get(id);
        if(delete == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No department grade id");
        }
        return ResponseEntity.ok(this.departmentRepository.delete(id));
    }
}
