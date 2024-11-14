package hms.user;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import hms.appointment.AppointmentOutcomeRecord;
import hms.inventory.*;

public class Pharmacist extends User {
    private String gender;
    private String age;
    private InventoryManager inventoryManager;

    //constructor
    public Pharmacist(String id, String name, String gender, String age, String password, String securityQuestion, String securityAnswer) {
        super(id, name, "pharmacist", password, securityQuestion, securityAnswer);
        this.gender = gender;
        this.age = age;
        this.inventoryManager = new InventoryManager();
    }
    
    //view appointmentOutcomeRecord method
    public void viewAppointmentOutcomeRecord(AppointmentOutcomeRecord record) {
        System.out.println("Appointment Outcome Record:");
        System.out.println(record);
    }

    //updatePrescriptionStatus method
    public void updatePrescriptionStatus(String appointmentId, String status) {
        System.out.println("Updating prescription status for Appointment ID: " + appointmentId);
        // need to implement logic here
        System.out.println("Prescription status updated to: " + status);
    }

    //viewInventory method
    public void viewInventory() {
        System.out.println("Viewing medication inventory...");
        List<Medicine> medicines = inventoryManager.getMedicines();
        for (Medicine medicine : medicines) {
            System.out.println(medicine);
        }
    }

    //submitReplenishmentRequest method
    private void submitReplenishmentRequest(String medicineName, int quantity) {
        try (FileWriter writer = new FileWriter("src/hms/data/Replenishment_Requests.csv", true)) {
            writer.append(id).append(",")
                  .append(medicineName).append(",")
                  .append(String.valueOf(quantity)).append("\n");
            System.out.println("Replenishment request submitted successfully for Medication: " + medicineName + ", Quantity: " + quantity);
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    //showMenu method, might need to add an option for changing password after login 
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nPharmacist Menu:");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Change Password");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Appointment ID to view outcome record: ");
                    String appointmentId = scanner.nextLine();
                    AppointmentOutcomeRecord record = getAppointmentOutcomeRecord(appointmentId);
                    if (record != null) {
                        viewAppointmentOutcomeRecord(record);
                    } else {
                        System.out.println("No record found for Appointment ID: " + appointmentId);
                    }
                    break;
                case 2:
                    System.out.print("Enter Appointment ID to update prescription status: ");
                    appointmentId = scanner.nextLine();
                    System.out.print("Enter new status (e.g., dispensed): ");
                    String status = scanner.nextLine();
                    updatePrescriptionStatus(appointmentId, status);
                    break;
                case 3:
                    viewInventory();
                    break;
                case 4:
                    System.out.print("Enter medicine name for replenishment: ");
                    String medicineName = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    submitReplenishmentRequest(medicineName, quantity);
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
                    System.out.println("Invalid option. Please try again.");
            }
        }
    } //showMenu ends here

    //method to get an appointment outcome record
    private AppointmentOutcomeRecord getAppointmentOutcomeRecord(String appointmentId) {
        //need to update with actual logic to get an appointment outcome by ID
        return null; 
    }
}
