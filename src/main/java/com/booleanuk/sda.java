/*package com.booleanuk;
import com.booleanuk.Employee.Employee;
import com.booleanuk.Employee.EmployeeRepository;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        EmployeeRepository myRepo = new EmployeeRepository();
        try {
            myRepo.connectToDatabase();
        }
        catch(Exception e) {
            System.out.println("Oops: " + e);
        }
        try {
            for (Employee employee : myRepo.getAll())
                System.out.println(employee);
        }
        catch(Exception e) {
            System.out.println("Oops: " + e);
        }
    }
}*/