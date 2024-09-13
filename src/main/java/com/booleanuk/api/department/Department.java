package com.booleanuk.api.department;

public class Department {
    private long id;
    private String name;
    private String location;

    public Department(long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public String toString(){
        String result = "";
        result += this.id + " - ";
        result += this.name + " - ";
        result += this.location + " - ";
        return result;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setId(long id) {
        this.id = id;
    }
}
