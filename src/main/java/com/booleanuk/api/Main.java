package com.booleanuk.api;
import com.booleanuk.api.employees.Employees;
import com.booleanuk.api.employees.EmployeesRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EmployeesRepository myRepo = null;
        try {
            myRepo = new EmployeesRepository();
        } catch (Exception e) {
            System.out.println("Oops: " + e);
        }
        Scanner input = new Scanner(System.in);
        String userChoice = "";
        while (!userChoice.equals("X")) {
            System.out.println("\nPlease choose from the following options.\n");
            System.out.println("A Show all employees");
            System.out.println("B Show a specific employer");
            System.out.println("C Update a specific employer ");
            System.out.println("D Delete a specific employer");
            System.out.println("E Add a new employer");
            System.out.println("X to exit the program");
            userChoice = input.nextLine().toUpperCase();
            if (userChoice.equals("A")) {
                try {
                    assert myRepo != null;
                    for (Employees employees : myRepo.getAll())
                        System.out.println(employees);
                } catch (Exception e) {
                    System.out.println("Oops: " + e);
                }
            } else if (userChoice.equals("B")) {
                System.out.println("\nPlease enter the id of the user you wish to view.");
                String value = input.nextLine();
                long id = Long.parseLong(value);
                try {
                    assert myRepo != null;
                    Employees employees = myRepo.get(id);
                    if (employees != null) {
                        System.out.println(employees);
                    } else {
                        System.out.println("Sorry that employer id was not recognised");
                    }
                } catch (Exception e) {
                    System.out.println("Oops: " + e);
                }
            } else if (userChoice.equals("C")) {
                System.out.println("\nPlease enter the id of the user you wish to view.");
                String value = input.nextLine();
                long id = Long.parseLong(value);
                System.out.println("Please enter the new name for the updated employee");
                String name = input.nextLine();
                System.out.println("Please enter the new jobname for the updated employee");
                String jobName = input.nextLine();
                System.out.println("Please enter the new salarygrade for the updated employee");
                String salaryGrade = input.nextLine();
                System.out.println("Please enter the new department for the updated employee");
                String department = input.nextLine();
                Employees updatedEmployee = new Employees(id, name, jobName, salaryGrade, department);
                try {
                    assert myRepo != null;
                    Employees theEmployee = myRepo.update(id, updatedEmployee);
                    if (theEmployee != null) {
                        System.out.println(theEmployee);
                    } else {
                        System.out.println("Sorry that customer id was not recognised");
                    }
                } catch (Exception e) {
                    System.out.println("Oops: " + e);
                }
            } else if (userChoice.equals("D")) {
                System.out.println("\nPlease enter the id of the user you wish to delete.");
                String theValue = input.nextLine();
                long theId = Long.parseLong(theValue);
                try {
                    assert myRepo != null;
                    Employees aEmployee = myRepo.delete(theId);
                    if (aEmployee!= null) {
                        System.out.println(aEmployee);
                    } else {
                        System.out.println("Sorry that employee id was not recognised");
                    }
                } catch (Exception e) {
                    System.out.println("Oops: " + e);
                }
            } else if (userChoice.equals("E")) {
                System.out.println("Please enter the name for the new employee");
                String name = input.nextLine();
                System.out.println("Please enter the jobname for the new employee");
                String jobName = input.nextLine();
                System.out.println("Please enter the salarygrade for the new employee");
                String salaryGrade = input.nextLine();
                System.out.println("Please enter the department for the new employee");
                String department = input.nextLine();
                Employees employees = new Employees(0, name, jobName, salaryGrade, department);
                try {
                    assert myRepo != null;
                    employees = myRepo.add(employees);
                    if (employees != null) {
                        System.out.println(employees);
                    } else {
                        System.out.println("Sorry we couldn't add that customer for some reason");
                    }
                } catch (Exception e) {
                    System.out.println("Oops: " + e);
                }
            }
        }
    }
}


