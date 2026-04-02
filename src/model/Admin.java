package model;

public class Admin extends User {

    private String adminCode; 

    public Admin(String userId, String name, String email, String password, String adminCode) {
        super(userId, name, email, password, "ADMIN"); 
        this.adminCode = adminCode;
    }

    public String getAdminCode() {
        return adminCode;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }

    // Override parent method - Dynamic Method Dispatch
    public String getDisplayInfo() {
        return "Admin ID: " + getUserId() + " | Name: " + getName()
                + " | Email: " + getEmail();
    }

    // Convert to file-safe string
    public String toFileString() {
        return getUserId() + "," + getName() + "," + getEmail() + ","
                + getPassword() + ",ADMIN," + adminCode;
    }
}
