package com.booleanuk.api.departments;

public class Department {
    private int id;
    private String name;
    private String location;

    public Department(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public String toString() {
        String result = this.id + " - ";
        result += this.name + " - ";
        result += this.location + " - ";
        return result;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }

    public void setId(int id) {
        this.id = id;
    }
}
