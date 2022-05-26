package com.company.Entities;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Category{
    private static final AtomicInteger count = new AtomicInteger(0);
    private Integer id;

    public Integer getId() {
        return id;
    }

    private String name;

    public Category(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
        id = count.incrementAndGet();

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
