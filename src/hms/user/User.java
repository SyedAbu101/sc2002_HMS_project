package hms.user;

import hms.util.ContactInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class User {
    protected String id;
    protected String name;
    protected String password;
    protected String role;

    private static Map<String, User> userDatabase = new HashMap<>();

    //constructor
    public User(String id, String name, String role) {
        this.id = id;
        this.name = name;
        this.password = "password"; // password as the default password
        this.role = role;
        userDatabase.put(id, this);
    }

    
    public static User login(String id, String password) {
        User user = userDatabase.get(id);
        if (user != null && user.password.equals(password)) {
            System.out.println("Login successful! Welcome, " + user.name);
            return user;
        } 
        else {
            System.out.println("Invalid ID or password. Please try again.");
            return null;
        }
    }

    //need to modify classes to be able to change their passwords after login
    public void changePassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password changed successfully.");
    }

    //abstract showMenu method
    public abstract void showMenu();

    //method for creating new users
    public static User createUser(String id, String name, String role, String dateOfBirth, String gender, String bloodType,  ContactInfo contactInfo) {
        switch (role.toLowerCase()) {
            case "patient":
                return new Patient(id, name, dateOfBirth, gender, bloodType, contactInfo);
            case "doctor":
                return new Doctor(id, name);
            case "pharmacist":
                return new Pharmacist(id, name);
            case "administrator":
                return new Administrator(id, name);
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    public static void loadUsersFromCsv(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] details = line.split(",");
                if (details.length == 7) {
                    // Patient data
                    String id = details[0].trim();
                    String name = details[1].trim();
                    String dateOfBirth = details[2].trim();
                    String gender = details[3].trim();
                    String bloodType = details[4].trim();
                    ContactInfo contactInfo = new ContactInfo(details[5].trim());
                    createUser(id, name, "patient", dateOfBirth, gender, bloodType, contactInfo);
                } else if (details.length == 6) {
                    // Staff data
                    String id = details[0].trim();
                    String name = details[1].trim();
                    String role = details[2].trim();
                    createUser(id, name, role, "", "","", null);
                } else {
                    System.out.println("Invalid entry in CSV: " + line);
                }
            }
            System.out.println("Users loaded successfully from CSV file.");
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String dataPath = "data/Staff_List.csv";
        loadUsersFromCsv(dataPath);
        String patientPath = "data/Patient_List.csv";
        loadUsersFromCsv(patientPath);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = login(id, password);
        if (user != null) 
        {
            user.showMenu(); 
        }

        scanner.close();
    }
}