package com.company.Entities;

import java.util.concurrent.atomic.AtomicInteger;

public class Address{

    private static final AtomicInteger count = new AtomicInteger(0);
    private Integer id;
    private String country;

    public Integer getId() {
        return id;
    }

    private String city;
    private String street;
    private Integer user_id;

    public Address(String country, String city, String street, Integer user_id){
        this.country = country;
        this.city = city;
        this.street = street;
        this.user_id = user_id;
    }

    public Address() {
        id = count.incrementAndGet();
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }


}
