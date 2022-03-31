package com.company.Services;

import com.company.Entities.User;
import com.company.Repositories.Repository;

import java.util.Objects;
import java.util.Set;

public class UserService implements IUserService{

    private static UserService instance;

    private Repository<User> userRepository = new Repository<>();

    private UserService() {};

    public static UserService getInstance(){
        if (instance == null)
            instance = new UserService();
        return instance;
    }

    @Override
    public void addUser(User user) {
        this.userRepository.create(user);
    }

    @Override
    public Set<User> getUsers() {
        return this.userRepository.read();
    }

    @Override
    public User getUserById(int id) {
        Set<User> users = getUsers();

        for(User i : users){
            if(i.getId() == id)
                return i;
        }
        return null;
    }

    @Override
    public void deleteUserById(int id) {
        Set<User> users = getUsers();

        for(User i : users){
            if(i.getId() == id){
                this.userRepository.delete(id);
                break;
            }
        }
    }

    @Override
    public void editUser(User user) {
        this.userRepository.update(user);
    }

    @Override
    public User login(String userName, String password) {
        Set<User> users = getUsers();

        for(User i : users){
            if(Objects.equals(i.getUserName(), userName) && Objects.equals(i.getPassword(), password))
                return getUserById(i.getId());
        }

        return null;
    }


}
