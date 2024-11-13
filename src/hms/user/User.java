package hms.user;

import hms.util.ContactInfo;

import java.io.*;
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

    private String staffPath = "src/hms/data/Staff_List.csv";

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

    public abstract void showMenu();

    // Static method to get user by ID
    public static User getUserById(String id) {
        return userDatabase.get(id);
    }

    // Static method to remove user by ID
    public static User removeUserById(String id) {
        return userDatabase.remove(id);
    }

    // Static method to get all users
    public static Map<String, User> getAllUsers() {
        return userDatabase;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
        saveUsersToCSV(staffPath);
    }

    public void setRole(String role) {
        this.role = role;
        saveUsersToCSV(staffPath);
    }

    public void setPassword(String password) {
        this.password = password;
        saveUsersToCSV(staffPath);
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
        saveUsersToCSV(staffPath);
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
        saveUsersToCSV(staffPath);
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

    // Instance method to convert User to CSV format
    public String toCSV() {
        return String.join(",", id, name, role, password, securityQuestion, securityAnswer);
    }

    // Static method to save all staff to the CSV file
    public static void saveUsersToCSV(String staffFilePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(staffFilePath))) {
            bw.write("Staff ID,Name,Role,Gender,Age,Password,Security question,Security answer");
            bw.newLine();
            for (User user : userDatabase.values()) {
                if(!user.role.equalsIgnoreCase("patient")) {
                    bw.write(user.toCSV());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to the CSV file: " + e.getMessage());
        }
    }

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
                            new Doctor(id, name, gender, age, password, securityQuestion, securityAnswer);
                            break;
                        case "pharmacist":
                            new Pharmacist(id, name, gender, age, password, securityQuestion, securityAnswer);
                            break;
                        case "administrator":
                            new Administrator(id, name, gender, age, password, securityQuestion, securityAnswer);
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


