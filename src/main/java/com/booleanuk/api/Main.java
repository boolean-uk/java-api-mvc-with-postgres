package com.booleanuk.api;

public class Main {
    public static void main(String[] args){
        EmployeeRepository myRepo = new EmployeeRepository();
        try{
            //myRepo.connectToDatabase();
        }
        catch (Exception e){
            System.out.println("Ooops: " + e);
        }
    }
}
