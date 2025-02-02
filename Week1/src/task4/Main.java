package task4;

public class Main {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        userManager.addUser("Priya", "priya@example.com");
        userManager.addUser("Sri", "sri@example.com");

        String filename = "users.txt";

        userManager.saveUsersToFile(filename);
        userManager.loadUsersFromFile(filename);
        userManager.displayUsers();
    }
}