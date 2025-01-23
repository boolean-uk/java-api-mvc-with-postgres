package com.booleanuk.api.department;

public class Department {
    private int id;
    private String name;
    private String location;

    public Department(int id, String name, String location){
        this.id = id;
        this.name = name;
        this.location = location;

    }

    public Department(String name, String location){
        this.name = name;
        this.location = location;

    }

    public Department(int id){
        this.id = id;
    }

    public Department(){

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }


    public String toString() {
        String result = "";
        result += this.id + " - ";
        result += this.name + " - ";
        result += this.location;
        return result;
    }
}
