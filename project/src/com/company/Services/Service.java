package com.company.Services;

import com.company.Entities.Address;
import com.company.Entities.Category;
import com.company.Entities.Product;
import com.company.Entities.User;

import java.util.*;

public class Service implements IService {
    private final UserService userService = UserService.getInstance();
    private final CategoryService categoryService = CategoryService.getInstance();

    private static Service instance;

    private final Scanner scanner = new Scanner(System.in);

    private Service() { }

    public static Service getInstance(){
        if(instance == null)
            instance = new Service();
        return instance;
    }


    @Override
    public void mainPanel() {
        Set<Product> phones = new HashSet<Product>(Arrays.asList(
                new Product("iPhone12", "Even better at bringing nothing new", 3, 3000 ),
                new Product("Samsung Galaxy S20", "Journey to the galaxy.", 5, 5000)

        ));

        Set<Product> laptops = new HashSet<Product>(Arrays.asList(
                new Product("Asus TUF A15", "Some laptop specs", 2, 4000),
                new Product("Lenovo Legion 15", "Some other laptop specs", 4, 8000)
        ));


        categoryService.addCategory(new Category("Phones", phones));
        categoryService.addCategory(new Category("Laptops", laptops));


        User user = new User();
        boolean login = false;


        dance: while(true){

            do {
                firstMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        register();
                        break;
                    case 2:
                        user = login();
                        login = user != null;
                        if (login == false)
                            System.out.println("Wrong credentials. Please try again.");
                        break;

                    case 3:
                        break dance; // haha :)

                }

            } while (!login);


            System.out.println("You are logged in.");

            shopMenu: while(true){
                userMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice){
                    case 1:
                        displayUserInfo(user);
                        break;
                    case 2:
                        editUserInfo(user);
                        break;
                    case 3:
                        shopMenu();
                        break;

                    default:
                        login = false;
                        break shopMenu;

                }
            }



        }



    }

    @Override
    public void register() {
        User user = new User();

        System.out.println("Welcome to our shop. Please register: ");
        System.out.println("Username: ");
        user.setUserName(scanner.nextLine());
        System.out.println("Password: ");
        user.setPassword(scanner.nextLine());
        System.out.println("First name: ");
        user.setFirstName(scanner.nextLine());
        System.out.println("Last name: ");
        user.setLastName(scanner.nextLine());
        System.out.println("Role: ");
        user.setRole(scanner.nextLine());

        Address address = new Address();
        System.out.println("City: ");
        address.setCity(scanner.nextLine());
        System.out.println("Country: ");
        address.setCountry(scanner.nextLine());
        System.out.println("Street");
        address.setStreet(scanner.nextLine());

        user.setAddress(address);

        userService.addUser(user);
    }

    @Override
    public User login() {
        String userName, password;

        System.out.println("Username: ");
        userName = scanner.nextLine();
        System.out.println("Password: ");
        password = scanner.nextLine();

        return userService.login(userName, password);


    }

    @Override
    public void firstMenu() {
        System.out.println("-------------------------------------");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit.");

    }

    @Override
    public void userMenu() {
        System.out.println("-------------------------------------");
        System.out.println("1. Display your profile. ");
        System.out.println("2. Edit your profile. ");
        System.out.println("3. Go shop. ");
        System.out.println("4. Go back. ");
    }

    @Override
    public void displayUserInfo(User user) {
        System.out.println("Username: " + user.getUserName());
        System.out.println("First name: " + user.getFirstName());
        System.out.println("Last name: " + user.getLastName());
        System.out.println("Role: " + user.getRole());

        Address address = user.getAddress();

        System.out.println("Address: " + address.getCountry() + ", " + address.getCity() + ", "
        + address.getStreet());
    }

    @Override
    public void editUserInfo(User user) {

        System.out.println("Choose what field do you want to edit: ");

        edit: while (true){
            System.out.println("1. Username ");
            System.out.println("2. First name ");
            System.out.println("3. Last name ");
            System.out.println("4. Exit ");
            int choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice){
                case 1:
                    System.out.println("Type your new username: ");
                    user.setUserName(scanner.nextLine());
                    break;
                case 2:
                    System.out.println("Type your new first name: ");
                    user.setFirstName(scanner.nextLine());
                    break;
                case 3:
                    System.out.println("Type your new last name: ");
                    user.setLastName(scanner.nextLine());
                    break;
                case 4:
                    userService.editUser(user);
                    break edit;
            }
        }
    }

    @Override
    public void displayOrderHistory() {

    }

    @Override
    public void cart() {

    }

    @Override
    public void shopMenu() {
        System.out.println("--------------------------");
        shop: while (true){
            displayCategories();

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1:
                    Category c = categoryService.getCategoryById(1);
                    displayProducts(c);
                    break;

                case 2:
                    Category c1 = categoryService.getCategoryById(2);
                    displayProducts(c1);
                    break;

                case 3:
                    break shop;

            }
        }
    }

    @Override
    public void displayCategories() {
        System.out.println("Categories: ");
        Set<Category> categories = categoryService.getCategories();

        for(Category i : categories){
            System.out.println(i.getId() + ". " + i.getName());
        }

        System.out.println("3. Exit");


    }

    @Override
    public void displayProducts(Category category) {
        Set<Product> products = category.getProducts();

        for(Product p : products){
            System.out.println(p.getId() + ". " + p.getName());
        }
    }

    @Override
    public void sortProducts(Category category) {
        Set<Product> products = category.getProducts();

        List<Product> ps = new ArrayList<Product>(products);


    }

    @Override
    public void addProductToCart() {

    }

    @Override
    public void adminMenu() {

    }

    @Override
    public void displayAllUsers() {
        System.out.println("The list of all users in our system: ");
        Set<User> users = userService.getUsers();

        for(User i : users){
            System.out.println("-------");
            System.out.println("Id: " + i.getId());
            displayUserInfo(i);
        }

    }

    @Override
    public void banUser(int id) {
        userService.deleteUserById(id);
    }

    @Override
    public boolean closeShop() {
        return false;
    }


}
