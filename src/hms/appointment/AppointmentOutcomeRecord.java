package hms.appointment;

public class AppointmentOutcomeRecord {
    private String appointmentId;
    private String patientId;
    private String serviceType;
    private String medicationName;
    private String notes;
    private String prescriptionStatus;

    //constructor
    public AppointmentOutcomeRecord(String appointmentId, String patientId, String serviceType, String medicationName, String notes) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
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
    	return patientId;
    }

    //set method
    public void setPrescriptionStatus (String status) {
    	this.prescriptionStatus = status;
    }
    
    public String toString() {
        return "Appointment ID: " + appointmentId + ", Service Type: " + serviceType + ", Medication: " + medicationName + ", Prescription Status: " + prescriptionStatus + ", Notes: " + notes;
    }
}
