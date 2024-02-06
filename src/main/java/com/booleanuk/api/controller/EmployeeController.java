package com.booleanuk.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {


    private EmployeeController employeeController;

    public EmployeeController() {
        this.employeeController = new EmployeeController();
    }
}
