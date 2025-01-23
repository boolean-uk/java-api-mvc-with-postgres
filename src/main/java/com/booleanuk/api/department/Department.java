package com.booleanuk.api.department;

public class Department {
    private Integer id;
    String name;
    String location;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
