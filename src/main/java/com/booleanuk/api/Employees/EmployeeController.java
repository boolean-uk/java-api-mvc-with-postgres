package com.booleanuk.api.Employees;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository employees;

    public EmployeeController() {
        employees = new EmployeeRepository();
    }
}
