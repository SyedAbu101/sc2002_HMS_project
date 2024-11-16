package hms.user;

import hms.util.ContactInfo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class User {
    protected String id;
    protected String name;
    protected String password;
    protected String gender;
    protected String age;
    protected String role;
    protected String securityQuestion;
    protected String securityAnswer;

    // Additional fields for patient-specific data
    protected String dateOfBirth;
    protected String bloodType;
    protected String contactInfo;
    protected String pastDiagnosesAndTreatments;

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

    public String getName() { return name; }

    // Static method to get user by ID
    public static User getUserById(String id) {
        return userDatabase.get(id);
    }

    // Static method to remove user by ID
    public static User removeUserById(String id) {
        return userDatabase.remove(id);
    }

    // Static method for getter for all users
    public static Map<String, User> getAllUsers() {
        return userDatabase;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getGender() { return gender; }

    public String getAge() { return age; }

    // Setter methods
    public void setName(String name) {
        this.name = name;
        saveUsersToCSV(staffPath);
    }

    public void setRole(String role) {
        this.role = role;
        saveUsersToCSV(staffPath);
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
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
        // Determine which file to save based on role
        String filePath = this.role.equalsIgnoreCase("patient") ? "src/hms/data/Patient_List.csv" : "src/hms/data/Staff_List.csv";
        // Save updated users to the correct CSV file
        saveUsersToCSV(filePath);
        // Reload users from the CSV to update in-memory database
        loadUsersFromCsv(filePath);
    }

    // Instance method to convert User to CSV format
    public String toCSV() {
        if (this.role.equalsIgnoreCase("patient")) {
            // Use the stored values for the patient fields
            return String.join(",",
                    id,
                    name,
                    dateOfBirth,
                    gender,
                    bloodType,
                    contactInfo,
                    password,
                    pastDiagnosesAndTreatments,
                    securityQuestion,
                    securityAnswer
            );
        } else {
            // Staff format
            return String.join(",",
                    id,
                    name,
                    role,
                    gender,
                    age,
                    password,
                    securityQuestion,
                    securityAnswer
            );
        }
    }

    // Static method to save all users to the CSV file
    public static void saveUsersToCSV(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)))) {
            // Write header based on file type
            if (filePath.contains("Patient_List")) {
                bw.write("Patient ID,Name,Date of Birth,Gender,Blood Type,Contact Information,Password,Past Diagnoses and Treatments,Security Question,Security Answer");
            } else {
                bw.write("Staff ID,Name,Role,Gender,Age,Password,Security Question,Security Answer");
            }
            bw.newLine();
            // Write each user to the CSV file based on the role
            for (User user : userDatabase.values()) {
                if ((filePath.contains("Patient_List") && user.role.equalsIgnoreCase("patient")) ||
                        (filePath.contains("Staff_List") && !user.role.equalsIgnoreCase("patient"))) {
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

                if (filePath.contains("Patient_List") && details.length >= 10) { // Patient file format
                    String id = details[0].trim();
                    String name = details[1].trim();
                    String dateOfBirth = details[2].trim();
                    String gender = details[3].trim();
                    String bloodType = details[4].trim();
                    String contactInfo = details[5].trim();
                    String password = details[6].trim();
                    String pastDiagnosesAndTreatments = details[7].trim();
                    String securityQuestion = details[8].trim();
                    String securityAnswer = details[9].trim();

                    User patient = new Patient(id, name, password, securityQuestion, securityAnswer, dateOfBirth, gender, bloodType, new ContactInfo(contactInfo), List.of(pastDiagnosesAndTreatments));
                    patient.dateOfBirth = dateOfBirth; // Store patient-specific fields
                    patient.gender = gender;
                    patient.bloodType = bloodType;
                    patient.contactInfo = contactInfo;
                    patient.pastDiagnosesAndTreatments = pastDiagnosesAndTreatments;
                    userDatabase.put(id, patient);

                } else if (filePath.contains("Staff_List") && details.length == 8) { // Staff file format
                    String id = details[0].trim();
                    String name = details[1].trim();
                    String role = details[2].trim();
                    String gender = details[3].trim();
                    String age = details[4].trim();
                    String password = details[5].trim();
                    String securityQuestion = details[6].trim();
                    String securityAnswer = details[7].trim();

                    User newUser;
                    switch (role.toLowerCase()) {
                        case "doctor":
                            newUser = new Doctor(id, name, gender, age, password, securityQuestion, securityAnswer);
                            break;
                        case "pharmacist":
                            newUser = new Pharmacist(id, name, gender, age, password, securityQuestion, securityAnswer);
                            break;
                        case "administrator":
                            newUser = new Administrator(id, name, gender, age, password, securityQuestion, securityAnswer);
                            break;
                        default:
                            System.out.println("Unknown role: " + role);
                            continue; // Skip unknown roles
                    }
                    if (newUser != null) {
                        newUser.setAge(age);
                        newUser.setGender(gender);
                        userDatabase.put(id, newUser);
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
