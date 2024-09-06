package com.booleanuk.api.employees;

import java.sql.SQLException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booleanuk.api.base.Controller;
import com.booleanuk.api.database.DatabaseConnector;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController extends Controller<Employee, EmployeeDTO> {
  public EmployeeController() throws SQLException {
    super(new EmployeeRepository(DatabaseConnector.getConnection()));
  }
}
