package hms.inventory;

public class Medicine {
    private String name;
    private int stock;
    private int lowStockAlertLevel;

    //constructor
    public Medicine(String name, int stock, int lowStockAlertLevel) {
        this.name = name;
        this.stock = stock;
        this.lowStockAlertLevel = lowStockAlertLevel;
    }

    //get methods
    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }
    
    public int getLowStockAlertLevel() {
        return lowStockAlertLevel;
    }

    //set methods
    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setLowStockAlertLevel(int lowStockAlertLevel) { this.lowStockAlertLevel = lowStockAlertLevel; }

    //toString methods provides a string representation of an object, so that when we print an instance of the medicine class, we get a detailed description of the object's state
    public String toString() {
        return "Medicine Name: " + name + ", Stock: " + stock + ", Low Stock Alert Level: " + lowStockAlertLevel;
    }

    // Convert to CSV format string
    public String toCSV() { return name + "," + stock + "," + lowStockAlertLevel; }

    // Static method to create a Medicine instance from a CSV row
    public static Medicine fromCSV(String csvRow) {
        int stock;
        int lowStockAlertLevel;;
        String[] data = csvRow.split(",");

        if (data.length < 3) {
            throw new IllegalArgumentException("Invalid CSV row: " + csvRow);
        }

        String name = data[0];

        try {
            stock = Integer.parseInt(data[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid stock value in CSV row: " + csvRow, e);
        }

        try {
            lowStockAlertLevel = Integer.parseInt(data[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid low stock alert level in CSV row: " + csvRow, e);
        }
        return new Medicine(name, stock, lowStockAlertLevel);
    }
}
