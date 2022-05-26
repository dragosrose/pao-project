package com.company.Services;

import com.company.Entities.User;

import java.util.Set;

public interface IUserService {
    public void addUser(User user);
    public Set<User> getUsers();
    public User getUserById(int id);
    public void deleteUserById(int id);
    void editUser(User user);
    public User login(String userName, String password);

}
