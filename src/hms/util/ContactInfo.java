package hms.util;

public class ContactInfo {
    private String email;
    private String phone;

    //constructor
    public ContactInfo(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }

    public ContactInfo(String email) {
        this.email = email;
    }

    //get methods
    public String getEmail() {
        return email;
    }
    
    public String getPhone() {
        return phone;
    }

    //set methods
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return "Email: " + email + ", Phone: " + phone;
    }
}
