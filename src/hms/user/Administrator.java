package hms.user;

import hms.appointment.Appointment;
import hms.appointment.AppointmentManager;
import hms.inventory.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Administrator extends User {
    private String gender;
    private String age;
    private InventoryManager inventoryManager;
    private AppointmentManager appointmentManager;

    private String staffPath = "src/hms/data/Staff_List.csv";

    //constructor
    public Administrator(String id, String name, String gender, String age, String password, String securityQuestion, String securityAnswer) {
        super(id, name, "administrator", password, securityQuestion, securityAnswer);
        this.gender = gender;
        this.age = age;
        this.inventoryManager = new InventoryManager();
        this.appointmentManager = new AppointmentManager();
    }

    // Method to manage staff
    public void manageStaff() {
        User newUser;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Managing hospital staff...");
            System.out.println("Enter 'add' to add staff, 'update' to update staff, 'remove' to remove staff, 'list' to list all staff, or 'exit' to quit:");
            String action = scanner.nextLine().trim().toLowerCase();

            switch (action) {
                case "add":
                    System.out.print("Enter new staff ID: ");
                    String newStaffId = scanner.nextLine().trim();

                    if(searchStaff(newStaffId)) {
                        System.out.println("Staff ID already in use, please choose a new one");
                        break;
                    }

                    System.out.print("Enter new staff name: ");
                    String newStaffName = scanner.nextLine().trim();

                    System.out.print("Enter new staff role: ");
                    String newStaffRole = scanner.nextLine().trim();

                    System.out.print("Enter new staff gender: ");
                    String newStaffGender = scanner.nextLine().trim();

                    System.out.print("Enter new staff age: ");
                    String newStaffAge = scanner.nextLine().trim();

                    System.out.print("Enter new staff password: ");
                    String newStaffPassword = scanner.nextLine().trim();

                    System.out.print("Enter new staff security question: ");
                    String newStaffSecurityQuestion = scanner.nextLine().trim();

                    System.out.print("Enter new staff security answer: ");
                    String newStaffSecurityAnswer = scanner.nextLine().trim();

                    if (!newStaffId.isEmpty() && !newStaffName.isEmpty() && !newStaffRole.isEmpty() && !newStaffGender.isEmpty() && !newStaffAge.isEmpty() && !newStaffPassword.isEmpty() && !newStaffSecurityQuestion.isEmpty() && !newStaffSecurityAnswer.isEmpty()) {
                        switch (newStaffRole.toLowerCase()) {
                            case "doctor":
                                newUser = new Doctor(newStaffId, newStaffName, newStaffGender, newStaffAge, newStaffPassword, newStaffSecurityQuestion, newStaffSecurityAnswer);
                                break;
                            case "pharmacist":
                                newUser = new Pharmacist(newStaffId, newStaffName, newStaffGender, newStaffAge, newStaffPassword, newStaffSecurityQuestion, newStaffSecurityAnswer);
                                break;
                            case "administrator":
                                newUser = new Administrator(newStaffId, newStaffName, newStaffGender, newStaffAge, newStaffPassword, newStaffSecurityQuestion, newStaffSecurityAnswer);
                                break;
                            default:
                                System.out.println("Unknown role: " + newStaffRole);
                                return;
                        }
                        if (newUser != null) {
                            newUser.setAge(newStaffAge);
                            newUser.setGender(newStaffGender);
                            User.saveUsersToCSV(staffPath);
                        }
                        System.out.println("New staff added successfully.");
                    } else {
                        System.out.println("All fields are required to add a new staff member.");
                    }
                    break;

                case "update":
                    System.out.print("Enter the ID of the staff member to update: ");
                    String updateId = scanner.nextLine().trim();
                    User userToUpdate = User.getUserById(updateId);
                    if (userToUpdate != null) {
                        System.out.print("Enter new name (leave blank to keep current): ");
                        String newName = scanner.nextLine().trim();
                        if (!newName.isEmpty()) {
                            userToUpdate.setName(newName);
                        }

                        System.out.print("Enter new role (leave blank to keep current): ");
                        String newRole = scanner.nextLine().trim();
                        if (!newRole.isEmpty()) {
                            userToUpdate.setRole(newRole);
                        }

                        System.out.print("Enter new password (leave blank to keep current): ");
                        String newPassword = scanner.nextLine().trim();
                        if (!newPassword.isEmpty()) {
                            userToUpdate.setPassword(newPassword);
                        }

                        System.out.print("Enter new security question (leave blank to keep current): ");
                        String newSecurityQuestion = scanner.nextLine().trim();
                        if (!newSecurityQuestion.isEmpty()) {
                            userToUpdate.setSecurityQuestion(newSecurityQuestion);
                        }

                        System.out.print("Enter new security answer (leave blank to keep current): ");
                        String newSecurityAnswer = scanner.nextLine().trim();
                        if (!newSecurityAnswer.isEmpty()) {
                            userToUpdate.setSecurityAnswer(newSecurityAnswer);
                        }

                        System.out.println("Staff updated successfully.");
                    } else {
                        System.out.println("Staff member not found.");
                    }
                    break;

                case "remove":
                    System.out.print("Enter the ID of the staff member to remove: ");
                    String removeId = scanner.nextLine().trim();
                    User removedUser = User.removeUserById(removeId);
                    if (removedUser != null) {
                        System.out.println("Staff removed successfully.");
                        User.saveUsersToCSV(staffPath);
                    } else {
                        System.out.println("Staff member not found.");
                    }
                    break;

                case "list":
                    System.out.println("Listing all staff members:");
                    for (User user : User.getAllUsers().values()) {
                        if(!user.role.equalsIgnoreCase("patient")) {
                            System.out.println(user.toCSV());
                        }
                    }
                    break;

                case "exit":
                    System.out.println("Exiting staff management.");
                    return;

                default:
                    System.out.println("Invalid action. Please try again.");
                    break;
            }
        }
    }

    // Search through Staff_List.csv for a String, returns true if found, else false if not found
    public boolean searchStaff(String searchString) {
        try (BufferedReader br = new BufferedReader(new FileReader(staffPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip the header line
                if (line.startsWith("Staff ID,Name,Role,Password,Security question,Security answer")) {
                    continue;
                }
                if (line.contains(searchString)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
        return false;
    }

    //viewAppointmentDetails method
    public void viewAppointmentsDetails() {
        System.out.println("Viewing all appointment details:");
        List<Appointment> appointments = appointmentManager.getAllAppointments();
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }

    //viewMedicationInventory method
    public void viewMedicationInventory() {
        System.out.println("View medication inventory...");
        List<Medicine> medicines = inventoryManager.getMedicines();
        for (Medicine medicine : medicines) {
            System.out.println(medicine);
        }
    }

    //approveReplenishmentRequests
    public void approveReplenishmentRequests() {
        System.out.println("Approving replenishment requests...");
        //need to add logic to approve or disapprove replenishment requests
    }

    //showMenu method
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nAdministrator Menu:");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointments Details");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Change Password");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manageStaff();
                    break;
                case 2:
                    viewAppointmentsDetails();
                    break;
                case 3:
                    viewMedicationInventory();
                    break;
                case 4:
                    approveReplenishmentRequests();
                    break;
                case 5:
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    changePassword(newPassword);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    } //showMenu method ends here
}
