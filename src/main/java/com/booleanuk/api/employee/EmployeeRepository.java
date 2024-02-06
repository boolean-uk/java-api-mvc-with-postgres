package com.booleanuk.api.employee;

import com.booleanuk.api.Interfaces.ICrudRepo;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends ICrudRepo<Employee, Integer> {


}
