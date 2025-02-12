package FeeReportGeneration;
import java.util.Scanner;

public class FeeReportApp {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Admin Login\n2. Accountant Login\n3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: adminLogin();
                case 2: accountantLogin();
                case 3: {
                    System.out.println("Exiting...");
                    return;
                }
                default: System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static void adminLogin() {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (AdminDAO.validateAdmin(username, password)) {
            System.out.println("Admin Login Successful.");
            System.out.print("Enter Accountant Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Phone: ");
            String phone = scanner.nextLine();
            System.out.print("Enter Password: ");
            String accPassword = scanner.nextLine();

            if (AccountantDAO.addAccountant(name, email, phone, accPassword))
                System.out.println("Accountant Added Successfully.");
        } else {
            System.out.println("Invalid Credentials.");
        }
    }

    private static void accountantLogin() {
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (AccountantDAO.validateAccountant(email, password)) {
            System.out.println("Accountant Login Successful.");
            StudentDAO.checkDueFee();
        } else {
            System.out.println("Invalid Credentials.");
        }
    }
}
