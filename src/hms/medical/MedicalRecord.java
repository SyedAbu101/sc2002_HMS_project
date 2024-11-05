package hms.medical;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {
    private String patientId;
    private String bloodType;
    private List<String> diagnoses;
    private List<String> treatments;

    //constructor
    public MedicalRecord(String patientId) {
        this.patientId = patientId;
        this.diagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
    }

    //get methods
    public String getPatientId() {
        return patientId;
    }
    
    public String getBloodType() {
        return bloodType;
    }

    public List<String> getDiagnoses() {
        return new ArrayList<>(diagnoses);
    }
    
    public List<String> getTreatments() {
        return new ArrayList<>(treatments);
    }
    
    //set methods
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    //addDiagnosis method
    public void addDiagnosis(String diagnosis) {
        diagnoses.add(diagnosis);
        System.out.println("Diagnosis added successfully.");
    }

    //addTreatment method
    public void addTreatment(String treatment) {
        treatments.add(treatment);
        System.out.println("Treatment added successfully.");
    }

    //display method
    public void display() {
        System.out.println("Medical Record for Patient ID: " + patientId);
        System.out.println("Blood Type: " + bloodType);
        System.out.println("Diagnoses:");
        for (String diagnosis : diagnoses) {
            System.out.println("- " + diagnosis);
        }
        System.out.println("Treatments:");
        for (String treatment : treatments) {
            System.out.println("- " + treatment);
        }
    }
}
