package hms.user;

import hms.appointment.*;
import hms.util.ContactInfo;
import java.util.List;
import java.util.Scanner;

public class Patient extends User {
    private String dateOfBirth;
    private String gender;
    private ContactInfo contactInfo;
    private AppointmentManager appointmentManager;

    //constructor
    public Patient(String id, String name) {
        super(id, name, "patient");
        this.appointmentManager = new AppointmentManager();
    }

    //viewMedicalRecord method
    public void viewMedicalRecord() {
        System.out.println("Medical Record for Patient: " + name);
        //need to use getMedicalRecordByPatientId method in AppointmentManager.java
    }

    //updatePersonalInfo method
    public void updatePersonalInfo(ContactInfo newContactInfo) {
        this.contactInfo = newContactInfo;
        System.out.println("Contact information updated successfully.");
    }

    //viewAvailableAppointments method
    public void viewAvailableAppointments() {
        System.out.println("Available Appointment Slots:");
        List<Appointment> availableAppointments = appointmentManager.getAvailableAppointments();
        for (Appointment appointment : availableAppointments) {
            System.out.println(appointment);
        }
    }

    //scheduleAppointment method
    public void scheduleAppointment(String doctorId, String date, String time) {
        Appointment appointment = new Appointment(id, doctorId, date, time);
        boolean success = appointmentManager.scheduleAppointment(appointment);
        if (success) {
            System.out.println("Appointment scheduled successfully.");
        } else {
            System.out.println("Scheduling unsuccessful. Please try another slot.");
        }
    }

    //rescheduleAppointment method
    public void rescheduleAppointment(String appointmentId, String newDate, String newTime) {
        boolean success = appointmentManager.rescheduleAppointment(appointmentId, newDate, newTime);
        if (success) {
            System.out.println("Appointment rescheduled successfully.");
        } else {
            System.out.println("Failed to reschedule appointment. Please try another slot.");
        }
    }

    //cancelAppointment method
    public void cancelAppointment(String appointmentId) {
        boolean success = appointmentManager.cancelAppointment(appointmentId);
        if (success) {
            System.out.println("Appointment cancelled successfully.");
        } else {
            System.out.println("Failed to cancel appointment. Please try again.");
        }
    }

    //viewScheduledAppointments method
    public void viewScheduledAppointments() {
        System.out.println("Scheduled Appointments:");
        List<Appointment> appointments = appointmentManager.getAppointmentsByPatientId(id);
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }

    //viewPastAppointmentOutcomes method
    public void viewPastAppointmentOutcomes() {
        System.out.println("Past Appointment Outcomes:");
        List<AppointmentOutcomeRecord> outcomes = appointmentManager.getAppointmentOutcomesByPatientId(id);
        for (AppointmentOutcomeRecord outcome : outcomes) {
            System.out.println(outcome);
        }
    }

    //showMenu method, need to ass more options to include changing of password
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nPatient Menu:");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. Reschedule an Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcomes");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    viewMedicalRecord();
                    break;
                case 2:
                    System.out.print("Enter new email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter new phone number: ");
                    String phone = scanner.nextLine();
                    updatePersonalInfo(new ContactInfo(email, phone));
                    break;
                case 3:
                    viewAvailableAppointments();
                    break;
                case 4:
                    System.out.print("Enter Doctor ID: ");
                    String doctorId = scanner.nextLine();
                    System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter Appointment Time (HH:MM): ");
                    String time = scanner.nextLine();
                    scheduleAppointment(doctorId, date, time);
                    break;
                case 5:
                    System.out.print("Enter Appointment ID to reschedule: ");
                    String appointmentId = scanner.nextLine();
                    System.out.print("Enter new Appointment Date (YYYY-MM-DD): ");
                    String newDate = scanner.nextLine();
                    System.out.print("Enter new Appointment Time (HH:MM): ");
                    String newTime = scanner.nextLine();
                    rescheduleAppointment(appointmentId, newDate, newTime);
                    break;
                case 6:
                    System.out.print("Enter Appointment ID to cancel: ");
                    appointmentId = scanner.nextLine();
                    cancelAppointment(appointmentId);
                    break;
                case 7:
                    viewScheduledAppointments();
                    break;
                case 8:
                    viewPastAppointmentOutcomes();
                    break;
                case 9:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    } //showMenu method ends here
}

