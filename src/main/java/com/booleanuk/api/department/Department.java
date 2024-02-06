package com.booleanuk.api.department;

public class Department {
    private int id;
    private String department;
    private String location;

    public Department(int id, String department, String location) {
        this.id = id;
        this.department = department;
        this.location = location;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
