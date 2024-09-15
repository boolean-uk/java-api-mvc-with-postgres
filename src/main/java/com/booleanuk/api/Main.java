package com.booleanuk.api;
import com.booleanuk.api.employee.EmployeeRepository;

public class Main {
    public static void main(String[] args) {
        EmployeeRepository myRepository = null;

        try {
            myRepository = new EmployeeRepository();
//            System.out.println(myRepository.getAll());
        }
        catch (Exception e) {
            System.out.println("Oops: " + e);
        }
    }
}
