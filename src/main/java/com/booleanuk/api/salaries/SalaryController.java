package com.booleanuk.api.salaries;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booleanuk.api.base.Controller;
import com.booleanuk.api.database.DatabaseConnector;

@RestController
@RequestMapping(value = "/salaries")
public class SalaryController extends Controller<Salary, Salary> {
  public SalaryController() throws SQLException {
    super(new SalaryRepository(DatabaseConnector.getConnection()));
  }
}
