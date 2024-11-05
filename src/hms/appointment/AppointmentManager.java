package hms.appointment;

import hms.medical.MedicalRecord;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentManager implements AppointmentService {
    private List<Appointment> appointments;
    private List<MedicalRecord> medicalRecords;
    private List<AppointmentOutcomeRecord> appointmentOutcomes;

    //constructor
    public AppointmentManager() {
        this.appointments = new ArrayList<>();
        this.medicalRecords = new ArrayList<>();
        this.appointmentOutcomes = new ArrayList<>();
    }

    //scheduleAppointment method
    public boolean scheduleAppointment(Appointment appointment) {
        for (Appointment existingAppointment : appointments) {
            if (existingAppointment.getDoctorId().equals(appointment.getDoctorId()) &&
                existingAppointment.getDate().equals(appointment.getDate()) &&
                existingAppointment.getTime().equals(appointment.getTime()) &&
                existingAppointment.getStatus().equals("confirmed")) {
                System.out.println("The appointment slot is already taken.");
                return false;
            }
        }
        appointment.setStatus("confirmed");
        appointments.add(appointment);
        System.out.println("Appointment scheduled successfully.");
        return true;
    }

    //rescheduleApppointment method
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
                appointment.setStatus("cancelled");
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

    //getAvailableAppointments method
    public List<Appointment> getAvailableAppointments() {
        return appointments.stream()
                .filter(appointment -> appointment.getStatus().equals("pending"))
                .collect(Collectors.toList());
    }

    //addAvailableSlot method
    public void addAvailableSlot(String doctorId, String date, String time) {
        Appointment appointment = new Appointment("", doctorId, date, time);
        appointments.add(appointment);
        System.out.println("Available slot added successfully.");
    }

    //recordAppointmentOutcome method
    public void recordAppointmentOutcome(String appointmentId, String serviceType, String medicationName, String notes) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId().equals(appointmentId) && appointment.getStatus().equals("confirmed")) {
                AppointmentOutcomeRecord outcome = new AppointmentOutcomeRecord(appointmentId, serviceType, medicationName, notes);
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
    boolean scheduleAppointment(Appointment appointment);
    boolean rescheduleAppointment(String appointmentId, String newDate, String newTime);
    boolean cancelAppointment(String appointmentId);
    List<Appointment> getAppointmentsByPatientId(String patientId);
    List<Appointment> getAppointmentsByDoctorId(String doctorId);
    List<Appointment> getAvailableAppointments();
}


