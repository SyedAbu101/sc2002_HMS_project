package hms.user;

import hms.appointment.*;
import hms.medical.MedicalRecord;
import java.util.List;
import java.util.Scanner;

public class Doctor extends User {
    private String gender;
    private String age;
    private AppointmentManager appointmentManager;

    //constructor
    public  Doctor(String id, String name, String gender, String age, String password, String securityQuestion, String securityAnswer) {
        super(id, name, "doctor", password, securityQuestion, securityAnswer);
        this.gender = gender;
        this.age = age;
        this.appointmentManager = new AppointmentManager();
    }
    
    //viewPatientMedicalRecord method
    public void viewPatientMedicalRecord(String patientId) {
        MedicalRecord record = appointmentManager.getMedicalRecordByPatientId(patientId);
        if (record != null) {
            record.display();
        } else {
            System.out.println("No medical record found for Patient ID: " + patientId);
        }
    }

    //updateMedicalRecord method
    public void updatePatientMedicalRecord() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Patient ID to view or edit medical records: ");
        String patientId = scanner.nextLine();

        User patient =getUserById(patientId);
        if (patient == null) {
            System.out.println("Invalid Patient ID: " + patientId);
            return;
        }
        // Get or create a medical record
        MedicalRecord record = appointmentManager.getOrCreateMedicalRecord(patientId);

        // Display the medical record
        record.display();

        System.out.print("Do you want to edit the medical record? (yes/no): ");
        String editChoice = scanner.nextLine();
        if (editChoice.equalsIgnoreCase("yes")) {
            System.out.print("Enter new diagnosis (leave blank to skip): ");
            String newDiagnosis = scanner.nextLine().trim();
            if (!newDiagnosis.isEmpty()) {
                record.addDiagnosis(newDiagnosis);
            }

            System.out.print("Enter new treatment (leave blank to skip): ");
            String newTreatment = scanner.nextLine().trim();
            if (!newTreatment.isEmpty()) {
                record.addTreatment(newTreatment);
            }

            System.out.print("Enter new prescription (leave blank to skip): ");
            String newPrescription = scanner.nextLine().trim();
            if (!newPrescription.isEmpty()) {
                record.addPrescription(newPrescription);
            }

            System.out.println("Medical record updated successfully.");
        }
    }

    //viewPersonalSchedule method
    public void viewPersonalSchedule() {
        System.out.println("Personal Schedule for Dr. " + name + ":");
        List<Appointment> appointments = appointmentManager.getAppointmentsByDoctorId(id);
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }

    //setAvailability method
    public void setAvailability(String date, String time) {
        System.out.println("Setting availability for Dr. " + name + " on " + date + " at " + time);
        appointmentManager.addAvailableSlot(id, date, time);
        System.out.println("Availability set successfully.");
    }

    //acceptOrDeclineAppointment method
    public void acceptOrDeclineAppointment(String appointmentId, boolean accept) {
        if (accept) {
            appointmentManager.updateAppointmentStatus(appointmentId, "confirmed");
            System.out.println("Appointment confirmed.");
        } else {
            appointmentManager.updateAppointmentStatus(appointmentId, "declined");
            System.out.println("Appointment declined.");
        }
    }

    public void viewUpcomingAppointments(){
        System.out.println("Upcoming Appointment for Dr. " + name + ":");
        List<Appointment> appointments = appointmentManager.getFutureAppointmentsByDoctorId(id);
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }

    //recordAppointmentOutcome method
    public void recordAppointmentOutcome(String appointmentId, String serviceType, String medicationName, String notes) {
        System.out.println("Recording outcome for Appointment ID: " + appointmentId);
        appointmentManager.recordAppointmentOutcome(appointmentId, serviceType, medicationName, notes);
        System.out.println("Appointment outcome recorded successfully.");
    }

    //showMenu method
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nDoctor Menu:");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("8. Change Password");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter Patient ID to view medical records: ");
                    String patientId = scanner.nextLine();
                    viewPatientMedicalRecord(patientId);
                    break;
                case 2:
                    updatePatientMedicalRecord();
                    break;
                case 3:
                    viewPersonalSchedule();
                    break;
                case 4:
                    System.out.print("Enter date for availability (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter time for availability (HH:MM): ");
                    String time = scanner.nextLine();
                    setAvailability(date, time);
                    break;
                case 5:
                    System.out.print("Enter Appointment ID to accept/decline: ");
                    String appointmentId = scanner.nextLine();
                    System.out.print("Accept appointment? (yes/no): ");
                    String acceptInput = scanner.nextLine();
                    boolean accept = acceptInput.equalsIgnoreCase("yes");
                    acceptOrDeclineAppointment(appointmentId, accept);
                    break;
                case 6:
                    viewUpcomingAppointments();
                    break;
                case 7:
                    System.out.print("Enter Appointment ID to record outcome: ");
                    appointmentId = scanner.nextLine();
                    System.out.print("Enter type of service provided: ");
                    String serviceType = scanner.nextLine();
                    System.out.print("Enter medication name (if any): ");
                    String medicationName = scanner.nextLine();
                    System.out.print("Enter consultation notes: ");
                    String notes = scanner.nextLine();
                    recordAppointmentOutcome(appointmentId, serviceType, medicationName, notes);
                    break;
                case 8:
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    changePassword(newPassword);
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

