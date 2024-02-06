package com.booleanuk.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args){
        EmployeeRepository myRepo = new EmployeeRepository();
        try {
            myRepo.connectToDatabase();
        }
        catch(Exception e) {
            System.out.println("Oops: " + e);
        }
    }
}
