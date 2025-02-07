import java.io.*;
import java.util.*;
import java.util.concurrent.*;

// Product class
class Product {
    private String productID;
    private String name;
    private int quantity;
    private Location location;

    public Product(String productID, String name, int quantity, Location location) {
        this.productID = productID;
        this.name = name;
        this.quantity = quantity;
        this.location = location;
    }

    public String getProductID() { return productID; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public Location getLocation() { return location; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}

// Location class
class Location {
    private int aisle, shelf, bin;

    public Location(int aisle, int shelf, int bin) {
        this.aisle = aisle;
        this.shelf = shelf;
        this.bin = bin;
    }

    public String toString() {
        return "Aisle: " + aisle + ", Shelf: " + shelf + ", Bin: " + bin;
    }
}

// Order class
class Order implements Comparable<Order> {
    private String orderID;
    private List<String> productIDs;
    private Priority priority;

    public enum Priority { STANDARD, EXPEDITED }

    public Order(String orderID, List<String> productIDs, Priority priority) {
        this.orderID = orderID;
        this.productIDs = productIDs;
        this.priority = priority;
    }

    public String getOrderID() { return orderID; }
    public List<String> getProductIDs() { return productIDs; }
    public Priority getPriority() { return priority; }

    @Override
    public int compareTo(Order o) {
        return this.priority.compareTo(o.priority);
    }
}

// Custom exceptions
class OutOfStockException extends Exception {
    public OutOfStockException(String message) { super(message); }
}

class InvalidLocationException extends Exception {
    public InvalidLocationException(String message) { super(message); }
}

// Inventory Manager
class InventoryManager {
    private Map<String, Product> products = new ConcurrentHashMap<>();
    private PriorityBlockingQueue<Order> orderQueue = new PriorityBlockingQueue<>();

    public synchronized void addProduct(Product product) {
        products.put(product.getProductID(), product);
    }

    public synchronized void processOrder(Order order) {
        try {
            for (String productID : order.getProductIDs()) {
                if (!products.containsKey(productID)) {
                    throw new InvalidLocationException("Product " + productID + " not found.");
                }
                Product product = products.get(productID);
                if (product.getQuantity() == 0) {
                    throw new OutOfStockException("Product " + productID + " is out of stock.");
                }
                product.setQuantity(product.getQuantity() - 1);
            }
            System.out.println("Order " + order.getOrderID() + " processed successfully.");
        } catch (Exception e) {
            System.out.println("Error processing order: " + e.getMessage());
        }
    }

    public void processOrders() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        while (!orderQueue.isEmpty()) {
            executor.execute(() -> processOrder(orderQueue.poll()));
        }
        executor.shutdown();
    }

    public void addOrder(Order order) {
        orderQueue.add(order);
    }
}

// Main class
public class WareHouse {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InventoryManager inventoryManager = new InventoryManager();

        // Taking user input for products
        System.out.println("Enter number of products to add:");
        int productCount = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < productCount; i++) {
            System.out.println("Enter product ID, name, quantity, aisle, shelf, bin:");
            String productID = scanner.next();
            String name = scanner.next();
            int quantity = scanner.nextInt();
            int aisle = scanner.nextInt();
            int shelf = scanner.nextInt();
            int bin = scanner.nextInt();
            inventoryManager.addProduct(new Product(productID, name, quantity, new Location(aisle, shelf, bin)));
        }

        // Taking user input for orders
        System.out.println("Enter number of orders to process:");
        int orderCount = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < orderCount; i++) {
            System.out.println("Enter order ID, number of products, priority (STANDARD or EXPEDITED):");
            String orderID = scanner.next();
            int numProducts = scanner.nextInt();
            List<String> productIDs = new ArrayList<>();
            for (int j = 0; j < numProducts; j++) {
                productIDs.add(scanner.next());
            }
            Order.Priority priority = Order.Priority.valueOf(scanner.next().toUpperCase());
            inventoryManager.addOrder(new Order(orderID, productIDs, priority));
        }

        // Processing orders
        inventoryManager.processOrders();
        scanner.close();
    }
}
