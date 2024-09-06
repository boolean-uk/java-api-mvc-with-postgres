package com.booleanuk.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;


@SpringBootApplication
public class Main {
    public static void main(String[] args) throws SQLException {


        SpringApplication.run(Main.class, args);

        /*
        EmployeeRepository myRepo = new EmployeeRepository();


        try{
            myRepo.connectToDatabase();
        }catch(Exception e) {
            System.out.println("Whoops " + e);
        }



        try {
            System.out.println(myRepo.getAll());
        } catch (Exception e) {
            System.out.println("Oh dear: " + e);
        }

         */
    }
}