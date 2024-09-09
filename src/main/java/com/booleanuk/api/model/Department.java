package com.booleanuk.api.model;

public class Department {
    
    private long id;
    private String name;

    public Department(){

    }

    public Department(String name){
        this.name = name;
    }

    public Department(long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setId(long id){
        this.id = id;
    }

}
