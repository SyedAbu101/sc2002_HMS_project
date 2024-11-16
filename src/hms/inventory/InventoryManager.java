package hms.inventory;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class InventoryManager {
    private List<Medicine> medicines;
    private String medicinePath = "src/hms/data/Medicine_List.csv";

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

    // Method to save medicines to a CSV file
    public void saveMedicinesToCSV(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Name,Stock,LowStockAlertLevel\n");
            for (Medicine medicine : medicines) {
                writer.write(medicine.toCSV() + "\n");
            }
            System.out.println("Medicines saved successfully to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to the CSV file: " + e.getMessage());
        }
    }

    //addMedicine method
    public Boolean addMedicine(String name, int stock, int lowStockAlertLevel) {
        try {
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Medicine name cannot be empty.");
            }
            if (stock < 0) {
                throw new IllegalArgumentException("Stock cannot be negative.");
            }
            if (lowStockAlertLevel < 0) {
                throw new IllegalArgumentException("Low stock alert level cannot be negative.");
            }
            Medicine newMedicine = new Medicine(name, stock, lowStockAlertLevel);
            medicines.add(newMedicine);
            saveMedicinesToCSV(medicinePath);
            return true;

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            return false;
        }
    }

    //removeMedicine method
    public void removeMedicine(String medicineName) {
        boolean removed = medicines.removeIf(medicine -> medicine.getName().equalsIgnoreCase(medicineName));
        if (removed) {
            saveMedicinesToCSV(medicinePath);
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
                saveMedicinesToCSV(medicinePath);
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
