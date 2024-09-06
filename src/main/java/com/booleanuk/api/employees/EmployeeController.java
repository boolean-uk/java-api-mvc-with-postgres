package com.booleanuk.api.employees;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeRepository er;

    public EmployeeController() throws SQLException {
        er = new EmployeeRepository();
    }

    @GetMapping
    public ArrayList<Employee> getAll() {
        try {
            return er.getAll();
        } catch (SQLException err) {
            System.out.println(err);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public Employee get(@PathVariable int id){
        Employee e = null;
        try {
            e = er.get(id);
        } catch (SQLException err) {
            System.out.println(err);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return e;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee post(@RequestBody Employee employee) {
        Employee e = null;
        try {
            e = er.post(employee);
        } catch (SQLException err) {
            System.out.println(err);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return e;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee put(@PathVariable int id, @RequestBody Employee employee) {
        Employee e = null;
        try {
            e = er.update(id, employee);
        } catch (SQLException err) {
            System.out.println(err);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return e;
    }

    @DeleteMapping("{id}")
    public Employee delete(@PathVariable int id) {
        Employee e = null;
        try {
            e = er.delete(id);
        } catch (SQLException err) {
            System.out.println(err);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return e;
    }


}
