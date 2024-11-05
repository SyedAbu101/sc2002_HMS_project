package hms.appointment;

public class AppointmentOutcomeRecord {
    private String appointmentId;
    private String serviceType;
    private String medicationName;
    private String notes;

    //constructor
    public AppointmentOutcomeRecord(String appointmentId, String serviceType, String medicationName, String notes) {
        this.appointmentId = appointmentId;
        this.serviceType = serviceType;
        this.medicationName = medicationName;
        this.notes = notes;
    }

    //get methods
    public String getAppointmentId() {
        return appointmentId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public String getNotes() {
        return notes;
    }

    public String getPatientId() {
        //need to update with real logic
    	return "";
    }

    public String toString() {
        return "Appointment ID: " + appointmentId + ", Service Type: " + serviceType + ", Medication: " + medicationName + ", Notes: " + notes;
    }
}