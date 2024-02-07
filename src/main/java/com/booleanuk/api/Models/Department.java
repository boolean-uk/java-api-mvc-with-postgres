package com.booleanuk.api.Models;

public class Department {
    private long id;
    private String name;

    public Department(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        String result = "";
        result += this.id + " - ";
        result += this.name;
        return result;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
