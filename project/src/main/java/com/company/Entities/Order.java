package com.company.Entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order extends Tracker{
    private Date date;
    private Integer user_id;
    private Integer product_id;

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Order(Date date, Integer user_id, Integer product_id) {
        this.date = date;
        this.user_id = user_id;
        this.product_id = product_id;
    }
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
