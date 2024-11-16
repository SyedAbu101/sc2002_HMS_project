package hms.appointment;

public class Appointment {
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private String date;
    private String time;
    private String status;
    private static int apptNo = 0;
    //constructor
    public Appointment(String patientId, String doctorId, String date, String time) {
        this.appointmentId = "APPT" + apptNo;
        apptNo++;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.status = "pending"; //this is the default status of appointments
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

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String toString() {
        return "Appointment ID: " + appointmentId + ", Patient ID: " + patientId + ", Doctor ID: " + doctorId + ", Date: " + date + ", Time: " + time + ", Status: " + status;
    }
}
