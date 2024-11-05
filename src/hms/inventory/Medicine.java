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

    //toString methods provides a string representation of an object, so that when we print an instance of the medicine class, we get a detailed description of the object's state
    public String toString() {
        return "Medicine Name: " + name + ", Stock: " + stock + ", Low Stock Alert Level: " + lowStockAlertLevel;
    }
}
