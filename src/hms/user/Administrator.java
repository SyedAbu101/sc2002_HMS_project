package hms.user;

import hms.appointment.Appointment;
import hms.appointment.AppointmentManager;
import hms.inventory.*;
import java.util.List;
import java.util.Scanner;

public class Administrator extends User {
    private InventoryManager inventoryManager;
    private AppointmentManager appointmentManager;

    //constructor
    public Administrator(String id, String name) {
        super(id, name, "administrator");
        this.inventoryManager = new InventoryManager();
    }

    //manageStaff method
    public void manageStaff() {
        System.out.println("Managing hospital staff...");
        //need to add logic to update or remove staff
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
