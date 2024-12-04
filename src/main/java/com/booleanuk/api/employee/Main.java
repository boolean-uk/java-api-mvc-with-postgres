package com.booleanuk.api.employee;

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
    }
}