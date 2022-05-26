package com.company.Services;

import com.company.Config.DatabaseConfiguration;
import com.company.Entities.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Service implements IService {
    private final UserService userService = UserService.getInstance();
    private final CategoryService categoryService = CategoryService.getInstance();
    private final OrderService orderService = OrderService.getInstance();
    private final ProductService productService = ProductService.getInstance();
    private final AddressService addressService = AddressService.getInstance();
    private final PromotionService promotionService = PromotionService.getInstance();
    private final DistributorService distributorService = DistributorService.getInstance();

    private static Service instance;

    private final Scanner scanner = new Scanner(System.in);

    private Service() {
    }

    public static Service getInstance() {
        if (instance == null)
            instance = new Service();
        return instance;
    }


    @Override
    public void mainPanel() throws SQLException {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
            userService.writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

        createTables();

//        A se comenta pana la linia 65 pentru a evita duplicates.

        categoryService.addCategory(new Category("Phones"));
        categoryService.addCategory(new Category("Laptops"));
        categoryService.addCategory(new Category("TV"));
        categoryService.addCategory(new Category("Tablets"));

        distributorService.addDistributor(new Distributor("Zing"));
        distributorService.addDistributor(new Distributor("Fastly"));
        distributorService.addDistributor(new Distributor("Lessgoo"));

        productService.addProduct(new Product("Iphone 13", "descriere iphone 13", 5, 3000, 1, 1));
        productService.addProduct(new Product("Samsung S20", "descriere samsung s20", 8, 2000, 1, 2));
        productService.addProduct(new Product("ASUS TUF A15", "descriere laptop", 10, 5000, 2, 2));
        productService.addProduct(new Product("Samsung LED X50", "descriere TV", 7, 12000,3, 2));
        productService.addProduct(new Product("NEO TABLET 20", "descriere tableta", 9, 2300, 4,3));
//

        User user = null;
        boolean login = false;


        dance:
        while (true) {

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

            shopMenu:
            while (true) {
                userMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        displayUserInfo(user);
                        break;
                    case 2:
                        editUserInfo(user);
                        break;
                    case 3:
                        shopMenu(user);
                        break;

                    default:
                        login = false;
                        break shopMenu;

                }
            }


        }


    }

    @Override
    public void createTables() {
        String createCategory = "CREATE TABLE IF NOT EXISTS category " +
                "(id int PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(30) NOT NULL," +
                "UNIQUE(name));";
        String createDistributors = "CREATE TABLE IF NOT EXISTS distributor" +
                "(id int PRIMARY KEY AUTO_INCREMENT," +
                "name varchar(30) NOT NULL);";
        String createProduct = "CREATE TABLE IF NOT EXISTS product " +
                "(id int PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(30) NOT NULL, " +
                "description varchar(30) NOT NULL," +
                "price float NOT NULL," +
                "stock int," +
                "category_id int NOT NULL," +
                "distributor_id int NOT NULL," +
                "FOREIGN KEY (category_id) REFERENCES category(id)," +
                "FOREIGN KEY (distributor_id) REFERENCES distributor(id));";
        String createUser = "CREATE TABLE IF NOT EXISTS user" +
                "(id INT PRIMARY KEY AUTO_INCREMENT," +
                "firstName varchar(30) NOT NULL," +
                "lastName varchar(30) NOT NULL," +
                "userName varchar(30) NOT NULL," +
                "password varchar(30) NOT NULL," +
                "role varchar(10) NOT NULL);";
        String createAddress = "CREATE TABLE IF NOT EXISTS address" +
                "(id int PRIMARY KEY AUTO_INCREMENT," +
                "country varchar(30) NOT NULL," +
                "city varchar(30) NOT NULL," +
                "street varchar(30) NOT NULL," +
                "user_id int NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES user(id));";
        String createOrder = "CREATE TABLE IF NOT EXISTS userOrder" +
                "(id int PRIMARY KEY AUTO_INCREMENT," +
                "order_date date NOT NULL," +
                "user_id int NOT NULL," +
                "product_id int NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES user(id)," +
                "FOREIGN KEY (product_id) REFERENCES product(id));";
        String createPromotions = "CREATE TABLE IF NOT EXISTS promotion" +
                "(id INT PRIMARY KEY AUTO_INCREMENT," +
                "code varchar(10) NOT NULL," +
                "discount int," +
                "user_id int NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES user(id));";


        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createCategory);
            stmt.execute(createDistributors);
            stmt.execute(createProduct);
            stmt.execute(createUser);
            stmt.execute(createAddress);
            stmt.execute(createOrder);
            stmt.execute(createPromotions);

        } catch (SQLException e) {
            e.printStackTrace();
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
        user.setRole("user");
        user = new User(user);
        userService.addUser(user);

        Address address = new Address();
        System.out.println("City: ");
        address.setCity(scanner.nextLine());
        System.out.println("Country: ");
        address.setCountry(scanner.nextLine());
        System.out.println("Street");
        address.setStreet(scanner.nextLine());
        address.setUser_id(user.getId());
        System.out.println(address.getUser_id());
        addressService.addAddress(address);

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

        Address address = addressService.getAddressByUserId(user.getId());

        System.out.println("Address: " + address.getCountry() + ", " + address.getCity() + ", "
                + address.getStreet());
    }

    @Override
    public void editUserInfo(User user) {

        System.out.println("Choose what field do you want to edit: ");

        edit:
        while (true) {
            System.out.println("1. Username ");
            System.out.println("2. First name ");
            System.out.println("3. Last name ");
            System.out.println("4. Delete your account.");
            System.out.println("5. Exit ");
            int choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {
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
                    System.out.println("You have deleted your account.");
                    addressService.deleteAddressById(user.getId());
                    userService.deleteUserById(user.getId());
                    break edit;
                case 5:
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
    public void shopMenu(User user){
        System.out.println("--------------------------");
        displayCategories();
        shop:
        while (true) {

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Category c = categoryService.getCategoryById(1);
                    displayProducts(user, c);
                    break;

                case 2:
                    Category c1 = categoryService.getCategoryById(2);
                    displayProducts(user, c1);
                    break;
                case 3:
                    Category c2 = categoryService.getCategoryById(3);
                    displayProducts(user, c2);
                    break;
                case 4:
                    Category c3 = categoryService.getCategoryById(4);
                    displayProducts(user, c3);
                    break;

                default:
                    break shop;

            }


        }
    }

    @Override
    public void displayCategories() {
        System.out.println("Categories: ");
        categoryService.getCategories();

        System.out.println("5. Exit");


    }

    @Override
    public void displayProducts(User user, Category category){
        System.out.println("--------------------------");

        productPanel:
        while (true) {
            ArrayList<Product> products =  productService.getProductsByCategoryId(category.getId());
            System.out.println("0. Sort products by price.");
            System.out.println("Type the id of the product you are adding to your cart.");
            System.out.println("Type \"exit\" to exit.");
            String choice = scanner.nextLine();


            switch (choice) {
                case "0":
                    List<Product> ps= productService.sortProducts(products);
                    for(Product i : ps) {
                        System.out.println("Id: " + i.getId());
                        System.out.println("Name: " + i.getName());
                        System.out.println("Description: " + i.getDescription());
                        System.out.println("Price: " + i.getPrice());
                        System.out.println("Stock: " + i.getStock());
                    }
                    break;

                case "exit":
                    break productPanel;
                default:
                    try {
                        addProductToCart(user, productService.getProductById(Integer.parseInt(choice)));
                    } catch (Error e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    @Override
    public void sortProducts(Category category) {


    }

    @Override
    public void addProductToCart(User user, Product product) {
        Order order = new Order(new Date(), user.getId(), product.getId());
        orderService.addOrder(order);
        System.out.println("Order added.");
    }

    public void removeOrder(User user, Order order) {
        orderService.deleteOrderById(order.getId());
        System.out.println("Order deleted.");
    }

    @Override
    public void adminMenu() {

    }

    @Override
    public void displayAllUsers() {
        System.out.println("The list of all users in our system: ");
        Set<User> users = userService.getUsers();

        for (User i : users) {
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
