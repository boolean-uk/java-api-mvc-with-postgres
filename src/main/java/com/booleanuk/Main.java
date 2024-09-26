package com.booleanuk;

import com.booleanuk.api.Employee;
import com.booleanuk.api.EmployeeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        EmployeeRepository myRepo = null;
        try {
            myRepo = new EmployeeRepository();
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

        SpringApplication.run(Main.class, args);
    }
}
