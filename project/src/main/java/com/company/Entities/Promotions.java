package com.company.Entities;

public class Promotions extends Tracker{
    private String code;
    private float discount;
    private Integer user_id;

    public Promotions(String code, float discount, Integer user_id) {
        this.code = code;
        this.discount = discount;
        this.user_id = user_id;
    }
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
