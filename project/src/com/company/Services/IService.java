package com.company.Services;

import com.company.Entities.Category;
import com.company.Entities.User;

public interface IService {

    public void mainPanel();

    public void firstMenu();
    public void register();
    public User login();

    public void userMenu();
    public void displayUserInfo(User user);
    void editUserInfo(User user);
    void displayOrderHistory();
    void cart();

    void shopMenu();
    void displayCategories();
    void displayProducts(Category category);
    void sortProducts(Category category);
    void addProductToCart();

    void adminMenu();
    public void displayAllUsers();
    void banUser(int id);
    boolean closeShop();
}
