package com.booleanuk.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

//        try {
//            EmployeeRepository repo = new EmployeeRepository();
//            for (Employee employee : repo.getAll()) {
//                System.out.println(employee);
//            }
////            repo.connectToDatabase();
//        } catch (Exception e) {
//            System.out.println("oops: " + e );
//        }

}
