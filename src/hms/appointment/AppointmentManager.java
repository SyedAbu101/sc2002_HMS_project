package hms.appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import hms.medical.MedicalRecord;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentManager implements AppointmentService {
    private static List<Appointment> appointments;
    public static List<MedicalRecord> medicalRecords;
    public static List<AppointmentOutcomeRecord> appointmentOutcomes;
    
    //constructor
    public AppointmentManager() {
        appointments = new ArrayList<>();
        medicalRecords = new ArrayList<>();
        appointmentOutcomes = new ArrayList<>();
    }

    //scheduleAppointment method
    public boolean scheduleAppointment(String appointmentId, String patientId) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId().equals(appointmentId) && appointment.getStatus().equals("pending")) {
                appointment.setPatientId(patientId);
                appointment.setStatus("requested");
                return true;
            }
        }
        System.out.println("The appointment slot is not available.");
        return false;
    }

    //getRequestedAppointmentsByDoctorId method
    public List<Appointment> getRequestedAppointmentsByDoctorId(String doctorId) {
        return appointments.stream()
                .filter(appointment -> appointment.getDoctorId().equals(doctorId) && appointment.getStatus().equals("requested"))
                .collect(Collectors.toList());
    }

    //getAppointmentById method
    public Appointment getAppointmentById(String appointmentId) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                return appointment;
            }
        }
        return null;
    }

    // Auto-create Medical Record if not found
    public MedicalRecord getOrCreateMedicalRecord(String patientId) {
        MedicalRecord record = getMedicalRecordByPatientId(patientId);
        if (record == null) {
            System.out.println("No existing medical record found. Creating a new record for patient ID: " + patientId);
            record = new MedicalRecord(patientId);
            medicalRecords.add(record);
        }
        return record;
    }

    //rescheduleAppointment method
    public boolean rescheduleAppointment(String appointmentId, String newDate, String newTime) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId().equals(appointmentId) && appointment.getStatus().equals("confirmed")) {
                appointment.setDate(newDate);
                appointment.setTime(newTime);
                System.out.println("Appointment rescheduled successfully.");
                return true;
            }
        }
        System.out.println("Appointment not found or cannot be rescheduled.");
        return false;
    }

    //cancelAppointment method
    public boolean cancelAppointment(String appointmentId) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId().equals(appointmentId) && appointment.getStatus().equals("confirmed")) {
                appointment.setPatientId("Null");
                appointment.setStatus("pending");
                System.out.println("Appointment cancelled successfully.");
                return true;
            }
        }
        System.out.println("Appointment not found or already cancelled.");
        return false;
    }

    //getAppointmentsByPatientId method
    public List<Appointment> getAppointmentsByPatientId(String patientId) {
        return appointments.stream()
                .filter(appointment -> appointment.getPatientId().equals(patientId))
                .collect(Collectors.toList());
    }

    //getAppointmentsByDoctorId method
    public List<Appointment> getAppointmentsByDoctorId(String doctorId) {
        return appointments.stream()
                .filter(appointment -> appointment.getDoctorId().equals(doctorId))
                .collect(Collectors.toList());
    }

    //getFutureAppointmentsByDoctorId method
    public List<Appointment> getFutureAppointmentsByDoctorId(String doctorId) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return appointments.stream()
                .filter(appointment -> appointment.getDoctorId().equals(doctorId))
                .filter(appointment -> {

                    // Parse date and time strings
                    LocalDate appointmentDate = LocalDate.parse(appointment.getDate(), dateFormatter);
                    LocalTime appointmentTime = LocalTime.parse(appointment.getTime(), timeFormatter);

                    // Combine appointment date and time to form a LocalDateTime for comparison
                    LocalDateTime appointmentDateTime = LocalDateTime.of(appointmentDate, appointmentTime);
                    return appointmentDateTime.isAfter(now);
                })
                .collect(Collectors.toList());
    }

    //getAvailableAppointments method
    public List<Appointment> getAvailableAppointments() {
        return appointments.stream()
                .filter(appointment -> appointment.getStatus().equals("pending"))
                .collect(Collectors.toList());
    }

    //addAvailableSlot method
    public void addAvailableSlot(String doctorId, String date, String time) {
        Appointment appointment = new Appointment("Null", doctorId, date, time);
        appointment.setStatus("pending");
        appointments.add(appointment);
        System.out.println("Available slot added successfully.");
    }

    //recordAppointmentOutcome method
    public void recordAppointmentOutcome(String appointmentId, String serviceType, String medicationName, String notes) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId().equals(appointmentId) && appointment.getStatus().equals("confirmed")) {
                AppointmentOutcomeRecord outcome = new AppointmentOutcomeRecord(appointmentId, appointment.getPatientId(), serviceType, medicationName, notes);
                appointmentOutcomes.add(outcome);
                appointment.setStatus("completed");
                System.out.println("Appointment outcome recorded successfully.");
                return;
            }
        }
        System.out.println("Appointment not found or cannot record outcome.");
    }
    
    //getMedicalRecordByPatientId method
    public MedicalRecord getMedicalRecordByPatientId(String patientId) {
        for (MedicalRecord record : medicalRecords) {
            if (record.getPatientId().equals(patientId)) {
                return record;
            }
        }
        return null;
    }

    //getAppointmentOutcomesByPatientId method
    public List<AppointmentOutcomeRecord> getAppointmentOutcomesByPatientId(String patientId) {
        return appointmentOutcomes.stream()
                .filter(outcome -> outcome.getPatientId().equals(patientId))
                .collect(Collectors.toList());
    }

  //getAppointmentOutcomeByAppointmentId method
    public AppointmentOutcomeRecord getAppointmentOutcomeByAppointmentId(String appointmentId) {
        for (AppointmentOutcomeRecord record : appointmentOutcomes) {
            if (record.getAppointmentId().equals(appointmentId)) {
                return record;
            }
        }
        System.out.println("No outcome record found for Appointment ID: " + appointmentId);
        return null;
    }
    
    //viewAppointmentStatus method
    public void updateAppointmentStatus(String appointmentId, String newStatus) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                appointment.setStatus(newStatus);
                System.out.println("Appointment status updated successfully to: " + newStatus);
                return;
            }
        }
        System.out.println("Appointment not found with ID: " + appointmentId);
    }
    
    //getAllAppointments method
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }
}

//interface
interface AppointmentService {
    boolean scheduleAppointment(String appointmentId, String patientId);
    boolean rescheduleAppointment(String appointmentId, String newDate, String newTime);
    boolean cancelAppointment(String appointmentId);
    List<Appointment> getAppointmentsByPatientId(String patientId);
    List<Appointment> getAppointmentsByDoctorId(String doctorId);
    List<Appointment> getAvailableAppointments();
}
