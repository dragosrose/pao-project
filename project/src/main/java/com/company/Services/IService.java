package com.company.Services;

import com.company.Entities.Category;
import com.company.Entities.Product;
import com.company.Entities.User;

import java.sql.SQLException;

public interface IService {

    public void mainPanel() throws SQLException;

    public void createTables();

    public void firstMenu();
    public void register();
    public User login();

    public void userMenu();
    public void displayUserInfo(User user);
    void editUserInfo(User user);
    void displayOrderHistory();
    void cart();

    void shopMenu(User user);
    void displayCategories();
    void displayProducts(User user, Category category);
    void sortProducts(Category category);
    void addProductToCart(User user, Product product);

    void adminMenu();
    public void displayAllUsers();
    void banUser(int id);
    boolean closeShop();
}
