package com.booleanuk.api.controllers;

import com.booleanuk.api.repositories.SalaryRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("salaries")
public class SalaryController {
    private SalaryRepository salaries;

    public SalaryController() throws SQLException {
        this.salaries = new SalaryRepository();
    }
}
