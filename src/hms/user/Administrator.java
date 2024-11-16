package hms.user;

import hms.appointment.Appointment;
import hms.appointment.AppointmentManager;
import hms.inventory.*;

import java.io.*;
import java.util.*;

public class Administrator extends User {
    private String gender;
    private String age;
    private InventoryManager inventoryManager;
    private AppointmentManager appointmentManager;

    private String staffPath = "src/hms/data/Staff_List.csv";
    private String replenishmentPath = "src/hms/data/Pending_Replenishment_Requests.csv";
    private String medicinePath = "src/hms/data/Medicine_List.csv";

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

                    if (searchStaff(newStaffId)) {
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
                    System.out.println("Enter attribute to filter by (Staff ID, Name, Role, Gender, Age, Password, Security question, Security answer): ");
                    String attribute = scanner.nextLine().trim();

                    // List of valid attributes
                    List<String> validAttributes = Arrays.asList("Staff ID", "Name", "Role", "Gender", "Age", "Password", "Security question", "Security answer");

                    if (!validAttributes.contains(attribute)) {
                        System.out.println("Error: Invalid attribute");
                        return;
                    }

                    System.out.println("Enter value for " + attribute + ": ");
                    String value = scanner.nextLine().trim();

                    System.out.println("Listing all staff members filtered by " + attribute + " = " + value + ":");
                    for (User user : User.getAllUsers().values()) {
                        if (!user.role.equalsIgnoreCase("patient") && userMatchesAttribute(user, attribute, value)) {
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

    // Checks whether a particular user has that specified attribute that admin wants
    private boolean userMatchesAttribute(User user, String attribute, String value) {
        switch (attribute) {
            case "Staff ID":
                return user.id.equals(value);
            case "Name":
                return user.name.equals(value);
            case "Role":
                return user.role.equals(value);
            case "Gender":
                return user.gender.equals(value);
            case "Age":
                return user.age.equals(value);
            case "Password":
                return user.password.equals(value);
            case "Security question":
                return user.securityQuestion.equals(value);
            case "Security answer":
                return user.securityAnswer.equals(value);
            default:
                return false;
        }
    }

    //viewAppointmentDetails method
    public void viewAppointmentsDetails() {
        System.out.println("Viewing all appointment details:");
        List<Appointment> appointments = appointmentManager.getAllAppointments();
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }

    //manageMedicationInventory method
    public void manageMedicationInventory() {
        Scanner scanner = new Scanner(System.in);
        InventoryManager inventoryManager = new InventoryManager();

        while (true) {
            System.out.println("\nMedication Inventory Management");
            System.out.println("1. View medication inventory");
            System.out.println("2. Add medication");
            System.out.println("3. Remove medication");
            System.out.println("4. Update medication stock");
            System.out.println("5. Update low stock alert level");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // View medication inventory
                    List<Medicine> medicines = inventoryManager.getMedicines();
                    for (Medicine medicine : medicines) {
                        System.out.println(medicine);
                    }
                    break;

                case 2:
                    // Add medication
                    try {
                        System.out.print("Enter medicine name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter stock: ");
                        int stock = scanner.nextInt();
                        System.out.print("Enter low stock alert level: ");
                        int lowStockAlertLevel = scanner.nextInt();

                        if (inventoryManager.addMedicine(name, stock, lowStockAlertLevel)) {
                            System.out.println("New medication: " + name + " with stock level: " + stock + " and low stock alert level: " + lowStockAlertLevel + " added");
                            break;
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter numeric values for stock and low stock alert level.");
                        scanner.nextLine(); // Consume the invalid input
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                        break;
                    } catch (Exception e) {
                        System.out.println("An unexpected error occurred: " + e.getMessage());
                        break;
                    }

                case 3:
                    // Remove medication
                    System.out.print("Enter medicine name to remove: ");
                    String removeName = scanner.nextLine();
                    inventoryManager.removeMedicine(removeName);
                    break;

                case 4:
                    // Update medication stock
                    System.out.print("Enter medicine name to update stock: ");
                    String updateStockName = scanner.nextLine();
                    System.out.print("Enter new stock level: ");
                    int newStock = scanner.nextInt();
                    scanner.nextLine();
                    inventoryManager.updateStock(updateStockName, newStock);
                    break;

                case 5:
                    // Update low stock alert level
                    System.out.print("Enter medicine name to update alert level: ");
                    String updateAlertName = scanner.nextLine();
                    System.out.print("Enter new low stock alert level: ");
                    int newAlertLevel = scanner.nextInt();
                    scanner.nextLine();
                    Medicine medicineToUpdate = inventoryManager.getMedicineByName(updateAlertName);
                    if (medicineToUpdate != null) {
                        medicineToUpdate.setLowStockAlertLevel(newAlertLevel);
                        inventoryManager.saveMedicinesToCSV(medicinePath);
                        System.out.println("Low stock alert level updated successfully for: " + updateAlertName);
                    } else {
                        System.out.println("Medicine not found: " + updateAlertName);
                    }
                    break;

                case 6:
                    // Exit
                    System.out.println("Exiting Medication Inventory Management.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    //approveReplenishmentRequests
    public void approveReplenishmentRequests() {
        System.out.println("Approving replenishment requests...");
        try (BufferedReader reader = new BufferedReader(new FileReader(replenishmentPath))) {
            List<String> approvedRequests = new ArrayList<>();
            List<String> remainingRequests = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Pending Replenishment Request: " + line);
                System.out.print("Approve this request? (yes/no): ");
                Scanner scanner = new Scanner(System.in);
                String response = scanner.nextLine().trim().toLowerCase();
                if (response.equals("yes")) {
                    approvedRequests.add(line);
                } else {
                    System.out.println("Request not approved.");
                }
            }

            // Write approved requests to final inventory and update stock accordingly
            for (String request : approvedRequests) {
                String[] requestDetails = request.split(",");
                String medicineName = requestDetails[1];
                int quantity = Integer.parseInt(requestDetails[2]);
                if(inventoryManager.getMedicineByName(medicineName) == null) {
                    System.out.println("Invalid medicine, will not update this replenishment");
                    continue;
                }
                inventoryManager.updateStock(medicineName, inventoryManager.getMedicineByName(medicineName).getStock() + quantity);
                System.out.println("Request approved.");
            }
            // Rewrite replenishmentPath with only the requests that were not processed
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(replenishmentPath))) {
                for (String remainingRequest : remainingRequests) {
                    writer.write(remainingRequest);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing replenishment requests: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error reading replenishment requests: " + e.getMessage());
        }
}

    //showMenu method
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nAdministrator Menu:");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointments Details");
            System.out.println("3. View and Manage Medication Inventory");
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
                    manageMedicationInventory();
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
