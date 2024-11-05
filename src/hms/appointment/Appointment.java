package hms.appointment;

public class Appointment {
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private String date;
    private String time;
    private String status;

    //constructor
    public Appointment(String patientId, String doctorId, String date, String time) {
        this.appointmentId = generateAppointmentId();
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.status = "pending"; //this is the default status of appointments
    }

    //this function gives unique appointment IDs by using the current time in milliseconds
    private String generateAppointmentId() {
        return "APPT" + System.currentTimeMillis(); 
    }

    //get methods
    public String getAppointmentId() {
        return appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
    
    public String getStatus() {
        return status;
    }
    
    //set methods
    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return "Appointment ID: " + appointmentId + ", Patient ID: " + patientId + ", Doctor ID: " + doctorId + ", Date: " + date + ", Time: " + time + ", Status: " + status;
    }
}