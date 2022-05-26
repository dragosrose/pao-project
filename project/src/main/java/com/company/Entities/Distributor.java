package com.company.Entities;

public class Distributor extends User{
    public String name;

    public Distributor(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
