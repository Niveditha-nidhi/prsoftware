import java.util.ArrayList;
import java.util.Scanner;

class Product {
    private String name;
    private double price;
    private int quantity;
    private static final int RESTOCK_THRESHOLD = 10;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity += quantity;
    }

    public boolean isLowInStock() {
        return quantity <= RESTOCK_THRESHOLD;
    }
}

class Inventory {
    private ArrayList<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void updateProduct(String productName, int quantitySold) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                if (product.getQuantity() >= quantitySold) {
                    product.updateQuantity(-quantitySold);
                    System.out.println("Sale recorded successfully.");

                    if (product.isLowInStock()) {
                        System.out.println("Low in stock! Consider restocking " + product.getName() + ".");
                    }
                } else {
                    System.out.println("Insufficient stock for the sale.");
                }
                return;
            }
        }
        System.out.println("Product not found.");
    }

    public void generateReport() {
        System.out.println("\nInventory Report:");
        for (Product product : products) {
            System.out.println("Product: " + product.getName() +
                    ", Price: $" + product.getPrice() +
                    ", Quantity: " + product.getQuantity());
        }
    }
}

public class InventoryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Inventory inventory = new Inventory();

        while (true) {
            System.out.println("\nInventory Management System Menu:");
            System.out.println("1. Add Product");
            System.out.println("2. Record Sale");
            System.out.println("3. Generate Report");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter Product Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Product Price: $");
                    double price = scanner.nextDouble();
                    System.out.print("Enter Initial Quantity: ");
                    int quantity = scanner.nextInt();

                    Product newProduct = new Product(name, price, quantity);
                    inventory.addProduct(newProduct);
                    System.out.println("Product added successfully.");
                    break;

                case 2:
                    System.out.print("Enter Product Name for Sale: ");
                    String productName = scanner.nextLine();
                    System.out.print("Enter Quantity Sold: ");
                    int quantitySold = scanner.nextInt();

                    inventory.updateProduct(productName, quantitySold);
                    break;

                case 3:
                    inventory.generateReport();
                    break;

                case 4:
                    System.out.println("Exiting Inventory Management System. Goodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }
}
