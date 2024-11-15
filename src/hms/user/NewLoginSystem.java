package hms.user;

import java.util.Scanner;

public class NewLoginSystem {

    public static void main(String[] args) {
        String staffPath = "src/hms/data/Staff_List.csv";
        String patientPath = "src/hms/data/Patient_List.csv";
        User.loadUsersFromCsv(staffPath);
        User.loadUsersFromCsv(patientPath);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Hospital Management System ---");
            System.out.println("1. Log in");
            System.out.println("2. Forgot Password");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    handleLogin(scanner); // calling methods
                    break;
                case "2":
                    handleForgotPassword(scanner); 
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Method to handle the user login
    private static void handleLogin(Scanner scanner) {
        System.out.print("Enter User ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = User.login(id, password);
        if (user != null) {
            user.showMenu();  // Showing menu if user login successful
        }
    }

    // Method to handle the forget password
    private static void handleForgotPassword(Scanner scanner) {
        System.out.print("Enter your User ID: ");
        String id = scanner.nextLine();

        User user = User.getUserById(id);
        if (user == null) {
            System.out.println("User ID not found.");
            return;
        }
        
        // Using security question
        System.out.println("Security Question: " + user.getSecurityQuestion());
        System.out.print("Answer: ");
        String answer = scanner.nextLine();

        // Checking if answers the same
        if (!user.securityAnswer.equalsIgnoreCase(answer)) {
            System.out.println("Incorrect answer to the security question. Cannot reset password.");
            return;
        }

        // If the answer correct use the change password
        System.out.print("Enter your new password: ");
        String newPassword = scanner.nextLine();
        user.changePassword(newPassword);
        System.out.println("Your password has been reset successfully.");
    }
}
