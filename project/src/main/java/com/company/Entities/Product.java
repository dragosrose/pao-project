package com.company.Entities;

import java.util.concurrent.atomic.AtomicInteger;

public class Product{
    private static final AtomicInteger count = new AtomicInteger(0);
    private Integer id;

    public Integer getId() {
        return id;
    }

    private String name;
    private String description;
    private int stock;

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getDistributor_id() {
        return distributor_id;
    }

    public void setDistributor_id(int distributor_id) {
        this.distributor_id = distributor_id;
    }

    private float price;
    private int category_id;
    private int distributor_id;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Product(Integer id, String name, String description, int stock, float price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
    }

    public Product(String name, String description, int stock, float price, int category_id, int distributor_id) {
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.category_id = category_id;
        this.distributor_id = distributor_id;
        id = count.incrementAndGet();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
