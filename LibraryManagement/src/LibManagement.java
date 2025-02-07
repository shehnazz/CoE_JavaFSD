import java.io.*;
import java.util.*;
import java.util.concurrent.*;

// Book class
class Book {
    private String title;
    private String author;
    private String ISBN;
    private boolean isAvailable = true;

    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getISBN() { return ISBN; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { this.isAvailable = available; }
}

// User class
class User {
    private String name;
    private String userID;
    private List<Book> borrowedBooks = new ArrayList<>();

    public User(String name, String userID) {
        this.name = name;
        this.userID = userID;
    }

    public String getName() { return name; }
    public String getUserID() { return userID; }
    public List<Book> getBorrowedBooks() { return borrowedBooks; }
    public void borrowBook(Book book) { borrowedBooks.add(book); }
    public void returnBook(Book book) { borrowedBooks.remove(book); }
}

// ILibrary interface
interface ILibrary {
    void borrowBook(String ISBN, String userID) throws Exception;
    void returnBook(String ISBN, String userID) throws Exception;
    void reserveBook(String ISBN, String userID) throws Exception;
    Book searchBook(String title);
}

// Custom exceptions
class BookNotFoundException extends Exception {
    public BookNotFoundException(String message) { super(message); }
}

class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) { super(message); }
}

class MaxBooksAllowedException extends Exception {
    public MaxBooksAllowedException(String message) { super(message); }
}

// Abstract Library System
abstract class LibrarySystem implements ILibrary {
    protected List<Book> books = new CopyOnWriteArrayList<>();
    protected List<User> users = new CopyOnWriteArrayList<>();

    public abstract void addBook(Book book);
    public abstract void addUser(User user);
}

// Library Manager
class LibraryManager extends LibrarySystem {
    private final int MAX_BORROWED_BOOKS = 3;
    private ExecutorService executor = Executors.newFixedThreadPool(5);

    @Override
    public void addBook(Book book) { books.add(book); }

    @Override
    public void addUser(User user) { users.add(user); }

    @Override
    public void borrowBook(String ISBN, String userID) throws Exception {
        executor.execute(() -> {
            try {
                User user = users.stream().filter(u -> u.getUserID().equals(userID)).findFirst().orElseThrow(() -> new UserNotFoundException("User not found"));
                if (user.getBorrowedBooks().size() >= MAX_BORROWED_BOOKS) {
                    throw new MaxBooksAllowedException("User has reached borrowing limit");
                }
                Book book = books.stream().filter(b -> b.getISBN().equals(ISBN) && b.isAvailable()).findFirst().orElseThrow(() -> new BookNotFoundException("Book not available"));
                book.setAvailable(false);
                user.borrowBook(book);
                System.out.println("Book borrowed successfully");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        });
    }

    @Override
    public void returnBook(String ISBN, String userID) throws Exception {
        executor.execute(() -> {
            try {
                User user = users.stream().filter(u -> u.getUserID().equals(userID)).findFirst().orElseThrow(() -> new UserNotFoundException("User not found"));
                Book book = user.getBorrowedBooks().stream().filter(b -> b.getISBN().equals(ISBN)).findFirst().orElseThrow(() -> new BookNotFoundException("Book not found in user possession"));
                book.setAvailable(true);
                user.returnBook(book);
                System.out.println("Book returned successfully");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        });
    }

    @Override
    public void reserveBook(String ISBN, String userID) throws Exception {
        System.out.println("Reservation feature is under development.");
    }

    @Override
    public Book searchBook(String title) {
        return books.stream().filter(b -> b.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
    }
}

// Main class
public class LibManagement {
    public static void main(String[] args) {
        LibraryManager libManager = new LibraryManager();

        // Adding books and users
        libManager.addBook(new Book("Java Programming", "John Doe", "1234"));
        libManager.addBook(new Book("Data Structures", "Jane Smith", "5678"));
        libManager.addUser(new User("Alice", "U001"));
        libManager.addUser(new User("Bob", "U002"));

        // Simulating borrowing and returning books
        try {
            libManager.borrowBook("1234", "U001");
            Thread.sleep(2000); // Simulating time delay
            libManager.returnBook("1234", "U001");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
