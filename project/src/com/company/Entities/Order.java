package com.company.Entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order extends Tracker{
    private Date date;
    private Set<Product> products = new HashSet<Product>();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

}
