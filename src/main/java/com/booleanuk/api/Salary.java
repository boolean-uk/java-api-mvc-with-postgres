package com.booleanuk.api;

public class Salary {
    private long id;
    private String name;


    public Salary(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        String result = "";
        result += this.id + " - ";
        result += this.name + " - ";
        return result;
    }


    public String getName() {
        return name;
    }


    public void setId(long id) {
        this.id = id;
    }
}
