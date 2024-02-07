package com.booleanuk.api;
import com.booleanuk.api.Employee.EmployeeRepository;
public class Main {
    public static void main(String[] args) {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        try {
            employeeRepository.connectToDataBase();
        }
        catch (Exception e) {
            System.out.println("Ooops: " + e);
        }
    }
}
