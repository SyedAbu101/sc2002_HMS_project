package hms.user;

import hms.util.ContactInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class User {
    protected String id;
    protected String name;
    protected String password;
    protected String role;
    protected String securityQuestion;
    protected String securityAnswer;

    private static Map<String, User> userDatabase = new HashMap<>();

    public User(String id, String name, String role, String password, String securityQuestion, String securityAnswer) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        userDatabase.put(id, this);
    }

    public static User getUserById(String id) {
        return userDatabase.get(id);
    }

    public static User login(String id, String password) {
        User user = userDatabase.get(id);
        if (user != null && user.password.equals(password)) {
            System.out.println("Login successful! Welcome, " + user.name);
            return user;
        } else {
            System.out.println("Invalid ID or password. Please try again.");
            return null;
        }
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password changed successfully.");
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public abstract void showMenu();

    public static void loadUsersFromCsv(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }

                String[] details = line.split(",");
                if (details.length == 10) { // Has 10 variables
                    String id = details[0].trim();
                    String name = details[1].trim();
                    String dateOfBirth = details[2].trim();
                    String gender = details[3].trim();
                    String bloodType = details[4].trim();
                    ContactInfo contactInfo = new ContactInfo(details[5].trim());
                    String password = details[6].trim();
                    List<String> pastDiagnosesAndTreatments = List.of(details[7].trim().split(";"));
                    String securityQuestion = details[8].trim();
                    String securityAnswer = details[9].trim();
                    new Patient(id, name, password, securityQuestion, securityAnswer, dateOfBirth, gender, bloodType, contactInfo, pastDiagnosesAndTreatments);

                } else if (details.length == 8) { // has 8 variables
                    String id = details[0].trim();
                    String name = details[1].trim();
                    String role = details[2].trim();
                    String gender = details[3].trim();
                    String age = details[4].trim();
                    String password = details[5].trim();
                    String securityQuestion = details[6].trim();
                    String securityAnswer = details[7].trim();

                    switch (role.toLowerCase()) {
                        case "doctor":
                            new Doctor(id, name, password, securityQuestion, securityAnswer);
                            break;
                        case "pharmacist":
                            new Pharmacist(id, name, password, securityQuestion, securityAnswer);
                            break;
                        case "administrator":
                            new Administrator(id, name, password, securityQuestion, securityAnswer);
                            break;
                        default:
                            System.out.println("Unknown role: " + role);
                    }
                } else {
                    System.out.println("Invalid entry in CSV: " + line);
                }
            }
            System.out.println("Users loaded successfully from CSV file.");
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }
}

