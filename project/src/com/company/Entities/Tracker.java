package com.company.Entities;

public abstract class Tracker {

    private static int id = 0;
    protected  int currentId;

    public int getId() {
        return currentId;
    }

    public void setId(int id) {
        this.currentId = id;
    }

    public static int nextId(){
        return ++id;
    }
}
