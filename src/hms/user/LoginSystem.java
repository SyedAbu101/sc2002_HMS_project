package hms.user;

import hms.user.*;
import java.util.Scanner;

public class LoginSystem {
    public static void main(String[] args) {
        String staffPath = "src/hms/data/Staff_List.csv";
        String patientPath = "src/hms/data/Patient_List.csv";
        User.loadUsersFromCsv(staffPath);
        User.loadUsersFromCsv(patientPath);

        Scanner scanner = new Scanner(System.in);
        while (true) { //super loop for login system
            System.out.println("\n--- Hospital Management System Login ---");
            System.out.print("Enter User ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            User user = User.login(id, password);
            if (user != null) user.showMenu();
            //null case will be handled by login method in User.java

            System.out.print("Log in again? (yes/no): ");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("yes")) 
            {
                System.out.println("Goodbye!");
                break;
            }
        }
        scanner.close();
    }
    
    //might need to add more methods to handle password reset
}
