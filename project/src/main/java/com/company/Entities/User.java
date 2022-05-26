package com.company.Entities;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class User{
    private static final AtomicInteger count = new AtomicInteger(0);
    private Integer id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String role;
    public Integer getId() {
        return id;
    }

    public User(Integer id, String firstName, String lastName, String userName, String password, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.role = role;

    }

    public User(User user){
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.role = user.getRole();
        id = count.incrementAndGet();

    }

    public User() {


    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
