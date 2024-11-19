package hms.user;

import hms.appointment.*;
import hms.medical.MedicalRecord;
import hms.util.ContactInfo;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Patient extends User {
    private String dateOfBirth;
    private String gender;
    private ContactInfo contactInfo;
    private String bloodType;
    private List<String> pastDiagnosesAndTreatments;
    private AppointmentManager appointmentManager;

    //constructor
    public Patient(String id, String name, String password, String securityQuestion, String securityAnswer, String dateOfBirth, String gender, String bloodType, ContactInfo contactInfo, List<String> pastDiagnosesAndTreatments) {
        super(id, name, "patient", password, securityQuestion, securityAnswer);
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
        this.pastDiagnosesAndTreatments = pastDiagnosesAndTreatments;
        this.appointmentManager = new AppointmentManager();
    }

    //viewMedicalRecord method
    public void viewMedicalRecord() {
        System.out.println("Medical Record for Patient: " + name);
        System.out.println("Patient ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Gender: " + gender);
        System.out.println("Contact Information: " + contactInfo);
        System.out.println("Blood Type: " + bloodType);
        MedicalRecord record = appointmentManager.getOrCreateMedicalRecord(id);
        if (record != null) {
            record.display();
        } else {
            System.out.println("No medical record found.");
        }
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
            User doctor = User.getUserById(appointment.getDoctorId());
            System.out.println("Appointment ID: " + appointment.getAppointmentId() + ", Date: " + appointment.getDate() + ", Time: " + appointment.getTime() + ", Doctor: " + doctor.getName());
        }
    }

    //scheduleAppointment method
    public void scheduleAppointment(String appointmentId) {
        boolean success = appointmentManager.scheduleAppointment(appointmentId, id);
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

    public void rescheduleAppointment() {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Enter the appointment ID of an already confirmed appointment
        System.out.print("Enter Appointment ID to reschedule: ");
        String appointmentId = scanner.nextLine();

        // Step 2: Check if the appointment is confirmed and belongs to the patient
        Appointment oldAppointment = appointmentManager.getAppointmentById(appointmentId);
        if (oldAppointment == null || !oldAppointment.getPatientId().equals(id) || !oldAppointment.getStatus().equals("confirmed")) {
            System.out.println("Invalid or unconfirmed appointment ID.");
            return;
        }

        // Step 3: Display available appointments
        System.out.println("Available Appointment Slots:");
        List<Appointment> availableAppointments = appointmentManager.getAvailableAppointments();
        for (Appointment appointment : availableAppointments) {
            User doctor = User.getUserById(appointment.getDoctorId());
            System.out.println("Appointment ID: " + appointment.getAppointmentId() + ", Date: " + appointment.getDate() + ", Time: " + appointment.getTime() + ", Doctor: " + doctor.getName());
        }

        // Step 4: Enter the new appointment ID
        System.out.print("Enter new Appointment ID: ");
        String newAppointmentId = scanner.nextLine();

        // Step 5: Check if the new appointment is available
        Appointment newAppointment = appointmentManager.getAppointmentById(newAppointmentId);
        if (newAppointment == null || !newAppointment.getStatus().equals("pending")) {
            System.out.println("Invalid or unavailable appointment ID.");
            return;
        }

        // Step 6: Update the old appointment to pending and remove patient ID
        oldAppointment.setStatus("pending");
        oldAppointment.setPatientId(null);

        // Step 7: Update the new appointment with the patient ID and set status to requested
        newAppointment.setPatientId(id);
        newAppointment.setStatus("requested");

        System.out.println("Appointment rescheduled successfully.");
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
        if (outcomes.isEmpty()) {
            System.out.println("No past appointment outcomes available.");
        } 
        else {
            for (AppointmentOutcomeRecord outcome : outcomes) {
                System.out.println(outcome);
            }
        }
    }

    //showMenu method, need to add more options to include changing of password
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("\nPatient Menu:");
                System.out.println("1. View Medical Record");
                System.out.println("2. Update Personal Information");
                System.out.println("3. View Available Appointment Slots");
                System.out.println("4. Schedule an Appointment");
                System.out.println("5. Reschedule an Appointment");
                System.out.println("6. Cancel an Appointment");
                System.out.println("7. View Scheduled Appointments");
                System.out.println("8. View Past Appointment Outcomes");
                System.out.println("9. Change Password");
                System.out.println("10. Logout");
                System.out.print("Enter your choice: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number from the menu.");
                    scanner.next(); // Clear invalid input
                    continue;
                }

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        viewMedicalRecord();
                        break;
                    case 2:
                        System.out.println("Update Personal Information (Enter Null to delete):");
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
                        System.out.print("Enter appointment ID: ");
                        String appointmentId1 = scanner.nextLine();
                        scheduleAppointment(appointmentId1);
                        break;
                    case 5:
                        rescheduleAppointment();
                        break;
                    case 6:
                        System.out.print("Enter Appointment ID to cancel: ");
                        String appointmentId = scanner.nextLine();
                        cancelAppointment(appointmentId);
                        break;
                    case 7:
                        viewScheduledAppointments();
                        break;
                    case 8:
                        viewPastAppointmentOutcomes();
                        break;
                    case 9:
                        System.out.print("Enter new password: ");
                        String newPassword = scanner.nextLine();
                        changePassword(newPassword);
                        break;
                    case 10:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number from the menu.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    } // showMenu method ends here

}
