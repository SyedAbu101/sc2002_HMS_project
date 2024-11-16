package hms.user;

import hms.inventory.*;
import hms.appointment.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Pharmacist extends User {
    private String gender;
    private String age;
    private InventoryManager inventoryManager;
    private AppointmentManager appointmentManager;

    //constructor
    public Pharmacist(String id, String name, String gender, String age, String password, String securityQuestion, String securityAnswer) {
        super(id, name, "pharmacist", password, securityQuestion, securityAnswer);
        this.gender = gender;
        this.age = age;
        this.inventoryManager = new InventoryManager();
        this.appointmentManager = new AppointmentManager();
    }
    
  //viewAppointmentOutcomeRecord method
    public void viewAppointmentOutcomeRecord(String appointmentId) {
        if (appointmentManager == null) {
            this.appointmentManager = new AppointmentManager();
        }
        for (AppointmentOutcomeRecord outcome : AppointmentManager.appointmentOutcomes) {
            if (outcome.getAppointmentId().equals(appointmentId)) {
                System.out.println("Appointment Outcome Details:");
                System.out.println("Appointment ID: " + outcome.getAppointmentId());
                System.out.println("Patient ID: " + outcome.getPatientId());
                System.out.println("Service Type: " + outcome.getServiceType());
                System.out.println("Medication Prescribed: " + outcome.getMedicationName());
                System.out.println("Notes: " + outcome.getNotes());
                return;
            }
        }
        System.out.println("No outcome record found for Appointment ID: " + appointmentId);
    }
    
  //updatePrescriptionStatus method
    public void updatePrescriptionStatus(String appointmentId, String status) {
        System.out.println("Updating prescription status for Appointment ID: " + appointmentId);
        for (AppointmentOutcomeRecord outcome : AppointmentManager.appointmentOutcomes) {
            if (outcome.getAppointmentId().equals(appointmentId)) {
                outcome.setPrescriptionStatus(status);
                System.out.println("Prescription status updated to: " + status);
                return;
            }
        }
        System.out.println("No outcome record found for Appointment ID: " + appointmentId);
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
        try (FileWriter writer = new FileWriter("src/hms/data/Pending_Replenishment_Requests.csv", true)) {
            writer.append(id).append(",")
                  .append(medicineName).append(",")
                  .append(String.valueOf(quantity)).append("\n");
            System.out.println("Replenishment request submitted successfully for Medication: " + medicineName + ", Quantity: " + quantity);
            System.out.println("Awaiting approval from administrator...");
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
                    viewAppointmentOutcomeRecord(appointmentId);
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
} 
