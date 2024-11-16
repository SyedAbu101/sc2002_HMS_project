package hms.inventory;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InventoryManager {
    private List<Medicine> medicines;

    //constructor
    public InventoryManager() {
        this.medicines = loadMedicinesFromCSV("src/hms/data/Medicine_List.csv");
    }

    // Method to read medicines from a CSV file
    private List<Medicine> loadMedicinesFromCSV(String filePath) {
        List<Medicine> medicinesList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            boolean isFirstLine = true;
            String line;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                Medicine medicine = Medicine.fromCSV(line);
                medicinesList.add(medicine);
            }
            System.out.println("Medicines loaded successfully from " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
        return medicinesList;
    }

    //addMedicine method
    public void addMedicine(Medicine medicine) {
        medicines.add(medicine);
        System.out.println("Medicine added successfully: " + medicine.getName());
    }

    //removeMedicine method
    public void removeMedicine(String medicineName) {
        boolean removed = medicines.removeIf(medicine -> medicine.getName().equalsIgnoreCase(medicineName));
        if (removed) {
            System.out.println("Medicine removed successfully: " + medicineName);
        } else {
            System.out.println("Medicine not found: " + medicineName);
        }
    }

    //updateStock method
    public void updateStock(String medicineName, int newStock) {
        for (Medicine medicine : medicines) {
            if (medicine.getName().equalsIgnoreCase(medicineName)) {
                medicine.setStock(newStock);
                System.out.println("Stock updated successfully for: " + medicineName);
                return;
            }
        }
        System.out.println("Medicine not found: " + medicineName);
    }

    //getMedicines method
    public List<Medicine> getMedicines() {
        return new ArrayList<>(medicines);
    }
    
    //submitReplenishmentRequest method
    public void submitReplenishmentRequest(String medicineName, int quantity) {
        System.out.println("Replenishment request submitted for: " + medicineName + ", Quantity: " + quantity);
        //need to update with real logic
    }

    //getMedicineByName method
    public Medicine getMedicineByName(String medicineName) {
        for (Medicine medicine : medicines) {
            if (medicine.getName().equalsIgnoreCase(medicineName)) {
                return medicine;
            }
        }
        return null;
    }
}
