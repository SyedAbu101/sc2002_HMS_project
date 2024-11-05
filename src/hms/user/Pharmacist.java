package hms.user;

import java.util.List;
import java.util.Scanner;
import hms.appointment.AppointmentOutcomeRecord;
import hms.inventory.*;

public class Pharmacist extends User {
    private InventoryManager inventoryManager;

    //constructor
    public Pharmacist(String id, String name) {
        super(id, name, "pharmacist");
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
    public void submitReplenishmentRequest(String medicineName, int quantity) {
        System.out.println("Submitting replenishment request for: " + medicineName + ", Quantity: " + quantity);
        inventoryManager.submitReplenishmentRequest(medicineName, quantity);
        System.out.println("Replenishment request submitted.");
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
            System.out.println("5. Logout");
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



